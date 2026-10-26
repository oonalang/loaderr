package astral.loader

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.SystemClock

/** Short bundled cues, preloaded when the screen starts. Device audio settings govern playback. */
internal class ShaiSounds(context: Context) {
    enum class Cue { TAP, SUCCESS, ERROR, READY, CRY, HAPPY, GIGGLE }
    private val appContext = context.applicationContext
    private var soundPool: SoundPool? = null
    private val soundIds = HashMap<Cue, Int>()
    private val ready = HashSet<Cue>()
    private var pending: Pair<Cue, Long>? = null
    private var lastTapAt: Long? = null
    private var activeStream = 0
    private var busyUntil = 0L
    private val cues = mapOf(Cue.TAP to R.raw.ui_tap, Cue.SUCCESS to R.raw.ui_success,
        Cue.ERROR to R.raw.ui_error, Cue.READY to R.raw.ui_ready,
        Cue.CRY to R.raw.ui_cry, Cue.HAPPY to R.raw.ui_happy, Cue.GIGGLE to R.raw.ui_giggle)

    fun start() {
        if (soundPool != null) return
        val pool = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME)
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
        if (cue == Cue.TAP && now < busyUntil) return
        if (cue == Cue.TAP) {
            if (lastTapAt?.let { now - it < 110L } == true) return
            lastTapAt = now
        }
        if (cue in ready) playLoaded(cue) else pending = cue to now
    }

    private fun audible(): Boolean {
        val audio = appContext.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return false
        // Assistance sonification rides the system stream: mute it and the cues mute.
        return audio.ringerMode == AudioManager.RINGER_MODE_NORMAL &&
            audio.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0
    }
    private fun playLoaded(cue: Cue) {
        if (!audible()) return
        val id = soundIds[cue] ?: return
        val pool = soundPool ?: return
        if (cue == Cue.TAP && SystemClock.elapsedRealtime() < busyUntil) return
        if (activeStream != 0) pool.stop(activeStream)
        activeStream = pool.play(id, .8f, .8f, 1, 0, 1f)
        if (activeStream == 0) android.util.Log.w("ShaiSounds", "SoundPool could not allocate a UI stream")
        busyUntil = SystemClock.elapsedRealtime() + when(cue) {
            Cue.TAP -> 60L
            Cue.ERROR -> 250L
            Cue.SUCCESS, Cue.READY -> 450L
            else -> 650L
        }
    }
    fun stop() {
        soundPool?.setOnLoadCompleteListener(null)
        soundPool?.release()
        soundPool = null
        ready.clear()
        soundIds.clear()
        pending = null
        lastTapAt = null
        activeStream = 0
        busyUntil = 0L
    }
}
