package astral.loader

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
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
 * The initial surface deliberately resembles an ink sketch on ivory paper,
 * rather than a technical blueprint. The validated state uses one restrained
 * violet palette and no external image/video or constant animation timer.
 */
internal object ShaiPalette {
    val paper = Color.rgb(250, 247, 239)
    val paperCard = Color.rgb(255, 253, 248)
    val ink = Color.rgb(30, 25, 32)
    val pencil = Color.rgb(109, 99, 107)
    val void = Color.rgb(11, 11, 16)
    val darkPanel = Color.rgb(21, 19, 29)
    val lilac = Color.rgb(189, 156, 255)
    val violet = Color.rgb(146, 92, 244)
    val muted = Color.rgb(182, 174, 198)

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

/** Paints a paper texture in the sketch phase, and an ethereal gradient in the signed-in phase. */
class LoaderBackdropView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val seed = Random(1835)
    private val paperSpeckles = List(160) {
        Triple(seed.nextFloat(), seed.nextFloat(), seed.nextFloat())
    }
    private val stars = List(20) {
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
        if (phase < 1f) drawPaper(canvas, w, h, 1f - phase)
        if (phase > 0f) drawEthereal(canvas, w, h, phase)
    }

    private fun drawPaper(canvas: Canvas, w: Float, h: Float, opacity: Float) {
        paint.shader = null
        paperSpeckles.forEach { (x, y, size) ->
            paint.color = Color.argb((32 * opacity * (0.35f + size)).toInt(), 52, 43, 45)
            paint.style = Paint.Style.FILL
            canvas.drawCircle(x * w, y * h, 0.35f + size * 0.7f, paint)
        }
        paint.color = Color.argb((95 * opacity).toInt(), 42, 35, 45)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = resources.displayMetrics.density * 1.4f
        // Freehand motifs, not geometric drafting guides.
        val flourish = Path().apply {
            moveTo(w * .76f, h * .21f)
            cubicTo(w * .93f, h * .17f, w * 1.01f, h * .22f, w * .89f, h * .27f)
            cubicTo(w * .79f, h * .31f, w * .75f, h * .26f, w * .85f, h * .24f)
        }
        canvas.drawPath(flourish, paint)
        val underline = Path().apply {
            moveTo(w * .07f, h * .81f)
            cubicTo(w * .18f, h * .806f, w * .26f, h * .814f, w * .33f, h * .809f)
        }
        canvas.drawPath(underline, paint)
        paint.style = Paint.Style.FILL
    }

    private fun drawEthereal(canvas: Canvas, w: Float, h: Float, opacity: Float) {
        val radius = max(w, h) * .7f
        paint.style = Paint.Style.FILL
        paint.shader = RadialGradient(w * .87f, h * .20f, radius,
            intArrayOf(Color.argb((98 * opacity).toInt(), 91, 43, 186),
                Color.argb((35 * opacity).toInt(), 57, 29, 124), Color.TRANSPARENT),
            floatArrayOf(0f, .45f, 1f), Shader.TileMode.CLAMP)
        canvas.drawRect(0f, 0f, w, h, paint)
        paint.shader = RadialGradient(w * .1f, h * .85f, radius * .7f,
            Color.argb((67 * opacity).toInt(), 98, 45, 185), Color.TRANSPARENT,
            Shader.TileMode.CLAMP)
        canvas.drawRect(0f, 0f, w, h, paint)
        paint.shader = null
        paint.style = Paint.Style.STROKE
        paint.color = Color.argb((84 * opacity).toInt(), 176, 140, 255)
        paint.strokeWidth = resources.displayMetrics.density
        canvas.drawArc(RectF(w * .43f, h * .125f, w * 1.18f, h * .38f), 187f, 184f, false, paint)
        paint.style = Paint.Style.FILL
        stars.forEach { (x, y, strength) ->
            paint.color = Color.argb(((35 + strength * 140) * opacity).toInt(), 216, 192, 255)
            canvas.drawCircle(x * w, y * h, .35f + strength * 1.2f, paint)
        }
    }
}

/** One Drawable per surface. Interpolates colors and changes the actual border drawing. */
internal class PhaseSurfaceDrawable(private val kind: Kind) : Drawable() {
    enum class Kind { CARD, PRIMARY, SECONDARY, SUCCESS, DRAWER, SEARCH, ROW }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val curve = Path()
    var phase: Float = 0f
        set(value) { field = value.coerceIn(0f, 1f); invalidateSelf() }
    var selected: Boolean = false
        set(value) { field = value; invalidateSelf() }

    override fun draw(canvas: Canvas) {
        val b = bounds
        val left = b.left.toFloat() + 2f
        val top = b.top.toFloat() + 2f
        val right = b.right.toFloat() - 2f
        val bottom = b.bottom.toFloat() - 2f
        val base = if (kind == Kind.PRIMARY) ShaiPalette.ink else ShaiPalette.paperCard
        val final = when (kind) {
            Kind.PRIMARY -> Color.rgb(91, 36, 176)
            Kind.SUCCESS -> Color.rgb(36, 23, 60)
            Kind.SECONDARY -> Color.rgb(15, 11, 26)
            Kind.DRAWER -> Color.rgb(17, 12, 30)
            Kind.SEARCH -> Color.rgb(30, 21, 48)
            Kind.ROW -> Color.rgb(27, 19, 43)
            else -> ShaiPalette.darkPanel
        }
        paint.style = Paint.Style.FILL
        paint.shader = null
        paint.color = ShaiPalette.blend(base, final, phase)
        val roundness = when (kind) {
            Kind.PRIMARY -> 13f
            Kind.DRAWER -> 0f
            else -> 16f
        }
        val dp = resourcesDensity()
        canvas.drawRoundRect(RectF(left, top, right, bottom),
            (3f + (roundness - 3f) * phase) * dp,
            (3f + (roundness - 3f) * phase) * dp, paint)
        if (phase > 0f) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = dp
            paint.color = ShaiPalette.blend(Color.TRANSPARENT,
                if (kind == Kind.PRIMARY) Color.rgb(206, 163, 255) else Color.rgb(88, 64, 127), phase)
            canvas.drawRoundRect(RectF(left + dp, top + dp, right - dp, bottom - dp),
                roundness * dp, roundness * dp, paint)
        }
        if (kind == Kind.ROW && selected) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f * dp
            paint.color = ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.lilac, phase)
            canvas.drawRoundRect(RectF(left + dp, top + dp, right - dp, bottom - dp), 8f * dp, 8f * dp, paint)
        }
        if (phase < 1f) drawInk(canvas, left, top, right, bottom, dp, 1f - phase)
        paint.shader = null
        paint.style = Paint.Style.FILL
    }

    private fun drawInk(canvas: Canvas, l: Float, t: Float, r: Float, b: Float, dp: Float, alpha: Float) {
        // Slightly uneven hand-drawn contour; fixed paths mean no frame jitter.
        for (stroke in 0..1) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = (if (stroke == 0) 2.1f else .7f) * dp
            paint.color = Color.argb((if (stroke == 0) 240f else 108f).times(alpha).toInt(), 30, 25, 32)
            val offset = stroke * 1.5f * dp
            curve.reset()
            curve.moveTo(l + 5 * dp, t + 4 * dp + offset)
            curve.cubicTo(l + (r - l) * .31f, t - dp + offset,
                l + (r - l) * .68f, t + 5 * dp, r - 5 * dp, t + 3 * dp)
            curve.quadTo(r + 1 * dp, t + 3 * dp, r - 1 * dp, t + 9 * dp)
            curve.cubicTo(r + 2 * dp, t + (b - t) * .42f,
                r - 2 * dp, t + (b - t) * .71f, r - 4 * dp, b - 4 * dp)
            curve.cubicTo(l + (r - l) * .71f, b + 1 * dp,
                l + (r - l) * .30f, b - 3 * dp, l + 4 * dp, b - 2 * dp)
            curve.cubicTo(l - 2 * dp, t + (b - t) * .68f,
                l + 2 * dp, t + (b - t) * .36f, l + 5 * dp, t + 4 * dp + offset)
            canvas.drawPath(curve, paint)
        }
    }

    private fun resourcesDensity() = android.content.res.Resources.getSystem().displayMetrics.density
    override fun setAlpha(alpha: Int) { paint.alpha = alpha; invalidateSelf() }
    override fun setColorFilter(colorFilter: ColorFilter?) { paint.colorFilter = colorFilter; invalidateSelf() }
    @Deprecated("Deprecated in Java") override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
