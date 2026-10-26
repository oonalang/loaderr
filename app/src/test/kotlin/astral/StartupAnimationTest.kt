package astral.loader

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AnimatedVectorDrawable
import android.view.ContextThemeWrapper
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

/** Starts the resource explicitly: a snapshot alone never resolves its targets. */
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [28, 29, 35])
class StartupAnimationTest {

    @Test
    fun entranceArrowResolvesItsTargetsAndStarts() {
        val context = ContextThemeWrapper(RuntimeEnvironment.getApplication(), R.style.AppTheme)
        val arrow = context.getDrawable(R.drawable.avd_sketch_arrow) as AnimatedVectorDrawable
        val bitmap = Bitmap.createBitmap(80, 80, Bitmap.Config.ARGB_8888)
        arrow.setBounds(0, 0, bitmap.width, bitmap.height)
        try {
            arrow.start()
            arrow.draw(Canvas(bitmap))
        } finally {
            arrow.stop()
            bitmap.recycle()
        }
    }
}
