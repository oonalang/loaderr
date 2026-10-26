package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.widget.RadioGroup

/** Native radio semantics with one moving, slightly stretching clay backing. */
class LiquidModeGroup @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : RadioGroup(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()
    private var previous = 0f
    private var target = 0f
    private val motion = MotionValue(this, 0f) { invalidate() }
    init { setWillNotDraw(false) }
    fun select(id: Int, animate: Boolean = true) {
        previous = motion.value
        target = if (id == R.id.radio_manual) 1f else 0f
        motion.to(target, animate, InteractionMotion.SPRING_MS)
    }
    fun settle() = motion.finish()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val left = findViewById<android.view.View>(R.id.radio_auto) ?: return
        val right = findViewById<android.view.View>(R.id.radio_manual) ?: return
        if (left.width == 0) return
        val d = resources.displayMetrics.density
        val x = motion.value.coerceIn(-.02f, 1.02f)
        val progress = if (target == previous) 1f else ((x - previous) / (target - previous)).coerceIn(0f,1f)
        val stretch = kotlin.math.sin(progress * Math.PI).toFloat() * 3f * d
        rect.set(left.left + (right.left - left.left) * x - stretch, left.top + 2*d,
            left.right + (right.right - left.right) * x + stretch, left.bottom - 2*d)
        paint.color = ShaiPalette.blend(ShaiPalette.clayPeri, ShaiPalette.clayInk, .12f)
        canvas.drawRoundRect(rect.left,rect.top+2*d,rect.right,rect.bottom+2*d,13*d,13*d,paint)
        paint.color = ShaiPalette.clayPeri
        canvas.drawRoundRect(rect,13*d,13*d,paint)
        paint.style = Paint.Style.STROKE; paint.strokeWidth = 1.5f*d
        paint.color = ShaiPalette.blend(ShaiPalette.clayPeri, android.graphics.Color.WHITE,.45f)
        canvas.drawRoundRect(rect,13*d,13*d,paint)
        paint.style = Paint.Style.FILL
    }
}
