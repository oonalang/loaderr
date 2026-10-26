package astral.loader

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Looper
import android.view.View
import android.widget.RadioButton
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.Duration
import java.time.ZonedDateTime

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class ReconstructionTest {
    @Test fun greetingsUseAllLocalBoundaries() {
        val samples = mapOf("00:00" to "Still awake?", "04:59" to "Still awake?",
            "05:00" to "wow, it's sunrise.", "07:59" to "wow, it's sunrise.",
            "08:00" to "What a great day.", "10:22" to "What a great day.",
            "11:29" to "What a great day.", "11:30" to "How's lunch time lad?",
            "13:59" to "How's lunch time lad?", "14:00" to "What a great day.",
            "17:59" to "What a great day.", "18:00" to "Good evening.", "23:59" to "Good evening.")
        samples.forEach { (time, greeting) ->
            assertEquals(greeting, LocalGreeting.message(ZonedDateTime.parse("2026-10-26T${time}:00-04:00[America/New_York]")))
        }
    }
    @Test fun greetingReschedulesOnBoundaryResumeAndClockChange() {
        val context = RuntimeEnvironment.getApplication()
        var now = ZonedDateTime.parse("2026-10-26T11:29:59Z")
        var rendered = ""
        val clock = GreetingClock(context, { rendered = it }, { now })
        clock.setEnabled(true); clock.start()
        assertEquals("What a great day.", rendered)
        now = now.plusSeconds(1)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(1))
        assertEquals("How's lunch time lad?", rendered)
        clock.stop(); now = now.withHour(19); clock.start()
        assertEquals("Good evening.", rendered)
        now = now.withHour(6)
        context.sendBroadcast(Intent(Intent.ACTION_TIMEZONE_CHANGED))
        shadowOf(Looper.getMainLooper()).idle()
        assertEquals("wow, it's sunrise.", rendered)
        clock.stop()
    }
    @Test fun repeatedModeTapNeverUnchecksAndPersists() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        val activity = controller.get()
        val auto = activity.findViewById<RadioButton>(R.id.radio_auto)
        val manual = activity.findViewById<RadioButton>(R.id.radio_manual)
        manual.performClick(); manual.performClick()
        assertTrue(manual.isChecked); assertFalse(auto.isChecked)
        assertEquals("manual", LoaderPreferences(activity).getMode())
        auto.performClick(); assertTrue(auto.isChecked); assertFalse(manual.isChecked)
        assertEquals("auto", LoaderPreferences(activity).getMode())
        controller.pause().stop().destroy()
    }
    @Test fun imagesClipContentEvenAfterReplacement() {
        val view = RoundedArtworkView(RuntimeEnvironment.getApplication())
        view.layout(0, 0, 160, 160)
        for (color in listOf(Color.RED, Color.BLUE)) {
            view.setImageDrawable(android.graphics.drawable.ColorDrawable(color))
            val bitmap = Bitmap.createBitmap(160,160,Bitmap.Config.ARGB_8888)
            view.draw(Canvas(bitmap))
            assertEquals(0, Color.alpha(bitmap.getPixel(0,0)))
            assertEquals(color, bitmap.getPixel(80,80))
        }
    }
    @Test fun soundFirstEventWaitsForLoadAndSilentModeSuppressesPlayback() {
        val context = RuntimeEnvironment.getApplication()
        val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
        audio.setStreamVolume(AudioManager.STREAM_SYSTEM, 5, 0)
        val sounds = ShaiSounds(context); sounds.start()
        val pool = ShaiSounds::class.java.getDeclaredField("soundPool").apply { isAccessible = true }.get(sounds) as SoundPool
        val shadow = shadowOf(pool)
        sounds.play(ShaiSounds.Cue.SUCCESS)
        assertFalse(shadow.wasResourcePlayed(R.raw.ui_success))
        shadow.notifyResourceLoaded(R.raw.ui_success, true)
        assertTrue(shadow.wasResourcePlayed(R.raw.ui_success))
        shadow.clearPlayed(); audio.ringerMode = AudioManager.RINGER_MODE_SILENT
        sounds.play(ShaiSounds.Cue.SUCCESS)
        assertFalse(shadow.wasResourcePlayed(R.raw.ui_success))
        sounds.stop()
        audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
        sounds.play(ShaiSounds.Cue.SUCCESS)
        assertFalse(shadow.wasResourcePlayed(R.raw.ui_success))
    }
}
