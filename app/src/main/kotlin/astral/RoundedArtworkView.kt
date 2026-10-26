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
    private val skeletonPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
    private var pulseAnimator: android.animation.ValueAnimator? = null
    private var pulse = 0f
    var loading: Boolean = false
        set(value) { field = value; syncLoading(); invalidate() }
    private fun syncLoading() {
        pulseAnimator?.cancel(); pulseAnimator = null
        if (!loading || !isAttachedToWindow || !isShown || windowVisibility != VISIBLE || !android.animation.ValueAnimator.areAnimatorsEnabled()) return
        pulseAnimator = android.animation.ValueAnimator.ofFloat(0f, 1f).apply {
            duration = InteractionMotion.BREATH_MS
            repeatMode = android.animation.ValueAnimator.REVERSE
            repeatCount = android.animation.ValueAnimator.INFINITE
            addUpdateListener { pulse = it.animatedValue as Float; invalidate() }
            start()
        }
    }
    override fun onAttachedToWindow() { super.onAttachedToWindow(); syncLoading() }
    override fun onVisibilityAggregated(isVisible: Boolean) { super.onVisibilityAggregated(isVisible); syncLoading() }
    override fun onWindowVisibilityChanged(visibility: Int) { super.onWindowVisibilityChanged(visibility); syncLoading() }
    override fun onDetachedFromWindow() { pulseAnimator?.cancel(); pulseAnimator = null; super.onDetachedFromWindow() }
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
        if (loading) {
            skeletonPaint.color = ShaiPalette.blend(ShaiPalette.claySunken, ShaiPalette.clayRaised, .2f + .3f * pulse)
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), skeletonPaint)
        }
        canvas.restoreToCount(save)
    }
}
