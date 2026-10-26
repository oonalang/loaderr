package astral.loader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * The keykeeper, inked directly on the page.
 *
 * One illustration carries the whole identity: it opens the notebook, it stays
 * on the authenticated page as the crest, and every line is a stroked contour
 * revealed through [PathMeasure] rather than a cross-fade of two bitmaps.
 * Geometry is hand-authored in a 320 x 184 study space and scaled to fit.
 */
class ShaiIllustrationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private enum class Ink { LINE, DETAIL, KEY }

    private class Stroke(val path: Path, val ink: Ink, val start: Float, val end: Float)

    private val strokes = ArrayList<Stroke>(24)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val measure = PathMeasure()
    private val segment = Path()
    private val washRect = RectF()
    private var animator: ValueAnimator? = null

    /** 0 = blank page, 1 = finished drawing. */
    var reveal: Float = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    /** Paper sheet (0) to violet court (1); the same lines are simply re-inked. */
    var phase: Float = 0f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    init {
        buildKeykeeper()
    }

    private fun buildKeykeeper() {
        fun stroke(ink: Ink, start: Float, end: Float, build: Path.() -> Unit) {
            strokes += Stroke(Path().apply(build), ink, start, end)
        }

        // Ground: one loose line the figure stands on.
        stroke(Ink.DETAIL, 0f, .20f) {
            moveTo(30f, 160f); cubicTo(82f, 156f, 204f, 163f, 290f, 158f)
        }
        stroke(Ink.DETAIL, .06f, .22f) {
            moveTo(36f, 166f); lineTo(113f, 166f)
            moveTo(234f, 165f); lineTo(279f, 165f)
        }
        // Hood and shoulder silhouette, in one breath.
        stroke(Ink.LINE, .03f, .38f) {
            moveTo(93f, 157f)
            cubicTo(81f, 130f, 83f, 91f, 95f, 63f)
            cubicTo(104f, 34f, 128f, 18f, 151f, 22f)
            cubicTo(181f, 17f, 203f, 44f, 197f, 76f)
            cubicTo(193f, 94f, 197f, 109f, 211f, 124f)
            lineTo(230f, 158f)
        }
        // Swept hair: the heavy mass, then the lighter parting line inside it.
        stroke(Ink.LINE, .14f, .40f) {
            moveTo(104f, 100f)
            cubicTo(91f, 80f, 105f, 40f, 133f, 32f)
            cubicTo(159f, 23f, 184f, 35f, 189f, 57f)
        }
        stroke(Ink.DETAIL, .26f, .46f) {
            moveTo(189f, 57f)
            cubicTo(172f, 49f, 157f, 39f, 144f, 41f)
            cubicTo(135f, 57f, 119f, 66f, 106f, 68f)
            lineTo(104f, 100f)
        }
        // Three-quarter face.
        stroke(Ink.LINE, .20f, .54f) {
            moveTo(142f, 43f)
            cubicTo(151f, 51f, 167f, 55f, 180f, 56f)
            lineTo(179f, 76f)
            lineTo(185f, 87f)
            lineTo(176f, 90f)
            cubicTo(174f, 107f, 163f, 117f, 150f, 114f)
            lineTo(139f, 127f)
            lineTo(119f, 111f)
            lineTo(125f, 92f)
            cubicTo(111f, 93f, 109f, 75f, 119f, 72f)
            lineTo(127f, 76f)
            cubicTo(135f, 65f, 139f, 53f, 142f, 43f)
        }
        // Features, inked only after the head has settled.
        stroke(Ink.LINE, .46f, .55f) { moveTo(149f, 72f); quadTo(157f, 68f, 164f, 72f) }
        stroke(Ink.LINE, .49f, .57f) { moveTo(151f, 78f); quadTo(157f, 81f, 162f, 77f) }
        stroke(Ink.DETAIL, .50f, .56f) { moveTo(174f, 73f); lineTo(179f, 75f) }
        stroke(Ink.LINE, .52f, .59f) { moveTo(170f, 97f); quadTo(164f, 100f, 159f, 97f) }
        stroke(Ink.DETAIL, .53f, .60f) { moveTo(153f, 105f); lineTo(162f, 108f) }
        stroke(Ink.DETAIL, .50f, .58f) { moveTo(118f, 79f); quadTo(125f, 76f, 122f, 86f) }
        // Folded collar.
        stroke(Ink.LINE, .42f, .60f) {
            moveTo(119f, 105f); lineTo(143f, 126f); lineTo(166f, 115f); lineTo(183f, 126f)
        }
        stroke(Ink.LINE, .48f, .64f) {
            moveTo(183f, 126f); quadTo(203f, 131f, 217f, 156f)
        }
        stroke(Ink.LINE, .50f, .64f) {
            moveTo(87f, 157f); quadTo(88f, 128f, 108f, 119f)
        }
        // Edge hatching makes the medium explicit without shading the form.
        stroke(Ink.DETAIL, .64f, .82f) {
            moveTo(89f, 113f); lineTo(95f, 108f)
            moveTo(88f, 121f); lineTo(96f, 114f)
            moveTo(89f, 129f); lineTo(97f, 122f)
            moveTo(91f, 137f); lineTo(98f, 131f)
            moveTo(194f, 98f); lineTo(201f, 103f)
            moveTo(195f, 107f); lineTo(206f, 114f)
            moveTo(201f, 118f); lineTo(213f, 126f)
        }
        // The key is laid beside her on the page: the only object the drawing
        // asks you to look at, and the one thing the highlighter touches.
        stroke(Ink.KEY, .60f, .78f) { addOval(230.5f, 102.5f, 249.5f, 121.5f, Path.Direction.CW) }
        stroke(Ink.KEY, .74f, .90f) {
            moveTo(240f, 121f)
            lineTo(240f, 152f)
            lineTo(250f, 152f)
            lineTo(250f, 145f)
            lineTo(245f, 145f)
            lineTo(245f, 138f)
            lineTo(240f, 138f)
        }
    }

    /** Inks the drawing once. Respects the system animation scale. */
    fun playReveal(delayMs: Long = 0L) {
        animator?.cancel()
        if (!ValueAnimator.areAnimatorsEnabled()) {
            reveal = 1f
            return
        }
        reveal = 0f
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            startDelay = delayMs
            duration = ShaiMotion.ILLUSTRATION_MS
            addUpdateListener { reveal = it.animatedValue as Float }
            start()
        }
    }

    fun completeReveal() {
        animator?.cancel()
        animator = null
        reveal = 1f
    }

    override fun onDetachedFromWindow() {
        animator?.cancel()
        animator = null
        super.onDetachedFromWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        val h = if (hMode == MeasureSpec.EXACTLY) {
            MeasureSpec.getSize(heightMeasureSpec)
        } else {
            (w * STUDY_HEIGHT / STUDY_WIDTH).toInt()
        }
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val progress = reveal
        if (progress <= 0f || width == 0 || height == 0) return
        val dp = resources.displayMetrics.density
        val scale = minOf(width / STUDY_WIDTH, height / STUDY_HEIGHT)
        val dx = (width - STUDY_WIDTH * scale) * .5f
        val dy = (height - STUDY_HEIGHT * scale) * .5f

        canvas.save()
        canvas.translate(dx, dy)
        canvas.scale(scale, scale)

        // One highlighter mark, laid under the key after its outline is inked.
        val wash = local(progress, .86f, 1f)
        if (wash > 0f) {
            linePaint.style = Paint.Style.FILL
            linePaint.color = ShaiPalette.blend(ShaiPalette.highlight, ShaiPalette.gold, phase)
            val paperOnly = (1f - phase).coerceIn(0f, 1f)
            linePaint.alpha = (150f * smooth(wash) * paperOnly).toInt().coerceIn(0, 255)
            canvas.drawCircle(240f, 112f, 17f, linePaint)
            washRect.set(234f, 120f, 256f, 158f)
            canvas.drawRoundRect(washRect, 5f, 5f, linePaint)
        }
        linePaint.alpha = 255
        linePaint.style = Paint.Style.STROKE

        for (stroke in strokes) {
            val local = local(progress, stroke.start, stroke.end)
            if (local <= 0f) continue
            linePaint.color = when (stroke.ink) {
                Ink.LINE -> ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase)
                Ink.DETAIL -> ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.lilac, phase)
                Ink.KEY -> ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.gold, phase)
            }
            linePaint.strokeWidth = when (stroke.ink) {
                Ink.LINE -> 1.7f * dp
                Ink.DETAIL -> 1.0f * dp
                Ink.KEY -> 2.4f * dp
            }
            linePaint.alpha = when (stroke.ink) {
                Ink.DETAIL -> ((if (phase < .5f) 190f else 150f)).toInt()
                else -> 255
            }
            if (local >= 1f) {
                canvas.drawPath(stroke.path, linePaint)
            } else {
                measure.setPath(stroke.path, false)
                val length = measure.length
                if (length <= 0f) continue
                segment.reset()
                measure.getSegment(0f, length * smooth(local), segment, true)
                canvas.drawPath(segment, linePaint)
            }
        }
        linePaint.alpha = 255
        canvas.restore()
    }

    private fun local(progress: Float, start: Float, end: Float): Float {
        if (end <= start) return if (progress >= end) 1f else 0f
        return ((progress - start) / (end - start)).coerceIn(0f, 1f)
    }

    /** Slow in, slow out: a pen does not start or stop at full speed. */
    private fun smooth(t: Float): Float = t * t * (3f - 2f * t)

    private companion object {
        const val STUDY_WIDTH = 320f
        const val STUDY_HEIGHT = 184f
    }
}
