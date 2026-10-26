package astral.loader

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.time.Duration

/**
 * Renders the two opening pages on the JVM. This project is built on a phone,
 * with no emulator available, so these frames are the only real rendering of
 * the layouts during development: they also prove `onCreate` completes.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w390dp-h844dp-xxhdpi")
class PageRenderTest {

    private val outputDir = File("build/reports/ui").apply { mkdirs() }

    @Test
    fun splashPageRendersOnTheJvm() {
        val controller = Robolectric.buildActivity(SplashActivity::class.java).create()
        // The cover is already finished here; the app itself leaves as soon as
        // the launch checks settle, so this frame is the intended composition.
        controller.get().findViewById<ShaiIllustrationView>(R.id.splash_art).completeReveal()
        (controller.get().findViewById<android.widget.TextView>(R.id.splash_brand).background
            as HighlightSweepDrawable).progress = 1f
        val bitmap = capture(controller.get(), "splash.png")
        // Paper, not the dark window background: one frame is already the sheet.
        assertPaper(bitmap)
    }

    @Test
    fun keyPageRendersAndStartupCompletes() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        // Let the entrance animators and the illustration reveal settle.
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        val bitmap = capture(controller.get(), "key-page.png")
        assertPaper(bitmap)
        assertTrue("illustration never revealed", controller.get()
            .findViewById<ShaiIllustrationView>(R.id.hero_art).reveal > 0.99f)
    }

    @Test
    @Config(sdk = [34], qualifiers = "w360dp-h640dp-xxhdpi")
    fun keyPageFitsANarrowPhone() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        val bitmap = capture(controller.get(), "key-page-narrow.png")
        assertPaper(bitmap)
        val login = controller.get().findViewById<android.view.View>(R.id.login_button)
        val field = controller.get().findViewById<android.view.View>(R.id.key_field)
        assertTrue("primary action clipped", login.right <= bitmap.width)
        assertTrue("field clipped", field.right <= bitmap.width)
    }

    @Test
    fun courtPhaseKeepsTheSameDrawing() {
        val controller = Robolectric.buildActivity(MainActivity::class.java).setup()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        // Phase 1 without a real redemption: this is the palette/crest blend only.
        val updateTheme = MainActivity::class.java
            .getDeclaredMethod("updateTheme", Float::class.javaPrimitiveType)
            .apply { isAccessible = true }
        updateTheme.invoke(controller.get(), 1f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(600))
        capture(controller.get(), "court-page.png")
        val art = controller.get().findViewById<ShaiIllustrationView>(R.id.hero_art)
        assertEquals("crest is fully inked in the court", 1f, art.phase, 0.001f)
    }

    private fun capture(activity: Activity, name: String): Bitmap {
        val root = activity.window.decorView
        val metrics = activity.resources.displayMetrics
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        root.measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        )
        root.layout(0, 0, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        root.draw(Canvas(bitmap))
        File(outputDir, name).outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return bitmap
    }

    private fun assertPaper(bitmap: Bitmap) {
        val px = bitmap.getPixel(4, bitmap.height / 2)
        assertEquals("paper fill", 253, android.graphics.Color.red(px))
        assertEquals("paper fill", 252, android.graphics.Color.green(px))
        assertEquals("paper fill", 247, android.graphics.Color.blue(px))
    }
}
