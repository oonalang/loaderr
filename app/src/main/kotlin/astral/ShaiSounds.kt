package astral.loader

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool

/**
 * The app's small UI sound kit.
 *
 * Four locally bundled, licence-free cues, all short and quiet: a tactile tap
 * for meaningful buttons, toggles and selectors, one restrained success chord
 * only when the panel verifies a key, one low unresolved tone for real
 * validation or preparation failures, and a soft ready cue once a library has
 * actually finished preparing. There is no ambience, no music, no looping, and
 * nothing plays while the user has muted the app or silenced the device.
 *
 * The preference is persisted in the loader's own preference file so screens
 * and tests read the same source of truth the toggle writes. Playback never
 * blocks the main thread: the pool is built lazily and its samples stream in
 * asynchronously, so a first-ever tap may simply not sound yet rather than
 * hitch the frame.
 */
internal class ShaiSounds(context: Context) {

    enum class Cue { TAP, SUCCESS, ERROR, READY }

    private val appContext = context.applicationContext
    private val preferences = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private var soundPool: SoundPool? = null
    private val cues = mapOf(
        Cue.TAP to R.raw.ui_tap,
        Cue.SUCCESS to R.raw.ui_success,
        Cue.ERROR to R.raw.ui_error,
        Cue.READY to R.raw.ui_ready
    )
    private val soundIds = HashMap<Cue, Int>()
    private val ready = HashSet<Cue>()
    private var lastTapAt = 0L

    fun isEnabled(): Boolean = preferences.getBoolean(KEY_UI_SOUNDS, true)

    fun setEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_UI_SOUNDS, enabled).apply()
        if (!enabled) stop()
    }

    fun play(cue: Cue) {
        if (!isEnabled()) return
        val audio = appContext.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
        if (audio.ringerMode != AudioManager.RINGER_MODE_NORMAL) return
        val pool = soundPool ?: createPool().also { soundPool = it }
        val id = cues.getValue(cue)
        val soundId = soundIds.getOrPut(cue) {
            val newId = pool.load(appContext, id, 1)
            newId
        }
        // One tap per physical action, even when listeners fire twice.
        val now = System.currentTimeMillis()
        if (cue == Cue.TAP) {
            if (now - lastTapAt < TAP_GAP_MS) return
            lastTapAt = now
        }
        if (cue !in ready) return
        pool.play(soundId, 1f, 1f, SIMPLE_PRIORITY, 0, RATE_NORMAL)
    }

    private fun createPool(): SoundPool {
        val attributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        val pool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(attributes)
            .build()
        pool.setOnLoadCompleteListener { _, sampleId, status ->
            val cue = soundIds.entries.firstOrNull { it.value == sampleId }?.key
            if (cue != null) {
                if (status == 0) ready.add(cue) else soundIds.remove(cue)
            }
        }
        return pool
    }

    /** Called from the activity's teardown; a later play() rebuilds the pool. */
    fun stop() {
        soundPool?.release()
        soundPool = null
        soundIds.clear()
        ready.clear()
    }

    companion object {
        private const val PREFERENCES_NAME = "loaderPrefs"
        private const val KEY_UI_SOUNDS = "ui_sounds_enabled"
        private const val TAP_GAP_MS = 110L
        private const val SIMPLE_PRIORITY = 1
        private const val RATE_NORMAL = 1f
    }
}
