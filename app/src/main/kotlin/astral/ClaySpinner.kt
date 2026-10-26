package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatSpinner

/** Keeps the existing Spinner adapter/selection contract; replaces the native popup entirely. */
class ClaySpinner @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatSpinner(context,attrs) {
    private val paint=Paint(Paint.ANTI_ALIAS_FLAG).apply { style=Paint.Style.STROKE;strokeCap=Paint.Cap.ROUND;strokeJoin=Paint.Join.ROUND }
    private val path=Path()
    private val chevron=MotionValue(this,0f) { invalidate() }
    private var dialog: ClayChoiceDialog?=null
    var onChoice: (() -> Unit)?=null
    var materialPhase=1f
    override fun performClick(): Boolean {
        if(!isEnabled || dialog?.isShowing==true) return true
        val values=(0 until (adapter?.count ?: 0)).map { adapter.getItem(it).toString() }
        if(values.isEmpty()) return false
        chevron.to(180f)
        dialog=ClayChoiceDialog(context,contentDescription?.toString() ?: "Choose",values,selectedItemPosition,materialPhase) { index ->
            setSelection(index);onChoice?.invoke()
        }.apply { setOnDismissListener { chevron.to(0f);dialog=null };show() }
        return true
    }
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        val d=resources.displayMetrics.density;val x=width-20*d;val y=height/2f
        canvas.save();canvas.rotate(chevron.value,x,y)
        path.reset();path.moveTo(x-4*d,y-2*d);path.lineTo(x,y+2*d);path.lineTo(x+4*d,y-2*d)
        paint.color=if(materialPhase>.5f) ShaiPalette.clayMuted else ShaiPalette.pencil;paint.strokeWidth=1.8f*d
        canvas.drawPath(path,paint);canvas.restore()
    }
    fun closeChoices() { dialog?.closeImmediately();dialog=null;chevron.to(0f,false) }
    override fun onDetachedFromWindow() { closeChoices();super.onDetachedFromWindow() }
}
