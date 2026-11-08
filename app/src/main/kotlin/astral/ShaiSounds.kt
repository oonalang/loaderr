package astral.loader

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.SystemClock
import android.util.Log

internal class ShaiSounds(context: Context) {
    enum class Cue {
        TAP, SUCCESS, ERROR, READY, CRY, HAPPY, GIGGLE,
        WRITE, PAPER, CHECK, CLAY_TAP, DIGITAL
    }

    private val context = context.applicationContext
    private var soundPool: SoundPool? = null
    private val soundIds = mutableMapOf<Cue, Int>()
    private val loaded = mutableSetOf<Int>()
    private val lastPlayed = mutableMapOf<Cue, Long>()
    private var pending: Pair<Cue, Long>? = null

    private val resources = mapOf(
        Cue.TAP to R.raw.ui_tap,
        Cue.SUCCESS to R.raw.ui_success,
        Cue.ERROR to R.raw.ui_error,
        Cue.READY to R.raw.ui_ready,
        Cue.CRY to R.raw.ui_cry,
        Cue.HAPPY to R.raw.ui_happy,
        Cue.GIGGLE to R.raw.ui_giggle,
        Cue.WRITE to R.raw.pencil_write,
        Cue.PAPER to R.raw.paper_crumple,
        Cue.CHECK to R.raw.pencil_check,
        Cue.CLAY_TAP to R.raw.clay_tap,
        Cue.DIGITAL to R.raw.digital_click
    )

    fun start() {
        if (soundPool != null) return
        try {
            val pool = SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .build()
            soundPool = pool
            pool.setOnLoadCompleteListener { source, id, status ->
                if (source !== soundPool || status != 0) return@setOnLoadCompleteListener
                loaded.add(id)
                val waiting = pending
                if (waiting != null && soundIds[waiting.first] == id) {
                    pending = null
                    if (SystemClock.uptimeMillis() - waiting.second <= 400L) {
                        playLoaded(waiting.first)
                    }
                }
            }
            resources.forEach { (cue, resourceId) ->
                runCatching { pool.load(context, resourceId, 1) }
                    .onSuccess { id -> if (id > 0) soundIds[cue] = id }
                    .onFailure { Log.w("ShaiSounds", "Could not load $cue", it) }
            }
        } catch (error: Exception) {
            Log.w("ShaiSounds", "Audio playback is unavailable", error)
            stop()
        }
    }

    fun play(cue: Cue) {
        if (soundPool == null) return
        val now = SystemClock.uptimeMillis()
        val interval = when (cue) {
            Cue.WRITE -> 45L
            Cue.TAP, Cue.DIGITAL, Cue.CLAY_TAP, Cue.CHECK -> 65L
            Cue.PAPER -> 160L
            else -> 180L
        }
        if (now - (lastPlayed[cue] ?: Long.MIN_VALUE / 2L) < interval) return
        lastPlayed[cue] = now
        val id = soundIds[cue] ?: return
        if (id in loaded) {
            playLoaded(cue)
        } else {
            pending = cue to now
        }
    }

    private fun playLoaded(cue: Cue) {
        val pool = soundPool ?: return
        val id = soundIds[cue] ?: return
        val volume = when (cue) {
            Cue.WRITE -> 0.30f
            Cue.PAPER -> 0.50f
            Cue.CHECK -> 0.38f
            Cue.DIGITAL -> 0.32f
            Cue.CLAY_TAP -> 0.38f
            Cue.TAP -> 0.35f
            else -> 0.55f
        }
        runCatching { pool.play(id, volume, volume, 1, 0, 1f) }
            .onFailure { Log.w("ShaiSounds", "Could not play $cue", it) }
    }

    fun stop() {
        val pool = soundPool
        soundPool = null
        runCatching { pool?.setOnLoadCompleteListener(null) }
        runCatching { pool?.release() }
        soundIds.clear()
        loaded.clear()
        lastPlayed.clear()
        pending = null
    }
}
