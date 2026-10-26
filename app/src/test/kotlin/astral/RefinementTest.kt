package astral.loader

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.time.Duration

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w390dp-h844dp-xxhdpi")
class RefinementTest {

    private val outputDir = File("build/reports/ui").apply { mkdirs() }

    @Test
    fun rotatingHeadlineStartsInOrderAndReservesTwoLines() {
        assertEquals(
            listOf(
                "Shai Premium is better.",
                "Shai updates every week!",
                "Buy now!",
                "Suggest more feats!",
                "Hey did you know?",
                "Promo drops every week too!"
            ),
            RotatingHeadlineView.MESSAGES.toList()
        )
        val context = RuntimeEnvironment.getApplication()
        val headline = RotatingHeadlineView(context)
        assertEquals("Shai Premium is better.", headline.text.toString())
        headline.setMessageForTest(RotatingHeadlineView.BUY_NOW_INDEX)
        headline.measure(
            View.MeasureSpec.makeMeasureSpec(900, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(400, View.MeasureSpec.AT_MOST)
        )
        headline.layout(0, 0, headline.measuredWidth, headline.measuredHeight)
        assertTrue(
            "reserved height must cover two lines",
            headline.height >= (30f * context.resources.displayMetrics.scaledDensity * 2).toInt()
        )
        val bitmap = Bitmap.createBitmap(
            headline.width.coerceAtLeast(1),
            headline.height.coerceAtLeast(1),
            Bitmap.Config.ARGB_8888
        )
        headline.draw(Canvas(bitmap))
        var highlightRight = -1
        var highlightPixels = 0
        for (x in 0 until bitmap.width) {
            for (y in 0 until bitmap.height) {
                val px = bitmap.getPixel(x, y)
                if (Color.alpha(px) > 0 && Color.red(px) > 220 &&
                    Color.green(px) > 200 && Color.blue(px) < 180
                ) {
                    highlightPixels++
                    if (x > highlightRight) highlightRight = x
                }
            }
        }
        assertTrue("the highlighter is drawn", highlightPixels > 100)
        assertTrue(
            "the marker follows the measured text, not the full view width",
            highlightRight in 1 until headline.width - 8
        )
    }

    @Test
    fun onlyBuyNowOpensTheExactStoreUrlAndOnlyOnce() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
        val headline = activity.findViewById<RotatingHeadlineView>(R.id.hero_headline)

        headline.setMessageForTest(1)
        assertFalse("an ordinary tip is not a link", headline.isClickable)
        headline.performClick()
        assertNull(shadowOf(activity).nextStartedActivity)

        headline.setMessageForTest(RotatingHeadlineView.BUY_NOW_INDEX)
        assertTrue("Buy now is actionable", headline.isClickable)
        assertTrue(headline.performClick())
        val first = shadowOf(activity).nextStartedActivity
        assertNotNull("Buy now starts an external intent", first)
        assertEquals("https://shaistore.vercel.app", first!!.data.toString())

        headline.performClick()
        assertNull("a double tap cannot open a second tab", shadowOf(activity).nextStartedActivity)
    }

    @Test
    fun authenticationStopsTheTipLoopAndShowsTheFinishedHeadline() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
        activity.setPrivate("licenseVerified", true)
        activity.findViewById<View>(R.id.license_section).visibility = View.GONE
        activity.findViewById<View>(R.id.success_section).visibility = View.VISIBLE
        activity.invoke("setSignedInControlsVisible", true)
        activity.invoke("updateTheme", 1f)
        val headline = activity.findViewById<RotatingHeadlineView>(R.id.hero_headline)
        assertEquals("Access granted.", headline.text.toString())
        assertFalse(headline.isClickable)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(5))
        assertEquals(
            "the loop stops as soon as the key is accepted",
            "Access granted.",
            headline.text.toString()
        )
    }

    @Test
    fun aboutSheetRendersAsClayWithTheKeykeeperAndIntegratedCredit() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        val dialog = AboutDialog(activity, true)
        dialog.show()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(1))
        val root = dialog.window!!.decorView
        val metrics = activity.resources.displayMetrics
        root.measure(
            View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(metrics.heightPixels, View.MeasureSpec.EXACTLY)
        )
        root.layout(0, 0, metrics.widthPixels, metrics.heightPixels)
        val bitmap = Bitmap.createBitmap(metrics.widthPixels, metrics.heightPixels, Bitmap.Config.ARGB_8888)
        root.draw(Canvas(bitmap))
        File(outputDir, "about-clay.png").outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        val audit = StringBuilder()
        dump(root, audit, 0)
        File(outputDir, "layout-about.txt").writeText(audit.toString())
        val background = bitmap.getPixel(4, 4)
        assertEquals("clay page", 244, Color.red(background))
        assertEquals("clay page", 238, Color.green(background))
        assertEquals("clay page", 255, Color.blue(background))
        val art = dialog.findViewById<ShaiIllustrationView>(R.id.about_art)
        assertEquals("About uses the same finished keykeeper", 1f, art.phase, 0.001f)
        val back = dialog.findViewById<View>(R.id.about_back)
        assertTrue("back control keeps a 48dp target", back.height >= 48 * metrics.density - 1)
        val credit = dialog.findViewById<TextView>(R.id.about_credit)
        assertEquals(View.VISIBLE, credit.visibility)
        assertEquals("Powered by @Shaiaintshyy", credit.text.toString())
        assertTrue("the credit is part of the page, not a footer bar",
            credit.background == null)
        dialog.dismiss()
    }

    @Test
    fun signedInPageKeepsFunctionalBoundsInsideThePage() {
        val activity = Robolectric.buildActivity(MainActivity::class.java).setup().get()
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
        activity.setPrivate("licenseVerified", true)
        activity.findViewById<View>(R.id.license_section).visibility = View.GONE
        activity.findViewById<View>(R.id.success_section).visibility = View.VISIBLE
        activity.invoke("setSignedInControlsVisible", true)
        activity.invoke("updateTheme", 1f)
        val root = activity.window.decorView
        val metrics = activity.resources.displayMetrics
        root.measure(
            View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(metrics.heightPixels, View.MeasureSpec.EXACTLY)
        )
        root.layout(0, 0, metrics.widthPixels, metrics.heightPixels)

        val content = activity.findViewById<View>(R.id.main_content)
        val primary = activity.findViewById<View>(R.id.login_button)
        assertTrue(primary.left >= content.left && primary.right <= content.right)
        assertTrue("primary keeps a real touch target", primary.height >= 48 * metrics.density)
        val card = activity.findViewById<View>(R.id.library_card)
        val panel = activity.findViewById<View>(R.id.operation_section)
        assertTrue("a card cannot overlap the panel", panel.top >= card.bottom)
        val about = activity.findViewById<View>(R.id.about_button)
        assertTrue("About keeps a 48dp target", about.height >= 48 * metrics.density - 1)
        val status = activity.findViewById<TextView>(R.id.library_status)
        assertEquals("redundant choose-state chip stays hidden", View.GONE, status.visibility)
        val header = activity.findViewById<TextView>(R.id.sheet_note)
        assertEquals("verified state is not repeated in the header", View.GONE, header.visibility)

        val audit = StringBuilder()
        dump(root, audit, 0)
        File(outputDir, "layout-signed-in.txt").writeText(audit.toString())
    }

    private fun MainActivity.setPrivate(name: String, value: Any) {
        MainActivity::class.java.getDeclaredField(name).apply { isAccessible = true }.set(this, value)
    }

    private fun MainActivity.invoke(name: String, vararg args: Any?) {
        val method = MainActivity::class.java.declaredMethods.first {
            it.name == name && it.parameterTypes.size == args.size
        }
        method.isAccessible = true
        method.invoke(this, *args)
    }

    private fun RotatingHeadlineView.setMessageForTest(index: Int) {
        val field = RotatingHeadlineView::class.java.getDeclaredField("messageIndex")
        field.isAccessible = true
        field.set(this, index)
        val apply = RotatingHeadlineView::class.java.getDeclaredMethod("applyMessage")
        apply.isAccessible = true
        apply.invoke(this)
    }

    private fun dump(view: View, out: StringBuilder, depth: Int) {
        val visibility = when (view.visibility) {
            View.VISIBLE -> "V"
            View.INVISIBLE -> "I"
            else -> "G"
        }
        val id = try {
            view.resources.getResourceEntryName(view.id)
        } catch (_: Exception) {
            "no-id"
        }
        val text = (view as? TextView)?.text?.toString()?.replace('\n', ' ')?.take(80)
        val size = if (view is TextView) {
            " text=${view.textSize / view.resources.displayMetrics.density}sp"
        } else ""
        out.append("  ".repeat(depth))
            .append("$visibility $id ${view.javaClass.simpleName} ")
            .append("[${view.left},${view.top},${view.right},${view.bottom}]")
            .append(" ${view.width}x${view.height}")
            .append(size)
        if (text != null) out.append(" \"$text\"")
        out.append('\n')
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) dump(view.getChildAt(i), out, depth + 1)
        }
    }
}
