package astral.loader

import android.graphics.Bitmap
import android.graphics.Canvas
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], qualifiers = "w390dp-h844dp-xxhdpi")
class ClaySurfaceProbeTest {

    @Test
    fun claySurfaceRoundsItsCornersAndCastsAShadow() {
        val context = RuntimeEnvironment.getApplication()
        println("density=" + context.resources.displayMetrics.density)
        val drawable = PhaseSurfaceDrawable(context, PhaseSurfaceDrawable.Kind.CARD)
        drawable.phase = 1f
        drawable.setBounds(0, 0, 900, 300)
        val bitmap = Bitmap.createBitmap(900, 300, Bitmap.Config.ARGB_8888)
        drawable.draw(Canvas(bitmap))
        val corner = bitmap.getPixel(6, 6)
        // Inside the reserved inset but outside the face: the shadow lives here.
        val inset = bitmap.getPixel(12, 150)
        val face = bitmap.getPixel(450, 150)
        println("corner=%08x inset=%08x face=%08x".format(corner, inset, face))
        assertEquals("corner is outside the moulded form", 0, android.graphics.Color.alpha(corner))
        assertEquals("face is the raised clay", 255, android.graphics.Color.alpha(face))
        assertTrue("face is the raised clay", android.graphics.Color.red(face) > 240)
        val shade = android.graphics.Color.alpha(inset)
        assertTrue("shadow falls inside the bounds, was $shade", shade in 1..250)
    }
}
