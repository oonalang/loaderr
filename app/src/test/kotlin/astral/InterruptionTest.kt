package astral.loader

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Looper
import android.view.View
import android.widget.TextView
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.Duration
import kotlin.math.min

/**
 * Interruption behaviour: cancelled animations settle on their destination,
 * theme crossfades land on the saved selection, the drawer opens the same way
 * on its first pass, and navigation taps keep their audio cue.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class InterruptionTest {

    private fun start(): Pair<ActivityController<MainActivity>, MainActivity> {
        val controller = Robolectric.buildActivity(MainActivity::class.java)
        return controller to controller.setup().get()
    }

    private fun invokeThemeTransition(activity: MainActivity) {
        MainActivity::class.java.getDeclaredMethod("animateTheme").apply {
            isAccessible = true
        }.invoke(activity)
    }

    private fun notebookFractionOf(activity: MainActivity): Float {
        val field = MainActivity::class.java.getDeclaredField("notebookFraction")
        field.isAccessible = true
        return field.getFloat(activity)
    }

    private fun closedOffset(activity: MainActivity): Float {
        val dp = activity.resources.displayMetrics.density
        val width = min(
            (340 * dp).toInt(),
            activity.resources.displayMetrics.widthPixels - (20 * dp).toInt()
        )
        return -((width + (10 * dp).toInt())).toFloat()
    }

    private fun idleMillis(millis: Long) =
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(millis))

    @Test fun cancelledEntranceSettlesAndLeavesDeliberateDimmingAlone() {
        val (_, activity) = start()
        val animated = activity.findViewById<View>(R.id.brand_title)
        InteractionMotion.reveal(animated)
        // Freeze an interruption in mid-flight directly (Robolectric's animator
        // clock is not a real clock), owning the registration is what matters.
        animated.alpha = .3f
        animated.translationY = 12f

        val dimmed = activity.findViewById<View>(R.id.progressBar)
        dimmed.alpha = .5f

        InteractionMotion.cancelTree(activity.findViewById<View>(R.id.main_content))
        assertEquals(1f, animated.alpha)
        assertEquals(0f, animated.translationY, 0.001f)
        assertEquals(1f, animated.scaleX, 0.001f)
        assertEquals(1f, animated.scaleY, 0.001f)
        // Views that were never animated are not rewritten: a deliberately dim
        // control stays dim after every cancellation.
        assertEquals(.5f, dimmed.alpha, 0.0001f)
        // Repeated cancellation stays idempotent.
        InteractionMotion.cancelTree(activity.findViewById<View>(R.id.main_content))
        assertEquals(1f, animated.alpha)
    }

    @Test fun cancelledErrorRevealKeepsTheErrorTextVisible() {
        val (_, activity) = start()
        val errorLine = activity.findViewById<TextView>(R.id.key_error)
        errorLine.text = "Check the key and try again."
        errorLine.visibility = View.VISIBLE
        InteractionMotion.reveal(errorLine)
        idleMillis(60)
        InteractionMotion.cancelTree(activity.findViewById<View>(R.id.main_content))
        assertEquals(1f, errorLine.alpha, 0.001f)
        assertEquals("Check the key and try again.", errorLine.text.toString())
        assertEquals(View.VISIBLE, errorLine.visibility)
    }

    @Test fun interruptedThemeTransitionSettlesOnTheSavedSelection() {
        val (controller, activity) = start()
        LoaderPreferences(activity).saveTheme("midnight")
        invokeThemeTransition(activity)
        idleMillis(80)
        assertTrue("transition was mid-blend", ShaiPalette.night in 0f..1f)

        controller.pause(); controller.stop()
        assertEquals(1f, ShaiPalette.night)
        assertEquals(0f, notebookFractionOf(activity), 0.001f)

        // Reentering replays no transition.
        val settled = ShaiPalette.night
        controller.start()
        assertEquals(settled, ShaiPalette.night)
        controller.destroy()
    }

    @Test fun recreatedActivityKeepsTheSavedSelectionAfterAnInterruptedCrossfade() {
        val (controller, activity) = start()
        LoaderPreferences(activity).saveTheme("notebook")
        invokeThemeTransition(activity)
        idleMillis(80)
        controller.pause(); controller.stop(); controller.destroy()

        val (_, fresh) = start()
        assertEquals(0f, ShaiPalette.night)
        assertEquals(1f, notebookFractionOf(fresh), 0.001f)
    }

    @Test fun firstDrawerOpeningSlidesTheFullConfiguredWidth() {
        val (_, activity) = start()
        shadowOf(Looper.getMainLooper()).idle()
        val drawer = activity.findViewById<View>(R.id.library_drawer)
        val closed = closedOffset(activity)
        // Parked before the first opening, even though the drawer was never laid out.
        assertEquals(closed, drawer.translationX, 1f)
        assertEquals(View.GONE, drawer.visibility)

        activity.findViewById<View>(R.id.library_card).performClick()
        assertEquals(View.VISIBLE, drawer.visibility)
        // The slide starts from the park position, not from ≈0 (native first-opening bug).
        assertEquals(closed, drawer.translationX, 1.5f)
        idleMillis(400)
        assertEquals(0f, drawer.translationX, 1.5f)

        // Rapid close then open keeps the panel coherent.
        activity.findViewById<View>(R.id.drawer_close).performClick()
        idleMillis(40)
        activity.findViewById<View>(R.id.library_card).performClick()
        idleMillis(400)
        assertEquals(View.VISIBLE, drawer.visibility)
        assertEquals(0f, drawer.translationX, 2f)
    }

    @Test fun backgroundingAMidFlightDrawerCloseParksThePanel() {
        val (controller, activity) = start()
        activity.findViewById<View>(R.id.library_card).performClick()
        idleMillis(400)
        activity.findViewById<View>(R.id.drawer_close).performClick()
        idleMillis(50)

        controller.pause(); controller.stop()
        val drawer = activity.findViewById<View>(R.id.library_drawer)
        val scrim = activity.findViewById<View>(R.id.drawer_scrim)
        assertEquals(View.GONE, drawer.visibility)
        assertEquals(closedOffset(activity), drawer.translationX, 1f)
        assertEquals(View.GONE, scrim.visibility)
        assertEquals(0f, scrim.alpha, 0.001f)
    }

    @Test fun navigationActionsEachPlayOneTapCue() {
        val (controller, activity) = start()
        val context = RuntimeEnvironment.getApplication()
        val audio = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
        audio.setStreamVolume(AudioManager.STREAM_SYSTEM, 5, 0)
        audio.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0)

        val delegate = MainActivity::class.java.getDeclaredField("sounds\$delegate")
        delegate.isAccessible = true
        val sounds = (delegate.get(activity) as Lazy<*>).value as ShaiSounds
        fun shadowOfPool(): org.robolectric.shadows.ShadowSoundPool? {
            val current = ShaiSounds::class.java.getDeclaredField("soundPool").apply {
                isAccessible = true
            }.get(sounds) as? SoundPool ?: return null
            return shadowOf(current)
        }
        sounds.start()
        var shadow = shadowOfPool()!!
        shadow.notifyResourceLoaded(R.raw.ui_tap, true)

        fun playsTap(action: () -> Unit) {
            shadow.clearPlayed()
            action()
            assertTrue("expected a tap cue", shadow.wasResourcePlayed(R.raw.ui_tap))
            // The kit deliberately de-dupes taps inside 110ms; keep subsequent
            // actions out of that window.
            idleMillis(150)
        }

        playsTap { activity.findViewById<View>(R.id.theme_button).performClick() }
        playsTap { activity.findViewById<View>(R.id.change_key_link).performClick() }
        playsTap { activity.findViewById<View>(R.id.about_button).performClick() }
        playsTap { activity.findViewById<View>(R.id.library_card).performClick() }
        idleMillis(400)
        playsTap { activity.findViewById<View>(R.id.drawer_close).performClick() }
        playsTap { activity.findViewById<View>(R.id.drawer_about).performClick() }

        // Reinitialisation: leaving and returning rebuilds the pool and keeps cues.
        shadow.clearPlayed()
        controller.pause(); controller.stop()
        audio.ringerMode = AudioManager.RINGER_MODE_SILENT
        controller.start()
        audio.ringerMode = AudioManager.RINGER_MODE_NORMAL
        shadow = shadowOfPool()!!
        shadow.notifyResourceLoaded(R.raw.ui_tap, true)
        playsTap { activity.findViewById<View>(R.id.library_card).performClick() }
        idleMillis(400)
        controller.destroy()
    }
}
