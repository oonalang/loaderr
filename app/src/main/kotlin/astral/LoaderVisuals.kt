package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.max
import kotlin.random.Random

/**
 * Two states, one system.
 *
 * Sketch: an ivory concept sheet in graphite pencil with a single violet pencil
 * used for the "future" annotations. Maximal: the same sheet built out as a
 * violet court, with gilded double frames, guilloche rings, corner scrolls and
 * an engraved data surface.
 *
 * Everything is drawn with platform primitives: no images, no timers, no
 * per-frame allocation beyond fixed paths.
 */
internal object ShaiPalette {
    // Concept sheet
    val paper = Color.rgb(247, 242, 231)
    val paperCard = Color.rgb(253, 249, 240)
    val ink = Color.rgb(33, 28, 36)
    val pencil = Color.rgb(111, 98, 112)
    val draft = Color.rgb(106, 74, 140)

    // Violet court
    val void = Color.rgb(10, 6, 16)
    val darkPanel = Color.rgb(22, 12, 34)
    val plum = Color.rgb(36, 18, 50)
    val amethyst = Color.rgb(58, 31, 87)
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

/** Paints the concept sheet before auth and the violet court after auth. */
class LoaderBackdropView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val seed = Random(1835)
    private val density = resources.displayMetrics.density

    private val paperSpeckles = List(170) {
        Triple(seed.nextFloat(), seed.nextFloat(), seed.nextFloat())
    }
    private val stars = List(58) {
        Triple(seed.nextFloat(), seed.nextFloat(), seed.nextFloat())
    }
    private val flecks = List(14) {
        Triple(seed.nextFloat(), seed.nextFloat(), seed.nextFloat())
    }

    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        if (w <= 0f || h <= 0f) return
        canvas.drawColor(ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.void, phase))
        if (phase < 1f) drawConceptSheet(canvas, w, h, 1f - phase)
        if (phase > 0f) drawVioletCourt(canvas, w, h, phase)
    }

    // ---------------------------------------------------------------- sketch

    private fun drawConceptSheet(canvas: Canvas, w: Float, h: Float, opacity: Float) {
        paint.shader = null
        paint.style = Paint.Style.FILL
        paperSpeckles.forEach { (x, y, size) ->
            paint.color = argb(52, 43, 45, (30 * opacity * (0.3f + size)).toInt())
            canvas.drawCircle(x * w, y * h, 0.3f + size * 0.65f, paint)
        }

        val dp = density
        val inset = 15f * dp

        // Drafting margin, the sheet's actual structure.
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.8f * dp
        paint.color = argb(52, 43, 45, (44 * opacity).toInt())
        path.reset()
        path.moveTo(inset, inset)
        path.lineTo(w - inset, inset)
        path.lineTo(w - inset, h - inset)
        path.lineTo(inset, h - inset)
        path.close()
        canvas.drawPath(path, paint)

        paint.strokeWidth = 0.7f * dp
        paint.color = argb(52, 43, 45, (28 * opacity).toInt())
        var dashY = inset + 9f * dp
        while (dashY < h - inset - 6f * dp) {
            canvas.drawLine(inset - 5f * dp, dashY, inset - 1f * dp, dashY, paint)
            canvas.drawLine(w - inset + 1f * dp, dashY, w - inset + 5f * dp, dashY, paint)
            dashY += 16f * dp
        }

        // Ghost of the maximal layout: the sheet is a drawing of what comes next.
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.9f * dp
        paint.color = argb(106, 74, 140, (26 * opacity).toInt())
        val ghost = RectF(inset + 7f * dp, inset + 7f * dp, w - inset - 7f * dp, h - inset - 7f * dp)
        path.reset()
        path.addRoundRect(ghost, 2f * dp, 2f * dp, Path.Direction.CW)
        path.moveTo(ghost.left, ghost.top)
        path.lineTo(ghost.right, ghost.bottom)
        path.moveTo(ghost.right, ghost.top)
        path.lineTo(ghost.left, ghost.bottom)
        canvas.drawPath(path, paint)

        // Construction circle around the figure, plus a plotted centre cross.
        paint.color = argb(106, 74, 140, (34 * opacity).toInt())
        paint.strokeWidth = 0.8f * dp
        val cx = w * .30f
        val cy = h * .26f
        canvas.drawCircle(cx, cy, 66f * dp, paint)
        canvas.drawLine(cx - 76f * dp, cy, cx + 76f * dp, cy, paint)
        canvas.drawLine(cx, cy - 76f * dp, cx, cy + 76f * dp, paint)

        // Freehand flourish and underline (fixed paths, no shake).
        paint.color = argb(52, 43, 45, (86 * opacity).toInt())
        paint.strokeWidth = 1.3f * dp
        path.reset()
        path.moveTo(w * .76f, h * .105f)
        path.cubicTo(w * .93f, h * .075f, w * 1.01f, h * .115f, w * .89f, h * .155f)
        path.cubicTo(w * .79f, h * .185f, w * .75f, h * .145f, w * .85f, h * .135f)
        canvas.drawPath(path, paint)

        path.reset()
        path.moveTo(w * .055f, h * .865f)
        path.cubicTo(w * .16f, h * .861f, w * .25f, h * .869f, w * .34f, h * .863f)
        canvas.drawPath(path, paint)

        // Annotation arrow aimed at the key field, the sheet's own note.
        paint.color = argb(106, 74, 140, (76 * opacity).toInt())
        paint.strokeWidth = 0.9f * dp
        path.reset()
        path.moveTo(w * .62f, h * .50f)
        path.cubicTo(w * .70f, h * .52f, w * .72f, h * .555f, w * .66f, h * .575f)
        canvas.drawPath(path, paint)
        path.reset()
        path.moveTo(w * .66f, h * .575f)
        path.lineTo(w * .675f, h * .568f)
        path.moveTo(w * .66f, h * .575f)
        path.lineTo(w * .674f, h * .582f)
        canvas.drawPath(path, paint)

        paint.style = Paint.Style.FILL
    }

    // -------------------------------------------------------------- maximal

    private fun drawVioletCourt(canvas: Canvas, w: Float, h: Float, opacity: Float) {
        val dp = density
        paint.style = Paint.Style.FILL

        // Deep aubergine field.
        paint.shader = LinearGradient(
            0f, 0f, 0f, h,
            intArrayOf(argb(10, 6, 16, 255), argb(27, 14, 43, 255), argb(11, 6, 19, 255)),
            floatArrayOf(0f, .58f, 1f), Shader.TileMode.CLAMP
        )
        paint.alpha = (255 * opacity).toInt()
        canvas.drawRect(0f, 0f, w, h, paint)
        paint.alpha = 255

        // Crown glow over the crest, then a low violet wash.
        val halo = RadialGradient(
            w * .72f, h * .085f, max(w, h) * .62f,
            intArrayOf(argb(108, 46, 168, (150 * opacity).toInt()),
                argb(40, 21, 92, (64 * opacity).toInt()),
                Color.TRANSPARENT),
            floatArrayOf(0f, .46f, 1f), Shader.TileMode.CLAMP
        )
        paint.shader = halo
        canvas.drawRect(0f, 0f, w, h, paint)

        paint.shader = RadialGradient(
            w * .06f, h * .78f, max(w, h) * .46f,
            intArrayOf(argb(138, 42, 120, (52 * opacity).toInt()), Color.TRANSPARENT),
            floatArrayOf(0f, 1f), Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, w, h, paint)
        paint.shader = null

        // Guilloche: fine gilded rings above the content, clipped to the crown.
        val guillocheCenterX = w * .5f
        val guillocheCenterY = h * .22f
        canvas.save()
        canvas.clipRect(0f, 0f, w, h * .40f)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.8f * dp
        for (ring in 0..23) {
            val radius = 40f * dp + ring * 7f * dp
            val alpha = (20f - ring * 0.55f).coerceAtLeast(4f)
            paint.color = argb(233, 199, 126, (alpha * opacity).toInt())
            canvas.drawCircle(guillocheCenterX, guillocheCenterY, radius, paint)
        }
        paint.strokeWidth = 1.2f * dp
        paint.color = argb(146, 92, 244, (58 * opacity).toInt())
        canvas.drawCircle(w * .73f, h * .075f, 82f * dp, paint)
        canvas.drawCircle(w * .73f, h * .075f, 92f * dp, paint)
        canvas.restore()

        // Gilded double frame.
        val outer = 11f * dp
        val inner = 17f * dp
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.1f * dp
        paint.color = argb(233, 199, 126, (96 * opacity).toInt())
        path.reset()
        path.addRoundRect(RectF(outer, outer, w - outer, h - outer), 8f * dp, 8f * dp, Path.Direction.CW)
        canvas.drawPath(path, paint)
        paint.strokeWidth = 0.7f * dp
        paint.color = argb(233, 199, 126, (38 * opacity).toInt())
        path.reset()
        path.addRoundRect(RectF(inner, inner, w - inner, h - inner), 5f * dp, 5f * dp, Path.Direction.CW)
        canvas.drawPath(path, paint)

        // Corner scrolls, mirrored from one hand-drawn flourish.
        drawCornerScroll(canvas, outer + 3f * dp, outer + 3f * dp, 1f, 1f, dp, opacity)
        drawCornerScroll(canvas, w - outer - 3f * dp, outer + 3f * dp, -1f, 1f, dp, opacity)
        drawCornerScroll(canvas, outer + 3f * dp, h - outer - 3f * dp, 1f, -1f, dp, opacity)
        drawCornerScroll(canvas, w - outer - 3f * dp, h - outer - 3f * dp, -1f, -1f, dp, opacity)

        // Studs on the frame, not scattered decoration.
        paint.style = Paint.Style.FILL
        paint.color = argb(233, 199, 126, (150 * opacity).toInt())
        listOf(
            w * .5f to outer,
            w * .5f to h - outer,
            outer to h * .5f,
            w - outer to h * .5f
        ).forEach { (x, y) -> drawDiamond(canvas, x, y, 2.6f * dp, paint) }

        // Star dust and four-point sparkles.
        stars.forEach { (x, y, strength) ->
            paint.color = argb(226, 199, 255, (((26 + strength * 120) * opacity).toInt()))
            canvas.drawCircle(x * w, y * h, .45f + strength * 1.35f, paint)
        }
        flecks.forEach { (x, y, size) ->
            val cx = x * w
            val cy = y * h
            val arm = (2.6f + size * 3.6f) * dp
            paint.strokeWidth = 0.8f * dp
            paint.style = Paint.Style.STROKE
            paint.color = argb(233, 199, 126, ((42 + size * 40) * opacity).toInt())
            canvas.drawLine(cx - arm, cy, cx + arm, cy, paint)
            canvas.drawLine(cx, cy - arm, cx, cy + arm, paint)
        }

        // Vignette holds the middle of the screen readable.
        paint.style = Paint.Style.FILL
        paint.shader = RadialGradient(
            w * .5f, h * .42f, max(w, h) * .82f,
            intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT, argb(5, 3, 10, (150 * opacity).toInt())),
            floatArrayOf(0f, .44f, 1f), Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, w, h, paint)
        paint.shader = null
    }

    private fun drawCornerScroll(
        canvas: Canvas, x: Float, y: Float, sx: Float, sy: Float, dp: Float, opacity: Float
    ) {
        canvas.save()
        canvas.translate(x, y)
        canvas.scale(sx, sy)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 0.9f * dp
        paint.color = argb(233, 199, 126, (72 * opacity).toInt())
        path.reset()
        path.moveTo(0f, 42f * dp)
        path.cubicTo(0f, 17f * dp, 17f * dp, 0f, 42f * dp, 0f)
        path.cubicTo(28f * dp, 5f * dp, 17f * dp, 10f * dp, 15f * dp, 20f * dp)
        path.cubicTo(13f * dp, 30f * dp, 5f * dp, 27f * dp, 8f * dp, 19f * dp)
        path.cubicTo(10f * dp, 13f * dp, 17f * dp, 14f * dp, 18f * dp, 20f * dp)
        canvas.drawPath(path, paint)
        paint.style = Paint.Style.FILL
        paint.color = argb(233, 199, 126, (110 * opacity).toInt())
        drawDiamond(canvas, 25f * dp, 25f * dp, 1.9f * dp, paint)
        canvas.restore()
    }

    private fun drawDiamond(canvas: Canvas, cx: Float, cy: Float, r: Float, paint: Paint) {
        path.reset()
        path.moveTo(cx, cy - r)
        path.lineTo(cx + r, cy)
        path.lineTo(cx, cy + r)
        path.lineTo(cx - r, cy)
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun argb(r: Int, g: Int, b: Int, a: Int): Int =
        Color.argb(a.coerceIn(0, 255), r, g, b)
}

/**
 * One drawable per surface. The sketch state is a two-stroke hand-drawn contour
 * on paper; the maximal state adds an inner rule, corner ticks and gilded edges.
 */
internal class PhaseSurfaceDrawable(private val kind: Kind) : Drawable() {

    enum class Kind { CARD, PRIMARY, SECONDARY, SUCCESS, DRAWER, SEARCH, ROW, SELECT, PLAQUE }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val curve = Path()
    private val sheenClip = Path()
    private val sheenBounds = RectF()
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

    /** -1 hides the sweep; 0..1 moves a warm highlight across the gold action. */
    var sheen: Float = -1f
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
            Kind.PRIMARY -> 9f
            Kind.DRAWER -> 0f
            Kind.SELECT -> 5f
            else -> 7f
        }
        val radius = sketchRadius + (maximalRadius - sketchRadius) * phase

        // Paper gives a soft graphite drop, the court a lit top edge.
        if (phase < 1f) {
            paint.style = Paint.Style.FILL
            paint.shader = null
            paint.color = a(Color.rgb(45, 38, 48), .10f * (1f - phase))
            canvas.drawRoundRect(
                RectF(left + 2f * dp, top + 3f * dp, right + 2f * dp, bottom + 3f * dp),
                dp * radius, dp * radius, paint
            )
        }

        val fill = fillColor()
        paint.style = Paint.Style.FILL
        paint.shader = null
        paint.color = fill
        if (kind != Kind.SECONDARY) {
            canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)
        }
        if (phase > 0f && kind == Kind.PRIMARY) {
            paint.shader = LinearGradient(
                left, top, right, bottom,
                intArrayOf(Color.rgb(246, 226, 172), Color.rgb(219, 179, 105), Color.rgb(241, 212, 147)),
                floatArrayOf(0f, .52f, 1f), Shader.TileMode.CLAMP
            )
            paint.alpha = (255 * phase * globalAlpha / 255f).toInt()
            canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)
            paint.alpha = 255
            paint.shader = null
        }

        // The gilded action catches light as the court is assembled.
        if (kind == Kind.PRIMARY && phase > .5f && sheen in 0f..1f) {
            sheenBounds.set(left, top, right, bottom)
            sheenClip.reset()
            sheenClip.addRoundRect(sheenBounds, dp * radius, dp * radius, Path.Direction.CW)
            val bandCenter = left + (right - left) * sheen
            val band = 34f * dp
            canvas.save()
            canvas.clipPath(sheenClip)
            paint.style = Paint.Style.FILL
            paint.shader = LinearGradient(
                bandCenter - band, top, bandCenter + band, bottom,
                intArrayOf(
                    Color.TRANSPARENT,
                    Color.argb((108 * phase).toInt(), 255, 250, 235),
                    Color.TRANSPARENT
                ),
                floatArrayOf(0f, .5f, 1f), Shader.TileMode.CLAMP
            )
            canvas.drawRect(left, top, right, bottom, paint)
            canvas.restore()
            paint.shader = null
        }

        // Hand-drawn ink contour fades as the court is built.
        if (phase < 1f) drawInk(canvas, left, top, right, bottom, dp, 1f - phase)

        if (phase > 0f) {
            if (kind == Kind.PRIMARY) {
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = dp
                paint.color = a(Color.rgb(74, 44, 20), .55f * phase)
                canvas.drawRoundRect(
                    RectF(left + 3f * dp, top + 3f * dp, right - 3f * dp, bottom - 3f * dp),
                    dp * (radius - 2f), dp * (radius - 2f), paint
                )
            } else {
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 1.1f * dp
                paint.color = a(ShaiPalette.gold, .82f * phase)
                canvas.drawRoundRect(RectF(left, top, right, bottom), dp * radius, dp * radius, paint)

                paint.strokeWidth = 0.65f * dp
                paint.color = a(ShaiPalette.goldDeep, .5f * phase)
                val inset = 4f * dp
                canvas.drawRoundRect(
                    RectF(left + inset, top + inset, right - inset, bottom - inset),
                    dp * (radius - 1.5f), dp * (radius - 1.5f), paint
                )

                if (kind == Kind.CARD || kind == Kind.PLAQUE || kind == Kind.SUCCESS) {
                    paint.strokeWidth = dp
                    paint.color = a(ShaiPalette.gold, .95f * phase)
                    drawCornerTicks(canvas, left, top, right, bottom, dp)

                    paint.strokeWidth = 0.8f * dp
                    paint.color = a(ShaiPalette.orchid, .30f * phase)
                    canvas.drawLine(left + 14f * dp, top + 2.2f * dp, right - 14f * dp, top + 2.2f * dp, paint)
                }
            }

            if (kind == Kind.SUCCESS || kind == Kind.PLAQUE) {
                paint.style = Paint.Style.FILL
                paint.color = a(ShaiPalette.gold, .9f * phase)
                val cy = top + 1.5f * dp
                curve.reset()
                curve.moveTo(left + (right - left) * .5f, cy - 3f * dp)
                curve.lineTo(left + (right - left) * .5f + 3f * dp, cy)
                curve.lineTo(left + (right - left) * .5f, cy + 3f * dp)
                curve.lineTo(left + (right - left) * .5f - 3f * dp, cy)
                curve.close()
                canvas.drawPath(curve, paint)
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
            Kind.PRIMARY -> ShaiPalette.ink
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

    private fun drawInk(
        canvas: Canvas, l: Float, t: Float, r: Float, b: Float, dp: Float, alpha: Float
    ) {
        // Slightly uneven contour; fixed paths mean no frame jitter.
        for (stroke in 0..1) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = (if (stroke == 0) 2.1f else .7f) * dp
            paint.color = Color.argb(
                ((if (stroke == 0) 235f else 104f) * alpha * globalAlpha / 255f).toInt().coerceIn(0, 255),
                33, 28, 36
            )
            val offset = stroke * 1.5f * dp
            curve.reset()
            curve.moveTo(l + 5 * dp, t + 4 * dp + offset)
            curve.cubicTo(
                l + (r - l) * .31f, t - dp + offset,
                l + (r - l) * .68f, t + 5 * dp, r - 5 * dp, t + 3 * dp
            )
            curve.quadTo(r + 1 * dp, t + 3 * dp, r - 1 * dp, t + 9 * dp)
            curve.cubicTo(
                r + 2 * dp, t + (b - t) * .42f,
                r - 2 * dp, t + (b - t) * .71f, r - 4 * dp, b - 4 * dp
            )
            curve.cubicTo(
                l + (r - l) * .71f, b + 1 * dp,
                l + (r - l) * .30f, b - 3 * dp, l + 4 * dp, b - 2 * dp
            )
            curve.cubicTo(
                l - 2 * dp, t + (b - t) * .68f,
                l + 2 * dp, t + (b - t) * .36f, l + 5 * dp, t + 4 * dp + offset
            )
            canvas.drawPath(curve, paint)
        }

        if (kind == Kind.CARD || kind == Kind.PLAQUE) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 0.8f * dp
            paint.color = Color.argb((92 * alpha).toInt(), 33, 28, 36)
            for (i in 0..3) {
                canvas.drawLine(
                    r - (7f + i * 4f) * dp, b - 4f * dp,
                    r - 4f * dp, b - (7f + i * 4f) * dp, paint
                )
            }
            paint.strokeWidth = 0.7f * dp
            paint.color = Color.argb((70 * alpha).toInt(), 106, 74, 140)
            canvas.drawLine(l + 12f * dp, b + 1.5f * dp, l + 52f * dp, b + 1.5f * dp, paint)
        }
    }

    private fun drawCornerTicks(
        canvas: Canvas, l: Float, t: Float, r: Float, b: Float, dp: Float
    ) {
        val len = 9f * dp
        val inset = 3f * dp
        curve.reset()
        // Top-left
        curve.moveTo(l + inset, t + inset + len)
        curve.lineTo(l + inset, t + inset)
        curve.lineTo(l + inset + len, t + inset)
        // Top-right
        curve.moveTo(r - inset - len, t + inset)
        curve.lineTo(r - inset, t + inset)
        curve.lineTo(r - inset, t + inset + len)
        // Bottom-right
        curve.moveTo(r - inset, b - inset - len)
        curve.lineTo(r - inset, b - inset)
        curve.lineTo(r - inset - len, b - inset)
        // Bottom-left
        curve.moveTo(l + inset + len, b - inset)
        curve.lineTo(l + inset, b - inset)
        curve.lineTo(l + inset, b - inset - len)
        canvas.drawPath(curve, paint)
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
