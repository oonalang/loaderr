package astral.loader

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import java.time.Duration
import java.time.LocalTime
import java.time.ZonedDateTime

/** Device-local wall time, independent of promotional rotation and authentication. */
internal object LocalGreeting {
    private val boundaries = listOf(0 to 0, 5 to 0, 8 to 0, 11 to 30, 14 to 0, 18 to 0)
    fun message(now: ZonedDateTime): String = when (now.hour * 60 + now.minute) {
        in 0 until 300 -> "Still awake?"
        in 300 until 480 -> "wow, it's sunrise."
        in 480 until 690 -> "What a great day."
        in 690 until 840 -> "How's lunch time lad?"
        in 840 until 1080 -> "What a great day."
        else -> "Good evening."
    }

    fun nextBoundary(now: ZonedDateTime): ZonedDateTime = (0L..1L).flatMap { day ->
        boundaries.map { (hour, minute) ->
            now.toLocalDate().plusDays(day).atTime(LocalTime.of(hour, minute)).atZone(now.zone)
        }
    }.filter { it.isAfter(now) }.minBy { it.toInstant() }
}

/** One callback at the next boundary, rescheduled on resume or a clock/zone change. */
internal class GreetingClock(
    private val context: Context,
    private val render: (String) -> Unit,
    private val now: () -> ZonedDateTime = { ZonedDateTime.now() }
) {
    private val handler = Handler(Looper.getMainLooper())
    private var started = false
    private var enabled = false
    private val tick = Runnable { refresh() }
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) = refresh()
    }
    fun start() {
        if (started) return
        started = true
        ContextCompat.registerReceiver(context, receiver, IntentFilter().apply {
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_DATE_CHANGED)
        }, ContextCompat.RECEIVER_NOT_EXPORTED)
        refresh()
    }
    fun setEnabled(value: Boolean) {
        if (enabled == value) return
        enabled = value
        refresh()
    }
    private fun refresh() {
        handler.removeCallbacks(tick)
        if (!enabled) return
        val current = now()
        render(LocalGreeting.message(current))
        if (started) handler.postDelayed(tick,
            Duration.between(current, LocalGreeting.nextBoundary(current)).toMillis().coerceAtLeast(1))
    }
    fun stop() {
        handler.removeCallbacks(tick)
        if (started) context.unregisterReceiver(receiver)
        started = false
    }
}
