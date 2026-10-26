package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox

/** The check is drawn into the surface; CompoundButton still owns accessibility and state. */
class AnimatedRememberBox @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatCheckBox(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val part = Path()
    private val measure = PathMeasure()
    private val motion = MotionValue(this, if (isChecked) 1f else 0f) { invalidate() }
    init { buttonDrawable = null; buttonTintList = null }
    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        // CompoundButton's constructor can dispatch this override before fields initialize.
        if (ready()) motion.to(if (checked) 1f else 0f, isAttachedToWindow)
    }
    private fun ready(): Boolean = (motion as MotionValue?) != null
    fun settle() = motion.finish()
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val d = resources.displayMetrics.density
        val x = width * .5f; val y = height * .5f; val r = 10*d
        val progress = motion.value.coerceIn(0f,1f)
        val box = RectF(x-r,y-r,x+r,y+r)
        paint.style = Paint.Style.FILL; paint.color = ShaiPalette.blend(ShaiPalette.paperCard,ShaiPalette.highlight,progress)
        canvas.drawRoundRect(box,4*d,4*d,paint)
        paint.style = Paint.Style.STROKE; paint.strokeWidth = 1.6f*d; paint.color = ShaiPalette.ink
        canvas.drawRoundRect(box,4*d,4*d,paint)
        path.reset(); path.moveTo(x-5*d,y); path.lineTo(x-1*d,y+4*d);path.lineTo(x+6*d,y-5*d)
        measure.setPath(path,false);part.reset();measure.getSegment(0f,measure.length*progress,part,true)
        paint.strokeWidth = 2.4f*d;paint.strokeCap=Paint.Cap.ROUND;paint.strokeJoin=Paint.Join.ROUND
        canvas.drawPath(part,paint)
    }
}
