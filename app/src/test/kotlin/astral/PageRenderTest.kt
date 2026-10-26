package astral.loader

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Looper
import android.view.View
import org.json.JSONObject
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
 * Renders the pages on the JVM. This project is built on a phone, with no
 * emulator available, so these frames are the only real rendering of the
 * layouts during development: they also prove `onCreate` completes.
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
    fun clayPageRendersTheFinishedObject() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        // A real redemption cannot happen offline, so the authenticated state is
        // entered the way the activity itself enters it: the flag, then the phase.
        activity.setPrivate("licenseVerified", true)
        // The redemption path hides the key form; the test enters that state too.
        activity.findViewById<View>(R.id.license_section).visibility = View.GONE
        activity.findViewById<View>(R.id.success_section).visibility = View.VISIBLE
        activity.invoke("setSignedInControlsVisible", true)
        activity.invoke("updateTheme", 1f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(600))
        val bitmap = capture(activity, "clay-page.png")
        assertClay(bitmap)
        val art = activity.findViewById<ShaiIllustrationView>(R.id.hero_art)
        assertEquals("crest is fully sculpted", 1f, art.phase, 0.001f)
        assertEquals("headline switches with the material", "Access granted.",
            activity.findViewById<android.widget.TextView>(R.id.hero_headline).text.toString())
        val login = activity.findViewById<View>(R.id.login_button)
        val content = activity.findViewById<View>(R.id.main_content)
        assertTrue("primary action clipped", login.right <= content.right)
        assertTrue("surface shadow room kept", login.height >= 64)
    }

    @Test
    fun clayDrawerRendersLibraryRows() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        activity.setPrivate("licenseVerified", true)
        activity.addLibrary(
            """{"NAME":"Vanguard","GAME":"com.activision.callofduty.shooter",
               "TYPE":"CODM","VERSION":"1.9.42","STATUS":"ACTIVE"}"""
        )
        activity.addLibrary(
            """{"NAME":"Nightfall","GAME":"com.activision.callofduty.shooter",
               "TYPE":"CODM","VERSION":"1.9.40","MAINTENANCE":true}"""
        )
        activity.invoke("setSignedInControlsVisible", true)
        activity.invoke("updateTheme", 1f)
        activity.invoke("refreshLibraryDrawer", "")
        activity.invoke("setLibraryDrawerOpen", true)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(900))
        capture(activity, "clay-drawer.png")
        val list = activity.findViewById<android.widget.LinearLayout>(R.id.library_list)
        assertEquals("two library rows render", 2, list.childCount)
        assertTrue("rows are moulded surfaces", list.getChildAt(0).background is PhaseSurfaceDrawable)
    }

    @Test
    fun materialChangeMidpointKeepsBothVoices() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(3))
        activity.invoke("updateTheme", 0.5f)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(300))
        val bitmap = capture(activity, "clay-mid.png")
        // Halfway is neither paper nor clay: the page is between the two fills.
        val px = bitmap.getPixel(4, bitmap.height / 2)
        val red = android.graphics.Color.red(px)
        assertTrue("midpoint is between paper and clay, was $red", red in 245..252)
        val art = activity.findViewById<ShaiIllustrationView>(R.id.hero_art)
        assertEquals("crest is mid-sculpt", 0.5f, art.phase, 0.001f)
    }

    private fun MainActivity.setPrivate(name: String, value: Any) {
        MainActivity::class.java.getDeclaredField(name).apply { isAccessible = true }.set(this, value)
    }

    @Suppress("UNCHECKED_CAST")
    private fun MainActivity.addLibrary(json: String) {
        val field = MainActivity::class.java.getDeclaredField("allLibraries")
        field.isAccessible = true
        (field.get(this) as MutableList<JSONObject>).add(JSONObject(json))
    }

    /** Reflection is deliberate: this test drives the real state transition, not a copy of it. */
    private fun MainActivity.invoke(name: String, vararg args: Any?) {
        val method = MainActivity::class.java.declaredMethods.first {
            it.name == name && it.parameterTypes.size == args.size
        }
        method.isAccessible = true
        method.invoke(this, *args)
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

    private fun assertClay(bitmap: Bitmap) {
        val px = bitmap.getPixel(4, bitmap.height / 2)
        assertEquals("clay fill", 244, android.graphics.Color.red(px))
        assertEquals("clay fill", 238, android.graphics.Color.green(px))
        assertEquals("clay fill", 255, android.graphics.Color.blue(px))
    }
}
