package astral.loader

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt

/**
 * The key field is a box someone drew on the page, not a filled Material control.
 * Edges are four slightly bowed strokes that overshoot their corners, plus a
 * second, lighter pass at a different offset. Bounds stay exactly where the
 * layout put them, so the control is still geometrically honest for touch.
 */
internal class SketchFieldDrawable(
    private val density: Float = android.content.res.Resources.getSystem().displayMetrics.density
) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val path = Path()
    private val fillRect = RectF()
    private var globalAlpha = 255

    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidateSelf()
        }

    override fun isStateful(): Boolean = true

    override fun onStateChange(state: IntArray): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        val b = bounds
        val dp = density
        val left = b.left + 3f * dp
        val top = b.top + 3f * dp
        val right = b.right - 3f * dp
        val bottom = b.bottom - 3f * dp
        if (right - left < 8f * dp || bottom - top < 8f * dp) return

        val enabled = state.any { it == android.R.attr.state_enabled } || state.isEmpty()
        val focused = state.any { it == android.R.attr.state_focused }
        val alpha = if (enabled) globalAlpha else (globalAlpha * .45f).toInt()
        val clay = phase.coerceIn(0f, 1f)
        val paperOnly = 1f - clay
        val radius = (4f + 14f * clay) * dp

        // A fresh sheet sits inside the drawn edge so ruling never runs through the key;
        // in clay the field becomes a shallow well pressed into the page.
        paint.style = Paint.Style.FILL
        paint.color = withAlpha(ShaiPalette.blend(ShaiPalette.paperCard, ShaiPalette.claySunken, clay), alpha)
        fillRect.set(left - 1f * dp, top - 1f * dp, right + 1f * dp, bottom + 1f * dp)
        canvas.drawRoundRect(fillRect, radius, radius, paint)

        if (clay > 0.01f) {
            canvas.save()
            canvas.clipRect(fillRect)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 5f * dp
            paint.color = withAlpha(ShaiPalette.clayInk, (alpha * .16f * clay).toInt())
            canvas.drawRoundRect(
                fillRect.left + 1.4f * dp, fillRect.top + 1.4f * dp,
                fillRect.right + 1.4f * dp, fillRect.bottom + 1.4f * dp, radius, radius, paint
            )
            paint.color = withAlpha(Color.WHITE, (alpha * .55f * clay).toInt())
            canvas.drawRoundRect(
                fillRect.left - 1.4f * dp, fillRect.top - 1.4f * dp,
                fillRect.right - 1.4f * dp, fillRect.bottom - 1.4f * dp, radius, radius, paint
            )
            canvas.restore()
        }
        if (paperOnly <= 0f) return

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = (if (focused) 2.1f else 1.5f) * dp
        paint.color = withAlpha(
            if (focused) ShaiPalette.ink else ShaiPalette.pencil,
            (alpha * paperOnly).toInt()
        )
        buildOutline(left, top, right, bottom, dp, focused)
        canvas.drawPath(path, paint)

        // Second pass: a lighter re-ink one pixel off, so the line reads as drawn twice.
        paint.strokeWidth = (if (focused) 1.1f else 0.9f) * dp
        paint.color = withAlpha(
            ShaiPalette.pencil,
            (alpha * (if (focused) .40f else .30f) * paperOnly).toInt()
        )
        buildOutline(left + 1.1f * dp, top - 0.9f * dp, right + 0.8f * dp, bottom - 1.2f * dp, dp, false)
        canvas.drawPath(path, paint)
    }

    private fun buildOutline(l: Float, t: Float, r: Float, b: Float, dp: Float, focused: Boolean) {
        val w = r - l
        val h = b - t
        // Fixed irregularity — the same hand every time, not noise.
        val bow = if (focused) 1.9f * dp else 1.3f * dp
        path.reset()
        // Top edge, drawn right-to-left with a small overshoot at both ends.
        path.moveTo(r + 1.5f * dp, t - 0.6f * dp)
        path.cubicTo(l + w * .68f, t + bow, l + w * .30f, t - bow * .7f, l - 1.8f * dp, t + 0.9f * dp)
        // Left edge.
        path.moveTo(l + 0.4f * dp, t - 1.6f * dp)
        path.cubicTo(l - bow * .8f, t + h * .34f, l + bow, t + h * .70f, l + 1.2f * dp, b + 1.4f * dp)
        // Bottom edge.
        path.moveTo(l - 1.4f * dp, b - 0.4f * dp)
        path.cubicTo(l + w * .33f, b + bow, l + w * .72f, b - bow * .8f, r + 1.6f * dp, b + 0.7f * dp)
        // Right edge.
        path.moveTo(r - 0.6f * dp, b + 1.8f * dp)
        path.cubicTo(r + bow, b - h * .30f, r - bow * .7f, t + h * .66f, r + 0.4f * dp, t - 1.2f * dp)
    }

    private fun withAlpha(@ColorInt color: Int, alpha: Int): Int {
        val a = (Color.alpha(color) * alpha.coerceIn(0, 255) / 255f).toInt().coerceIn(0, 255)
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color))
    }

    override fun setAlpha(alpha: Int) {
        globalAlpha = alpha.coerceIn(0, 255)
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}

/**
 * One highlighter pass under a headline. Reveals left to right from a clip,
 * never loops, and turns to a quiet gold wash as the court takes over.
 */
internal class HighlightSweepDrawable : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val path = Path()

    var progress: Float = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidateSelf()
        }

    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidateSelf()
        }

    override fun draw(canvas: Canvas) {
        if (progress <= 0f) return
        val w = bounds.width().toFloat()
        val h = bounds.height().toFloat()
        if (w <= 0f || h <= 0f) return
        path.reset()
        path.moveTo(w * .012f, h * .54f)
        path.cubicTo(w * .22f, h * .40f, w * .55f, h * .52f, w * .992f, h * .40f)
        path.lineTo(w * .996f, h * .88f)
        path.cubicTo(w * .62f, h * .98f, w * .28f, h * .86f, w * .008f, h * .94f)
        path.close()
        paint.color = ShaiPalette.highlight
        // The marker belongs to the sheet; clay sets its headline without one.
        paint.alpha = (190f * (1f - phase * 1.6f)).toInt().coerceIn(0, 255)
        canvas.save()
        canvas.clipRect(0f, 0f, w * progress, h)
        canvas.drawPath(path, paint)
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
