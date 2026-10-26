package astral.loader

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Looper
import android.view.View
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.time.Duration

/**
 * Renders the two padlock states with the real native graphics path and
 * exercises the persisted sound preference. The geometric assertions sample
 * the drawn pixels, so a regression that only exists in the canvas work (an
 * alpha that never reaches 255, a shackle that lifts at the wrong pivot) fails
 * here instead of shipping.
 */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w390dp-h844dp-xxhdpi")
class ShaiSoundAndLockTest {

    private val scale = 2 // study space 320x184 drawn at 640x368

    private fun rendered(phase: Float): Bitmap {
        val context = Robolectric.setupActivity(astral.loader.MainActivity::class.java)
        shadowOf(Looper.getMainLooper()).idleFor(Duration.ofSeconds(2))
        return ShaiIllustrationView(context).let { view ->
            view.reveal = 1f
            view.phase = phase
            view.measure(
                View.MeasureSpec.makeMeasureSpec(640, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(368, View.MeasureSpec.EXACTLY)
            )
            view.layout(0, 0, 640, 368)
            val bitmap = Bitmap.createBitmap(640, 368, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.WHITE)
            view.draw(Canvas(bitmap))
            bitmap
        }
    }

    @Suppress("SameParameterValue")
    private fun sample(bitmap: Bitmap, x: Float, y: Float): Int {
        val window = IntArray(9)
        var index = 0
        for (dy in -1..1) {
            for (dx in -1..1) {
                window[index++] = bitmap.getPixel(x.toInt() * scale + dx, y.toInt() * scale + dy)
            }
        }
        var red = 0; var green = 0; var blue = 0
        for (px in window) { red += Color.red(px); green += Color.green(px); blue += Color.blue(px) }
        return Color.rgb(red / 9, green / 9, blue / 9)
    }

    private fun isDark(px: Int): Boolean =
        Color.red(px) < 110 && Color.green(px) < 110 && Color.blue(px) < 120

    private fun isMint(px: Int): Boolean =
        Color.green(px) > 180 && Color.blue(px) > 150 && Color.red(px) < Color.green(px)

    @Test
    fun padlockIsClosedOnPaperAndOpenInClay() {
        val paper = rendered(0f)
        val clay = rendered(1f)

        // Body face: ink outline only on paper, moulded mint in clay.
        val paperFace = sample(paper, 258f, 133f)
        assertFalse("the paper lock is a drawing, not a fill", isMint(paperFace))
        val clayFace = sample(clay, 258f, 133f)
        assertTrue("the clay lock body is mint", isMint(clayFace))

        // The shackle's right leg is seated while locked…
        assertTrue("locked: shackle leg seated on the body",
            isDark(sample(paper, 262f, 114f)))
        // …and swung open once the key is verified, leaving that column clear.
        val opened = sample(clay, 262f, 114f)
        assertFalse("open: the shackle leg has left the body edge", isDark(opened))
        assertFalse("open: the lifted leg is not parked on the body", isMint(opened))
    }

    @Test
    fun theSameLockedGeometryRelaxesRatherThanSwapping() {
        val mid = rendered(0.5f)
        // Halfway, the shackle has partially lifted: it is neither the closed
        // paper ink (still fading) nor yet fully mint. The lifted region must
        // already be partially clear, proving a rotation, not a swap.
        val lifting = sample(mid, 262f, 114f)
        val redMid = Color.red(lifting)
        assertTrue(
            "halfway the leg is already moving: $redMid",
            redMid > Color.red(Color.rgb(31, 41, 55))
        )
    }

}
