package astral.loader

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.SystemClock

/** Short bundled cues, preloaded when the screen starts. Device audio settings govern playback. */
internal class ShaiSounds(context: Context) {
    enum class Cue { TAP, SUCCESS, ERROR, READY }
    private val appContext = context.applicationContext
    private var soundPool: SoundPool? = null
    private val soundIds = HashMap<Cue, Int>()
    private val ready = HashSet<Cue>()
    private var pending: Pair<Cue, Long>? = null
    private var lastTapAt: Long? = null
    private val cues = mapOf(Cue.TAP to R.raw.ui_tap, Cue.SUCCESS to R.raw.ui_success,
        Cue.ERROR to R.raw.ui_error, Cue.READY to R.raw.ui_ready)

    fun start() {
        if (soundPool != null) return
        val pool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()).build()
        soundPool = pool
        pool.setOnLoadCompleteListener { source, id, status ->
            if (source !== soundPool) return@setOnLoadCompleteListener
            val cue = soundIds.entries.firstOrNull { it.value == id }?.key ?: return@setOnLoadCompleteListener
            if (status == 0) {
                ready.add(cue)
                val waiting = pending
                if (waiting?.first == cue) {
                    pending = null
                    if (SystemClock.elapsedRealtime() - waiting.second <= 500L) playLoaded(cue)
                }
            }
        }
        cues.forEach { (cue, resource) -> soundIds[cue] = pool.load(appContext, resource, 1) }
    }

    fun play(cue: Cue) {
        // No resurrection after onStop: the next onStart preloads a fresh pool.
        if (soundPool == null || !audible()) return
        val now = SystemClock.elapsedRealtime()
        if (cue == Cue.TAP) {
            if (lastTapAt?.let { now - it < 110L } == true) return
            lastTapAt = now
        }
        if (cue in ready) playLoaded(cue) else pending = cue to now
    }

    private fun audible(): Boolean {
        val audio = appContext.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return false
        return audio.ringerMode == AudioManager.RINGER_MODE_NORMAL &&
            audio.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0
    }
    private fun playLoaded(cue: Cue) {
        if (!audible()) return
        val id = soundIds[cue] ?: return
        soundPool?.play(id, .65f, .65f, 1, 0, 1f)
    }
    fun stop() {
        soundPool?.setOnLoadCompleteListener(null)
        soundPool?.release()
        soundPool = null
        ready.clear()
        soundIds.clear()
        pending = null
        lastTapAt = null
    }
}
