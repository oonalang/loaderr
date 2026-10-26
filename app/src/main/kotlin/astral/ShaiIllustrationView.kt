package astral.loader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 * The keykeeper, inked on the page and then sculpted out of it.
 *
 * One illustration carries the whole identity. On paper every line is a stroked
 * contour revealed through [PathMeasure]; as the clay phase arrives the same
 * coordinates become a moulded form: the silhouette fills with matte pink clay,
 * the face is a cream inset, the construction hatching and the ground line are
 * drawn out, and the key inflates into a mint clay object. Nothing swaps: the
 * drawing is the model.
 */
class ShaiIllustrationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private enum class Ink { FRAME, FEATURE, DETAIL, HATCH, GROUND, KEY }

    private class Stroke(val path: Path, val ink: Ink, val start: Float, val end: Float)

    private val strokes = ArrayList<Stroke>(24)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val measure = PathMeasure()
    private val segment = Path()
    private val washRect = RectF()
    private val formRect = RectF()
    private var animator: ValueAnimator? = null

    /** The silhouette and the face opening, filled only once clay arrives. */
    private val cloak = Path()
    private val clayGroove = Path()
    private val clayFace = Path()
    private val FACE_OVAL = RectF(122f, 50f, 178f, 121f)
    private val face = Path()
    private var cloakShader: LinearGradient? = null

    /** 0 = blank page, 1 = finished drawing. */
    var reveal: Float = 1f
        set(value) {
            field = value.coerceIn(0f, 1f)
            invalidate()
        }

    /** Paper sheet (0) to moulded clay (1). */
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
        stroke(Ink.GROUND, 0f, .20f) {
            moveTo(30f, 160f); cubicTo(82f, 156f, 204f, 163f, 290f, 158f)
        }
        stroke(Ink.GROUND, .06f, .22f) {
            moveTo(36f, 166f); lineTo(113f, 166f)
            moveTo(234f, 165f); lineTo(279f, 165f)
        }
        // Hood and shoulder silhouette, in one breath.
        stroke(Ink.FRAME, .03f, .38f) {
            moveTo(93f, 157f)
            cubicTo(81f, 130f, 83f, 91f, 95f, 63f)
            cubicTo(104f, 34f, 128f, 18f, 151f, 22f)
            cubicTo(181f, 17f, 203f, 44f, 197f, 76f)
            cubicTo(193f, 94f, 197f, 109f, 211f, 124f)
            lineTo(230f, 158f)
        }
        // Swept hair: the heavy mass, then the lighter parting line inside it.
        stroke(Ink.FRAME, .14f, .40f) {
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
        stroke(Ink.FRAME, .20f, .54f) {
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
        stroke(Ink.FEATURE, .46f, .55f) { moveTo(149f, 72f); quadTo(157f, 68f, 164f, 72f) }
        stroke(Ink.FEATURE, .49f, .57f) { moveTo(151f, 78f); quadTo(157f, 81f, 162f, 77f) }
        stroke(Ink.DETAIL, .50f, .56f) { moveTo(174f, 73f); lineTo(179f, 75f) }
        stroke(Ink.FEATURE, .52f, .59f) { moveTo(170f, 97f); quadTo(164f, 100f, 159f, 97f) }
        stroke(Ink.DETAIL, .53f, .60f) { moveTo(153f, 105f); lineTo(162f, 108f) }
        stroke(Ink.DETAIL, .50f, .58f) { moveTo(118f, 79f); quadTo(125f, 76f, 122f, 86f) }
        // Folded collar.
        stroke(Ink.FRAME, .42f, .60f) {
            moveTo(119f, 105f); lineTo(143f, 126f); lineTo(166f, 115f); lineTo(183f, 126f)
        }
        stroke(Ink.FRAME, .48f, .64f) {
            moveTo(183f, 126f); quadTo(203f, 131f, 217f, 156f)
        }
        stroke(Ink.FRAME, .50f, .64f) {
            moveTo(87f, 157f); quadTo(88f, 128f, 108f, 119f)
        }
        // Edge hatching makes the medium explicit without shading the form.
        stroke(Ink.HATCH, .64f, .82f) {
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

        // The same geometry, closed, so the paper drawing can become a form.
        cloak.moveTo(93f, 157f)
        cloak.cubicTo(81f, 130f, 83f, 91f, 95f, 63f)
        cloak.cubicTo(104f, 34f, 128f, 18f, 151f, 22f)
        cloak.cubicTo(181f, 17f, 203f, 44f, 197f, 76f)
        cloak.cubicTo(193f, 94f, 197f, 109f, 211f, 124f)
        cloak.lineTo(230f, 158f)
        cloak.close()

        clayFace.addOval(FACE_OVAL, Path.Direction.CW)

        face.moveTo(142f, 43f)
        face.cubicTo(151f, 51f, 167f, 55f, 180f, 56f)
        face.lineTo(179f, 76f)
        face.lineTo(185f, 87f)
        face.lineTo(176f, 90f)
        face.cubicTo(174f, 107f, 163f, 117f, 150f, 114f)
        face.lineTo(139f, 127f)
        face.lineTo(119f, 111f)
        face.lineTo(125f, 92f)
        face.cubicTo(111f, 93f, 109f, 75f, 119f, 72f)
        face.lineTo(127f, 76f)
        face.cubicTo(135f, 65f, 139f, 53f, 142f, 43f)
        face.close()
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

        val clay = phase
        // The pen leaves before the clay sets, so the two never overlap as a
        // double exposure. This is the whole trick of the handover.
        val form = (((clay - .34f) / .66f).coerceIn(0f, 1f))
        if (form > 0.01f) drawClayForm(canvas, form, smooth(progress))

        // One highlighter mark, laid under the key after its outline is inked.
        val wash = local(progress, .86f, 1f)
        if (wash > 0f && clay < .99f) {
            fillPaint.style = Paint.Style.FILL
            fillPaint.color = ShaiPalette.highlight
            val paperOnly = (1f - clay * 1.6f).coerceIn(0f, 1f)
            fillPaint.alpha = (150f * smooth(wash) * paperOnly).toInt().coerceIn(0, 255)
            canvas.drawCircle(240f, 112f, 17f, fillPaint)
            washRect.set(234f, 120f, 256f, 158f)
            canvas.drawRoundRect(washRect, 5f, 5f, fillPaint)
        }
        fillPaint.alpha = 255
        linePaint.style = Paint.Style.STROKE

        for (stroke in strokes) {
            val local = local(progress, stroke.start, stroke.end)
            if (local <= 0f) continue
            val ink = strokeInk(stroke.ink, clay)
            if (ink.alpha <= 0) continue
            linePaint.color = ink.color
            linePaint.strokeWidth = ink.width * dp
            linePaint.alpha = ink.alpha.coerceIn(0, 255)
            val path = if (local >= 1f) stroke.path else segmentOf(stroke.path, smooth(local)) ?: continue
            canvas.drawPath(path, linePaint)
            // Clay is lit: grooves take a light below them, pellets a rim above.
            val accent = ink.accent
            if (accent != null && accent.alpha > 0) {
                val shift = accent.offset * dp
                canvas.save()
                canvas.translate(shift, shift)
                linePaint.color = accent.color
                linePaint.alpha = accent.alpha.coerceIn(0, 255)
                linePaint.strokeWidth = ink.width * accent.scale * dp
                canvas.drawPath(path, linePaint)
                canvas.restore()
            }
        }
        linePaint.alpha = 255
        canvas.restore()
    }

    /** The paper drawing, sculpted: filled silhouette, cream face, clay key. */
    private fun drawClayForm(canvas: Canvas, clay: Float, inked: Float) {
        // The figure sits on the page: a soft contact shadow replaces the ground line.
        val contact = clay * .11f * inked
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = ShaiPalette.clayInk
        for (i in 4 downTo 1) {
            val grow = i * 3.4f
            fillPaint.alpha = (255 * contact * (1f - i / 5f) * .55f).toInt().coerceIn(0, 255)
            formRect.set(83f - grow, 152f - grow * .22f, 239f + grow, 163f + grow * .22f)
            canvas.drawOval(formRect, fillPaint)
        }
        fillPaint.alpha = 255

        // Cloak: matte pink clay, lit from the upper left.
        if (cloakShader == null) {
            cloakShader = LinearGradient(
                95f, 22f, 230f, 158f,
                ShaiPalette.clayPink, ShaiPalette.clayPinkDeep,
                Shader.TileMode.CLAMP
            )
        }
        fillPaint.shader = cloakShader
        fillPaint.alpha = (255 * clay * inked).toInt().coerceIn(0, 255)
        canvas.drawPath(cloak, fillPaint)
        fillPaint.shader = null

        // Rim light, clipped to the form so only its inner edge catches the light.
        canvas.save()
        canvas.clipPath(cloak)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 5f
        linePaint.color = Color.WHITE
        linePaint.alpha = (255 * .38f * clay * inked).toInt().coerceIn(0, 255)
        canvas.drawPath(cloak, linePaint)
        canvas.restore()

        // The face is re-authored in clay: a front-facing plane inside the same
        // hooded silhouette. A filled profile reads as a mask at this size.
        val strength = clay * inked
        fillPaint.style = Paint.Style.FILL
        fillPaint.color = ShaiPalette.blend(ShaiPalette.clayPink, ShaiPalette.clayRaised, .55f)
        fillPaint.alpha = (255 * strength).toInt().coerceIn(0, 255)
        canvas.drawOval(FACE_OVAL, fillPaint)
        fillPaint.alpha = 255

        // The hood's edge sits *in front of* the face, so it casts into it.
        canvas.save()
        canvas.clipPath(clayFace)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 7f
        linePaint.color = ShaiPalette.clayPinkDeep
        linePaint.alpha = (165 * strength).toInt().coerceIn(0, 255)
        canvas.drawOval(FACE_OVAL, linePaint)
        canvas.restore()

        // Carved features: a groove, then the light that catches its lower lip.
        clayGroove.reset()
        clayGroove.moveTo(133f, 79f); clayGroove.quadTo(139f, 73f, 145f, 79f)
        clayGroove.moveTo(156f, 78f); clayGroove.quadTo(162f, 72f, 168f, 78f)
        clayGroove.moveTo(150f, 84f); clayGroove.quadTo(153f, 91f, 147f, 92f)
        clayGroove.moveTo(141f, 100f); clayGroove.quadTo(150f, 107f, 159f, 100f)
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 3.2f
        linePaint.color = ShaiPalette.clayPinkDeep
        linePaint.alpha = (215 * strength).toInt().coerceIn(0, 255)
        canvas.drawPath(clayGroove, linePaint)
        canvas.save()
        canvas.translate(1.1f, 1.3f)
        linePaint.color = Color.WHITE
        linePaint.alpha = (70 * strength).toInt().coerceIn(0, 255)
        canvas.drawPath(clayGroove, linePaint)
        canvas.restore()

        // The key is its own object in clay: a bow, a straight stem, one tooth,
        // so it still reads as a key at 20dp rather than a mint lump.
        clayGroove.reset()
        clayGroove.addCircle(242f, 110f, 11f, Path.Direction.CW)
        clayGroove.moveTo(242f, 121f)
        clayGroove.lineTo(242f, 151f)
        clayGroove.moveTo(242f, 143f)
        clayGroove.lineTo(253f, 143f)
        canvas.save()
        canvas.translate(1.8f, 2.2f)
        linePaint.strokeWidth = 6.4f
        linePaint.color = ShaiPalette.clayInk
        linePaint.alpha = (26 * strength).toInt().coerceIn(0, 255)
        canvas.drawPath(clayGroove, linePaint)
        canvas.restore()
        linePaint.strokeWidth = 6.4f
        linePaint.color = ShaiPalette.clayMint
        linePaint.alpha = (255 * strength).toInt().coerceIn(0, 255)
        canvas.drawPath(clayGroove, linePaint)
        canvas.save()
        canvas.translate(-1.3f, -1.5f)
        linePaint.strokeWidth = 2.4f
        linePaint.color = Color.WHITE
        linePaint.alpha = (120 * strength).toInt().coerceIn(0, 255)
        canvas.drawPath(clayGroove, linePaint)
        canvas.restore()
    }

    private class InkStyle(
        val color: Int,
        val width: Float,
        val alpha: Int,
        val accent: Accent? = null
    )

    /** A second pass: engraved grooves take light, clay pellets take a rim highlight. */
    private class Accent(val color: Int, val alpha: Int, val scale: Float, val offset: Float)

    private fun strokeInk(ink: Ink, clay: Float): InkStyle {
        return when (ink) {
            // The outline is replaced by the filled form, so it leaves the page.
            Ink.FRAME -> InkStyle(
                ShaiPalette.ink, 1.7f,
                (255 * (1f - clay * 3.1f).coerceAtLeast(0f)).toInt()
            )
            // The inked profile is paper only; clay draws its own carved face.
            Ink.FEATURE -> InkStyle(
                ShaiPalette.ink, 1.7f,
                (255 * (1f - clay * 2.6f).coerceAtLeast(0f)).toInt()
            )
            Ink.DETAIL -> InkStyle(
                ShaiPalette.pencil, 1f,
                (190 * (1f - clay * 2.6f).coerceAtLeast(0f)).toInt()
            )
            // Construction marks: hatching and the ground line are blueprint only.
            Ink.HATCH -> InkStyle(
                ShaiPalette.pencil, 1f,
                (190 * (1f - clay * 1.9f).coerceAtLeast(0f)).toInt()
            )
            Ink.GROUND -> InkStyle(
                ShaiPalette.pencil, 1f,
                (190 * (1f - clay * 1.9f).coerceAtLeast(0f)).toInt()
            )
            Ink.KEY -> InkStyle(
                ShaiPalette.ink, 2.4f,
                (255 * (1f - clay * 2.8f).coerceAtLeast(0f)).toInt()
            )
        }
    }

    private fun segmentOf(path: Path, fraction: Float): Path? {
        measure.setPath(path, false)
        val length = measure.length
        if (length <= 0f) return null
        segment.reset()
        measure.getSegment(0f, length * fraction, segment, true)
        return segment
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
