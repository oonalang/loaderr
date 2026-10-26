package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/** Clips the image itself, including Glide replacements and transparent artwork. */
class RoundedArtworkView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {
    private val clip = Path()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val radius = 16f * resources.displayMetrics.density
        clip.reset()
        clip.addRoundRect(RectF(0f, 0f, w.toFloat(), h.toFloat()), radius, radius, Path.Direction.CW)
    }
    override fun draw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(clip)
        super.draw(canvas)
        canvas.restoreToCount(save)
    }
}
