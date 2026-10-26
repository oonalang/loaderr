package astral.loader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/** Breathing placeholders only while a real request is pending and the view is visible. */
class SkeletonView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context,attrs) {
    private val paint=Paint(Paint.ANTI_ALIAS_FLAG)
    private var pulse=0f
    private var animator:ValueAnimator?=null
    override fun onDraw(canvas:Canvas) {
        val d=resources.displayMetrics.density
        paint.color=ShaiPalette.blend(ShaiPalette.claySunken,ShaiPalette.clayRaised,.15f+.25f*pulse)
        canvas.drawRoundRect(12*d,12*d,68*d,68*d,16*d,16*d,paint)
        canvas.drawRoundRect(82*d,18*d,width-18*d,33*d,7*d,7*d,paint)
        canvas.drawRoundRect(82*d,45*d,width-48*d,56*d,5*d,5*d,paint)
    }
    private fun sync() {
        animator?.cancel();animator=null
        if(!isAttachedToWindow || !isShown || windowVisibility!=VISIBLE || !ValueAnimator.areAnimatorsEnabled()) return
        animator=ValueAnimator.ofFloat(0f,1f).apply {
            duration=InteractionMotion.BREATH_MS;repeatMode=ValueAnimator.REVERSE;repeatCount=ValueAnimator.INFINITE
            addUpdateListener { pulse=it.animatedValue as Float;invalidate() };start()
        }
    }
    override fun onAttachedToWindow() { super.onAttachedToWindow();sync() }
    override fun onVisibilityAggregated(isVisible:Boolean) { super.onVisibilityAggregated(isVisible);sync() }
    override fun onWindowVisibilityChanged(visibility:Int) { super.onWindowVisibilityChanged(visibility);sync() }
    override fun onDetachedFromWindow() { animator?.cancel();animator=null;super.onDetachedFromWindow() }
}
