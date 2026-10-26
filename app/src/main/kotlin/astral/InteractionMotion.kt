package astral.loader

import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sqrt

/** A bounded spring response: damping .82, natural response 320ms, no perpetual physics loop. */
internal object InteractionMotion {
    const val CHANGE_MS = 240L
    const val SPRING_MS = 360L
    const val REACTION_MS = 720L
    const val BREATH_MS = 1100L
    val spring = Interpolator { t ->
        if (t >= 1f) 1f else {
            val damping = .82
            val omega = 12.0
            val wd = omega * sqrt(1 - damping * damping)
            (1 - exp(-damping * omega * t) * (cos(wd * t) + damping / sqrt(1 - damping * damping) * kotlin.math.sin(wd * t))).toFloat()
        }
    }
    fun press(view: View) {
        view.setOnTouchListener { target, event ->
            if (target.isEnabled && target.isClickable) when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> scale(target, .975f, ShaiMotion.PRESS_IN_MS)
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scale(target, 1f, ShaiMotion.PRESS_OUT_MS)
            }
            false
        }
    }
    private fun scale(view: View, value: Float, duration: Long) {
        view.animate().cancel()
        if (!ValueAnimator.areAnimatorsEnabled()) { view.scaleX = value; view.scaleY = value; return }
        view.animate().scaleX(value).scaleY(value).setStartDelay(0).setDuration(duration)
            .setInterpolator(ShaiMotion.easeOut).start()
    }
    fun reveal(view: View) {
        view.animate().cancel()
        rest(view, 1f, ty = 0f)
        if (!ValueAnimator.areAnimatorsEnabled()) { view.alpha = 1f; view.translationY = 0f; return }
        view.alpha = 0f; view.translationY = 6f * view.resources.displayMetrics.density
        view.animate().alpha(1f).translationY(0f).setStartDelay(0).setDuration(CHANGE_MS)
            .setInterpolator(ShaiMotion.easeOut).start()
    }

    /**
     * Records the values a view must hold once its animation is over, so a
     * cancelled animation can be settled to its destination instead of being
     * frozen halfway. Views never animated here keep the old behaviour of
     * cancellation: their own properties (a deliberately dimmed control, for
     * instance) are left alone.
     */
    fun rest(view: View, alpha: Float, tx: Float = 0f, ty: Float = 0f) {
        view.setTag(R.id.anim_rest, floatArrayOf(alpha, tx, ty))
    }

    fun cancelTree(view: View) {
        settleNode(view)
        if (view is ViewGroup) for (i in 0 until view.childCount) cancelTree(view.getChildAt(i))
    }

    private fun settleNode(view: View) {
        view.animate().cancel()
        // Drop the listener so a cancelled animation cannot run its own
        // end-action afterwards and undo this settlement.
        view.animate().setListener(null)
        (view.getTag(R.id.anim_rest) as? FloatArray)?.let { rest ->
            view.alpha = rest[0]
            view.translationX = rest[1]
            view.translationY = rest[2]
        }
        view.scaleX = 1f; view.scaleY = 1f
    }
}

/** Animates a scalar from its current presentation; detachment settles and releases it. */
internal class MotionValue(owner: View, initial: Float, private val draw: (Float) -> Unit) {
    var value = initial; private set
    private var target = initial
    private var animator: ValueAnimator? = null
    init {
        owner.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) = Unit
            override fun onViewDetachedFromWindow(v: View) { finish() }
        })
    }
    fun to(next: Float, animate: Boolean = true, duration: Long = InteractionMotion.CHANGE_MS) {
        animator?.cancel(); target = next
        if (!animate || !ValueAnimator.areAnimatorsEnabled()) { value = next; draw(value); return }
        animator = ValueAnimator.ofFloat(value, next).apply {
            this.duration = duration
            interpolator = InteractionMotion.spring
            addUpdateListener { value = it.animatedValue as Float; draw(value) }
            start()
        }
    }
    fun finish() { animator?.cancel(); animator = null; value = target; draw(value) }
}
