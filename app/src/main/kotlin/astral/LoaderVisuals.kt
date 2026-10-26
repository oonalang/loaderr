package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import android.graphics.Outline

/**
 * Two material states, one set of tokens.
 *
 * 0 = the concept sheet: paper, ink, pencil, highlighter.
 * 1 = the clay build: pale lavender, matte pastel, one upper-left light.
 */
internal object ShaiPalette {
    // Concept sheet. Five roles only: paper, grid, ink, pencil, highlighter.
    val paper = Color.rgb(253, 252, 247)
    val paperCard = Color.rgb(255, 255, 255)
    val ink = Color.rgb(31, 41, 55)
    val pencil = Color.rgb(119, 123, 132)
    val gridBlue = Color.rgb(201, 218, 248)
    val highlight = Color.rgb(255, 241, 118)
    /** Paper emphasis is ink weight, not a sixth hue; clay turns it pink. */
    val draft = ink

    // Clay. Same identity, finished material.
    val clayBg = Color.rgb(244, 238, 255)
    val clayInk = Color.rgb(43, 35, 64)
    val clayMuted = Color.rgb(95, 83, 111)
    val clayRaised = Color.rgb(250, 247, 255)
    val claySunken = Color.rgb(233, 225, 247)
    val clayPink = Color.rgb(255, 184, 217)
    val clayPinkDeep = Color.rgb(238, 152, 197)
    val clayMint = Color.rgb(168, 230, 207)
    val clayMintDeep = Color.rgb(136, 211, 184)
    val clayPeri = Color.rgb(181, 199, 255)
    val clayPeriDeep = Color.rgb(155, 176, 245)

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
        // Construction lines fade out before the clay arrives: they are the
        // blueprint, not part of the finished object.
        val ruling = (1f - phase * 2.2f).coerceIn(0f, 1f)
        canvas.drawColor(ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.clayBg, phase))
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = GRID_STROKE_DP * density
        paint.color = ShaiPalette.gridBlue
        paint.alpha = ((GRID_ALPHA * ruling).toInt()).coerceIn(0, 255)
        if (paint.alpha > 0) {
            // One geometry pass: 24dp ruling, drawn rather than tiled from a bitmap.
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

/**
 * Every functional surface in the app, in both materials.
 *
 * On paper it is a ruled edge or a drawn box. In clay it is a moulded form:
 * a soft stack shadow, a matte face, and a bevel lit from the upper left.
 * Pressing compresses the form instead of tinting it, and recessed controls
 * (search, dropdowns) are lit from the inside — the one rule that separates a
 * finished clay interface from rounded rectangles with shadows.
 */
internal class PhaseSurfaceDrawable(
    context: Context,
    private val kind: Kind
) : Drawable() {

    /** Measured in the view's own density, so a 30dp radius is 30dp everywhere. */
    private val density: Float = context.resources.displayMetrics.density

    enum class Kind { CARD, PRIMARY, SECONDARY, SUCCESS, DRAWER, SEARCH, ROW, SELECT, PLAQUE }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bevelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE }
    private val faceRect = RectF()
    private val shadowRect = RectF()
    private val outlineRect = RectF()
    private var globalAlpha = 255

    private var bevel: LinearGradient? = null
    private var bevelWidth = -1f
    private var bevelHeight = -1f
    private var bevelRadius = -1f
    private var bevelSunken: Boolean? = null

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
        val dp = density
        val clay = phase
        // Paper keeps 2dp so its strokes are never clipped; clay reserves real
        // room inside the view for the shadow to fall into.
        val inset = (2f + (clayInset() - 2f) * clay) * dp
        val left = b.left.toFloat() + inset
        val top = b.top.toFloat() + inset
        val right = b.right.toFloat() - inset
        val bottom = b.bottom.toFloat() - inset
        if (right - left < 8f * dp || bottom - top < 8f * dp) return

        val savedAlpha = globalAlpha
        // An empty state means "not synced yet", not "disabled"; only an explicit
        // absence of state_enabled dims the surface.
        if (state.isNotEmpty() && state.none { it == android.R.attr.state_enabled }) {
            globalAlpha = (globalAlpha * .55f).toInt()
        }
        val pressed = state.any { it == android.R.attr.state_pressed }
        val focused = state.any { it == android.R.attr.state_focused }
        // A press sinks the form 1dp and takes the shadow with it.
        val sink = if (pressed) 1.2f * dp * clay else 0f
        val radius = (sketchRadius() + (clayRadius() - sketchRadius()) * clay) * dp

        faceRect.set(left, top + sink, right, bottom + sink)

        if (clay > 0.01f) {
            if (clayShadow()) {
                drawStackShadow(canvas, faceRect, radius, dp, clay, pressed)
            }
            paint.style = Paint.Style.FILL
            paint.color = a(clayFace(pressed), 1f)
            canvas.drawRoundRect(faceRect, radius, radius, paint)
            drawBevel(canvas, faceRect, radius, dp, clay, sunken = !clayShadow())
        }

        // Paper pass: the ruled edge, then the drawn box. Fades as clay sets.
        paint.shader = null
        if (clay < .999f) {
            paint.style = Paint.Style.FILL
            paint.color = a(fillColor(), 1f)
            val ruled = kind == Kind.CARD || kind == Kind.SUCCESS || kind == Kind.PLAQUE || kind == Kind.ROW
            val paperOnly = 1f - clay
            if (!ruled && kind != Kind.SECONDARY) {
                paint.color = a(fillColor(), paperOnly)
                canvas.drawRoundRect(faceRect, radius, radius, paint)
            }
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = dp
            paint.color = a(ShaiPalette.pencil, .65f * paperOnly)
            if (ruled) {
                canvas.drawLine(left, bottom, right, bottom, paint)
            } else if (kind != Kind.DRAWER) {
                canvas.drawRoundRect(faceRect, radius, radius, paint)
                // Sketch double pass: a slightly offset re-ink.
                paint.alpha = (95 * paperOnly).toInt()
                canvas.save()
                canvas.translate(1.3f * dp, -0.8f * dp)
                canvas.drawRoundRect(faceRect, radius, radius, paint)
                canvas.restore()
            }
        }

        if (kind == Kind.ROW && selected && clay < .5f) {
            paint.shader = null
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f * dp
            paint.color = ShaiPalette.ink
            canvas.drawRoundRect(
                RectF(faceRect.left + dp, faceRect.top + dp, faceRect.right - dp, faceRect.bottom - dp),
                radius, radius, paint
            )
        }

        if (focused) {
            paint.shader = null
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = (1.6f + 0.6f * clay) * dp
            paint.color = a(
                if (kind == Kind.PRIMARY) ShaiPalette.clayInk else ShaiPalette.clayPeriDeep,
                if (clay > .5f) .9f else .75f
            )
            val gap = (1.6f + 2.4f * clay) * dp
            canvas.drawRoundRect(
                RectF(faceRect.left + gap, faceRect.top + gap, faceRect.right - gap, faceRect.bottom - gap),
                radius - gap, radius - gap, paint
            )
        }

        paint.shader = null
        paint.style = Paint.Style.FILL
        globalAlpha = savedAlpha
    }

    /**
     * Soft shadow as concentric rounded rings: deterministic, allocation-free,
     * and identical on every device. `BlurMaskFilter` is not reliable on a
     * hardware canvas, so the falloff is drawn rather than filtered.
     */
    private fun drawStackShadow(
        canvas: Canvas, rect: RectF, radius: Float, dp: Float, clay: Float, pressed: Boolean
    ) {
        val reach = (if (pressed) 3.4f else 7.6f) * dp * clay
        val strength = (if (pressed) .10f else .15f) * clay
        val lift = (if (pressed) .5f else 1.4f) * dp * clay
        paint.style = Paint.Style.FILL
        for (i in SHADOW_RINGS downTo 1) {
            val t = i.toFloat() / SHADOW_RINGS
            val grow = reach * t
            paint.color = a(ShaiPalette.clayInk, strength * (1f - t) * (1f - t) * .95f)
            shadowRect.set(
                rect.left - grow, rect.top - grow + lift,
                rect.right + grow, rect.bottom + grow + lift
            )
            canvas.drawRoundRect(shadowRect, radius + grow, radius + grow, paint)
        }
    }

    /**
     * One light source, upper left: a white rim on the top-left and a shade on
     * the bottom-right. Recessed controls invert it, so they read as pressed in.
     */
    private fun drawBevel(
        canvas: Canvas, rect: RectF, radius: Float, dp: Float, clay: Float, sunken: Boolean
    ) {
        if (bevel == null || bevelWidth != rect.width() || bevelHeight != rect.height() ||
            bevelRadius != radius || bevelSunken != sunken
        ) {
            val light = if (sunken) ShaiPalette.clayInk else Color.WHITE
            val shade = if (sunken) Color.WHITE else ShaiPalette.clayInk
            bevel = LinearGradient(
                rect.left, rect.top, rect.right, rect.bottom,
                intArrayOf(
                    withAlpha(light, if (sunken) .22f else .62f),
                    withAlpha(light, 0f),
                    withAlpha(shade, 0f),
                    withAlpha(shade, if (sunken) .10f else .20f)
                ),
                floatArrayOf(0f, .42f, .58f, 1f),
                Shader.TileMode.CLAMP
            )
            bevelWidth = rect.width()
            bevelHeight = rect.height()
            bevelRadius = radius
            bevelSunken = sunken
        }
        bevelPaint.shader = bevel
        bevelPaint.strokeWidth = (if (sunken) 7f else 9f) * dp
        bevelPaint.alpha = (255 * clay).toInt().coerceIn(0, 255)
        canvas.save()
        canvas.clipRect(rect)
        // Stroke sits on the edge; clipping keeps only its inner half.
        val band = bevelPaint.strokeWidth * .5f
        outlineRect.set(rect.left + band, rect.top + band, rect.right - band, rect.bottom - band)
        canvas.drawRoundRect(outlineRect, radius - band, radius - band, bevelPaint)
        canvas.restore()
        bevelPaint.shader = null
        bevelPaint.alpha = 255
    }

    private fun clayShadow(): Boolean = when (kind) {
        Kind.SEARCH, Kind.SELECT -> false
        else -> true
    }

    /** Must be at least the shadow reach, or the falloff is cut off at the view edge. */
    private fun clayInset(): Float = when (kind) {
        Kind.CARD, Kind.SUCCESS, Kind.PLAQUE -> 9f
        Kind.PRIMARY, Kind.SECONDARY -> 8f
        Kind.DRAWER -> 0f
        Kind.SEARCH, Kind.SELECT -> 3f
        Kind.ROW -> 9f
    }

    private fun clayRadius(): Float = when (kind) {
        Kind.CARD -> 30f
        Kind.PRIMARY -> 22f
        Kind.SECONDARY -> 20f
        Kind.SUCCESS, Kind.PLAQUE -> 26f
        // The panel is a slab now, not a full-bleed sheet: it needs its own corners.
        Kind.DRAWER -> 28f
        Kind.SEARCH, Kind.SELECT -> 16f
        Kind.ROW -> 18f
    }

    private fun sketchRadius(): Float = if (kind == Kind.DRAWER) 0f else 3f

    private fun clayFace(pressed: Boolean): Int {
        val base = when (kind) {
            Kind.PRIMARY -> ShaiPalette.clayPink
            Kind.SECONDARY -> ShaiPalette.clayPeri
            Kind.SUCCESS, Kind.PLAQUE -> ShaiPalette.clayMint
            Kind.SEARCH, Kind.SELECT -> ShaiPalette.claySunken
            Kind.ROW -> if (selected) ShaiPalette.clayPeri else ShaiPalette.clayRaised
            else -> ShaiPalette.clayRaised
        }
        if (!pressed) return base
        return if (kind == Kind.PRIMARY) {
            ShaiPalette.blend(base, ShaiPalette.clayPinkDeep, .55f)
        } else {
            ShaiPalette.blend(base, ShaiPalette.claySunken, .55f)
        }
    }

    private fun fillColor(): Int {
        val sketch = when (kind) {
            Kind.PRIMARY -> ShaiPalette.highlight
            else -> ShaiPalette.paperCard
        }
        return sketch
    }

    private fun withAlpha(@ColorInt color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor.coerceIn(0f, 1f)).toInt().coerceIn(0, 255)
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color))
    }

    private fun a(@ColorInt color: Int, factor: Float): Int {
        val alpha = (Color.alpha(color) * factor.coerceIn(0f, 1f) * globalAlpha / 255f).toInt()
        return Color.argb(alpha.coerceIn(0, 255), Color.red(color), Color.green(color), Color.blue(color))
    }

    /** Lets the system elevation shadow follow the moulded radius, not the view box. */
    override fun getOutline(outline: Outline) {
        val inset = clayInset() * density * phase + 2f * density * (1f - phase)
        val target = outlineRect
        target.set(
            bounds.left + inset, bounds.top + inset,
            bounds.right - inset, bounds.bottom - inset
        )
        if (target.width() <= 0f || target.height() <= 0f) {
            outline.setEmpty()
            return
        }
        val radius = (sketchRadius() + (clayRadius() - sketchRadius()) * phase) * density
        outline.setRoundRect(
            Rect(target.left.toInt(), target.top.toInt(), target.right.toInt(), target.bottom.toInt()),
            radius
        )
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

    private companion object {
        const val SHADOW_RINGS = 10
    }
}

/**
 * A horizontal rule that is structural, not trim: centre mark plus end ticks.
 * It is a drafting mark, so it is drawn out of the page as the clay arrives.
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
        // No ornament belongs to the finished object; the rule leaves with the grid.
        val paper = (1f - phase * 1.8f).coerceIn(0f, 1f)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f || paper <= 0f) return
        val dp = density
        val y = h * .5f
        val cx = w * .5f
        val gap = (h * .9f) + 12f * dp
        val amount = reveal * paper

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.8f * dp
        paint.color = ShaiPalette.pencil
        paint.alpha = (255 * .7f * paper).toInt()

        path.reset()
        path.moveTo(dp, y)
        path.cubicTo(w * .26f, y - 1.1f * dp, w * .30f, y + 1.2f * dp, cx - gap, y)
        path.moveTo(cx + gap, y)
        path.cubicTo(w * .70f, y + 1.1f * dp, w * .76f, y - 1.2f * dp, w - dp, y)
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
        paint.alpha = (255 * paper).toInt()
        paint.color = ShaiPalette.ink
        val r = 2.2f * dp * amount
        path.reset()
        path.moveTo(cx, y - r)
        path.lineTo(cx + r, y)
        path.lineTo(cx, y + r)
        path.lineTo(cx - r, y)
        path.close()
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.9f * dp
        paint.color = ShaiPalette.pencil
        paint.alpha = (230 * .5f * paper * amount).toInt()
        if (amount > .75f) {
            canvas.drawCircle(cx - gap, y, 1.1f * dp, paint)
            canvas.drawCircle(cx + gap, y, 1.1f * dp, paint)
        }

        // End ticks give the rule a measured, drafted quality.
        if (amount > .94f) {
            canvas.drawLine(dp, y - 3f * dp, dp, y + 3f * dp, paint)
            canvas.drawLine(w - dp, y - 3f * dp, w - dp, y + 3f * dp, paint)
        }
        paint.alpha = 255
    }
}
