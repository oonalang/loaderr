package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt

/** Shared paper and night-ink tokens for the illustrated notebook. */
internal object ShaiPalette {
    // Concept sheet. Five roles only: paper, grid, ink, pencil, highlighter.
    val paper = Color.rgb(253, 252, 247)
    val paperCard = Color.rgb(255, 255, 255)
    val ink = Color.rgb(31, 41, 55)
    val pencil = Color.rgb(119, 123, 132)
    val gridBlue = Color.rgb(201, 218, 248)
    val highlight = Color.rgb(255, 241, 118)
    /** Paper emphasis is ink weight, not a sixth hue; the court turns it gold. */
    val draft = ink

    // Violet court
    val void = Color.rgb(10, 6, 16)
    val darkPanel = Color.rgb(21, 17, 29)
    val plum = Color.rgb(34, 28, 44)
    val amethyst = Color.rgb(47, 40, 57)
    val gold = Color.rgb(233, 199, 126)
    val goldDeep = Color.rgb(157, 116, 58)
    val orchid = Color.rgb(226, 199, 255)
    val lilac = Color.rgb(194, 163, 255)
    val violet = Color.rgb(146, 92, 244)
    val muted = Color.rgb(188, 173, 209)
    val rose = Color.rgb(255, 122, 184)
    val success = Color.rgb(88, 226, 170)

    fun blend(@ColorInt from: Int, @ColorInt to: Int, fraction: Float): Int {
        val t = fraction.coerceIn(0f, 1f)
        return Color.argb(
            (Color.alpha(from) + (Color.alpha(to) - Color.alpha(from)) * t).toInt(),
            (Color.red(from) + (Color.red(to) - Color.red(from)) * t).toInt(),
            (Color.green(from) + (Color.green(to) - Color.green(from)) * t).toInt(),
            (Color.blue(from) + (Color.blue(to) - Color.blue(from)) * t).toInt()
        )
    }
}

/** Quiet notebook edge; illustration belongs to the scrolling content, never behind inputs. */
class LoaderBackdropView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val density = resources.displayMetrics.density
    var phase: Float = 0f
        set(value) { field = value.coerceIn(0f, 1f); invalidate() }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.void, phase))
        // One geometry pass: 24dp ruling, drawn rather than tiled from a bitmap.
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = GRID_STROKE_DP * density
        paint.color = ShaiPalette.gridBlue
        paint.alpha = ((GRID_ALPHA * (1f - phase)).toInt()).coerceIn(0, 255)
        if (paint.alpha > 0) {
            val step = GRID_STEP_DP * density
            var gx = step
            while (gx < width) {
                canvas.drawLine(gx, 0f, gx, height.toFloat(), paint)
                gx += step
            }
            var gy = step
            while (gy < height) {
                canvas.drawLine(0f, gy, width.toFloat(), gy, paint)
                gy += step
            }
        }
        paint.alpha = 255
    }

    private companion object {
        const val GRID_STEP_DP = 24f
        const val GRID_STROKE_DP = 0.75f
        const val GRID_ALPHA = 132f
    }
}

/** Ruled information and enclosed controls, with explicit interaction states. */
internal class PhaseSurfaceDrawable(private val kind: Kind) : Drawable() {

    enum class Kind { CARD, PRIMARY, SECONDARY, SUCCESS, DRAWER, SEARCH, ROW, SELECT, PLAQUE }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var globalAlpha = 255

    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidateSelf()
        }

    var selected: Boolean = false
        set(value) {
            field = value
            invalidateSelf()
        }

    override fun isStateful(): Boolean = true

    override fun onStateChange(state: IntArray): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {
        val b = bounds
        val dp = resourcesDensity()
        val left = b.left.toFloat() + 2f * dp
        val top = b.top.toFloat() + 2f * dp
        val right = b.right.toFloat() - 2f * dp
        val bottom = b.bottom.toFloat() - 2f * dp
        if (right <= left || bottom <= top) return

        val savedAlpha = globalAlpha
        if (state.none { it == android.R.attr.state_enabled }) {
            globalAlpha = (globalAlpha * .55f).toInt()
        }

        val sketchRadius = 3f
        val maximalRadius = when (kind) {
            Kind.PRIMARY -> 3f
            Kind.DRAWER -> 0f
            Kind.SELECT -> 3f
            else -> 3f
        }
        val radius = sketchRadius + (maximalRadius - sketchRadius) * phase

        // Only actionable surfaces are enclosed; information is separated by a rule.
        paint.shader = null
        paint.style = Paint.Style.FILL
        paint.color = a(fillColor(), 1f)
        val ruled = kind == Kind.CARD || kind == Kind.SUCCESS || kind == Kind.PLAQUE || kind == Kind.ROW
        if (!ruled && kind != Kind.SECONDARY) {
            canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)
        }
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = dp
        paint.color = a(ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.goldDeep, phase), .65f)
        if (ruled) {
            canvas.drawLine(left, bottom, right, bottom, paint)
        } else if (kind != Kind.DRAWER) {
            canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)
            // Sketch double pass: a slightly offset re-ink, fading as the court takes over.
            if (phase < .99f) {
                paint.alpha = (95 * (1f - phase)).toInt()
                canvas.drawRoundRect(
                    RectF(left + 1.3f * dp, top - 0.8f * dp, right + 1.1f * dp, bottom - 1.5f * dp),
                    dp * radius, dp * radius, paint
                )
                paint.color = a(ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.goldDeep, phase), .65f)
            }
        }

        if (kind == Kind.ROW && selected) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = if (phase > .5f) 1.8f * dp else 2f * dp
            paint.color = ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase)
            canvas.drawRoundRect(
                RectF(left + dp, top + dp, right - dp, bottom - dp),
                dp * radius, dp * radius, paint
            )
        }

        val focused = state.any { it == android.R.attr.state_focused }
        val pressed = state.any { it == android.R.attr.state_pressed }

        if (pressed) {
            paint.style = Paint.Style.FILL
            paint.color = if (kind == Kind.PRIMARY) {
                a(ShaiPalette.void, .16f)
            } else {
                a(ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase), .10f)
            }
            canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)
        }

        if (focused && kind != Kind.DRAWER) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.6f * dp
            paint.color = if (kind == Kind.PRIMARY) {
                ShaiPalette.blend(ShaiPalette.ink, Color.rgb(74, 44, 20), phase)
            } else {
                ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase)
            }
            canvas.drawRoundRect(
                RectF(left + 1.6f * dp, top + 1.6f * dp, right - 1.6f * dp, bottom - 1.6f * dp),
                dp * radius, dp * radius, paint
            )
        }

        paint.shader = null
        paint.style = Paint.Style.FILL
        globalAlpha = savedAlpha
    }

    private fun fillColor(): Int {
        val sketch = when (kind) {
            Kind.PRIMARY -> ShaiPalette.highlight
            Kind.SECONDARY -> ShaiPalette.paperCard
            else -> ShaiPalette.paperCard
        }
        val court = when (kind) {
            Kind.PRIMARY -> ShaiPalette.gold
            Kind.SUCCESS, Kind.PLAQUE -> Color.rgb(38, 16, 52)
            Kind.SECONDARY -> Color.rgb(22, 12, 34)
            Kind.DRAWER -> Color.rgb(18, 10, 28)
            Kind.SEARCH, Kind.SELECT -> Color.rgb(30, 16, 46)
            Kind.ROW -> Color.rgb(33, 18, 49)
            else -> ShaiPalette.darkPanel
        }
        return ShaiPalette.blend(sketch, court, phase)
    }

    private fun a(@ColorInt color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor.coerceIn(0f, 1f) * globalAlpha / 255f).toInt()
        return Color.argb(alpha.coerceIn(0, 255), Color.red(color), Color.green(color), Color.blue(color))
    }

    private fun resourcesDensity() = android.content.res.Resources.getSystem().displayMetrics.density

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
 * A horizontal rule that is structural, not trim: centre mark plus end ticks.
 * Pencil on the sheet, gilded diamond in the court.
 */
class OrnamentRuleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val density = resources.displayMetrics.density

    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    /** 0 draws the rule from its centre; 1 is the complete rule. */
    var reveal: Float = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f) return
        val dp = density
        val y = h * .5f
        val cx = w * .5f
        val gap = (h * .9f) + 12f * dp
        val amount = reveal

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = (0.8f + 0.5f * phase) * dp
        paint.color = ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.gold, phase)
        paint.alpha = (255 * (.7f - .1f * phase)).toInt()

        path.reset()
        path.moveTo(dp, y)
        if (phase < .5f) {
            // Slightly hand-drawn rule.
            path.cubicTo(w * .26f, y - 1.1f * dp, w * .30f, y + 1.2f * dp, cx - gap, y)
            path.moveTo(cx + gap, y)
            path.cubicTo(w * .70f, y + 1.1f * dp, w * .76f, y - 1.2f * dp, w - dp, y)
        } else {
            path.lineTo(cx - gap, y)
            path.moveTo(cx + gap, y)
            path.lineTo(w - dp, y)
        }
        canvas.save()
        canvas.clipRect(
            cx - (cx - dp) * amount,
            0f,
            cx + (w - dp - cx) * amount,
            h
        )
        canvas.drawPath(path, paint)
        canvas.restore()

        paint.style = Paint.Style.FILL
        paint.alpha = 255
        val mark = ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase)
        paint.color = mark
        val r = (2.2f + 2.6f * phase) * dp * amount
        path.reset()
        path.moveTo(cx, y - r)
        path.lineTo(cx + r, y)
        path.lineTo(cx, y + r)
        path.lineTo(cx - r, y)
        path.close()
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.9f * dp
        paint.color = ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.goldDeep, phase)
        paint.alpha = (230 * (0.5f + 0.5f * phase) * amount).toInt()
        if (amount > .75f) {
            canvas.drawCircle(cx - gap, y, (1.1f + phase * 1.1f) * dp, paint)
            canvas.drawCircle(cx + gap, y, (1.1f + phase * 1.1f) * dp, paint)
        }

        // End ticks give the rule a measured, drafted quality.
        if (amount > .94f) {
            canvas.drawLine(dp, y - 3f * dp, dp, y + 3f * dp, paint)
            canvas.drawLine(w - dp, y - 3f * dp, w - dp, y + 3f * dp, paint)
        }
        paint.alpha = 255
    }
}
