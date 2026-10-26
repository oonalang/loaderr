package astral.loader

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.SystemClock
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

/**
 * The sheet's changing tip line.
 *
 * One TextView owns the whole loop: the message, the measured highlighter, the
 * Buy now affordance and the accessibility state. Nothing on the page is
 * rebuilt when a tip changes, and the timer is tied to the view being both
 * attached and actually visible.
 */
class RotatingHeadlineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        const val HOLD_MS = ShaiMotion.HEADLINE_HOLD_MS
        const val FADE_OUT_MS = ShaiMotion.HEADLINE_FADE_OUT_MS
        const val FADE_IN_MS = ShaiMotion.HEADLINE_FADE_IN_MS
        const val SHOP_URL = "https://shaistore.vercel.app"
        const val BUY_NOW_INDEX = 2
        val MESSAGES = arrayOf(
            "Shai Premium is better.",
            "Shai updates every week!",
            "Buy now!",
            "Suggest more feats!",
            "Hey did you know?",
            "Promo drops every week too!"
        )
    }

    /** Supplies the external navigation; the view never opens anything itself. */
    var onBuyNowClick: (() -> Unit)? = null

    private val density = resources.displayMetrics.density
    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = ShaiPalette.highlight
    }
    private val focusPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f * density
        color = ShaiPalette.clayPeriDeep
    }
    private val highlightPath = Path()
    private val highlightRect = RectF()
    private val advanceRunnable = Runnable { advanceMessage() }

    private var messageIndex = 0
    private var clay = false
    private var rotationEnabled = true
    private var rotationScheduled = false
    private var lastStoreLaunchAt = 0L

    init {
        includeFontPadding = false
        gravity = Gravity.START or Gravity.CENTER_VERTICAL
        minLines = 2
        maxLines = 2
        ellipsize = TextUtils.TruncateAt.END
        setLineSpacing(0f, 1f)
        setPadding(dp(4), dp(2), dp(8), dp(6))
        setAutoSizeTextTypeUniformWithConfiguration(24, 30, 1, TypedValue.COMPLEX_UNIT_SP)
        // minLines alone does not reserve two real line boxes once auto-size is
        // involved. Pin the space at the largest permitted line height so a
        // one-line tip and a two-line tip occupy the same composition.
        val maxLine = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 30f, resources.displayMetrics
        ) * 1.18f
        minHeight = (maxLine * 2 + compoundPaddingTop + compoundPaddingBottom).toInt()
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
        accessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_POLITE
        setOnClickListener {
            if (!clay && messageIndex == BUY_NOW_INDEX) {
                val now = SystemClock.elapsedRealtime()
                // A tap on a moving target must not open two tabs.
                if (now - lastStoreLaunchAt < 900L) return@setOnClickListener
                lastStoreLaunchAt = now
                onBuyNowClick?.invoke()
            }
        }
        applyMessage()
    }

    /**
     * 0 = the sheet; 1 = the finished clay page. In clay the rotation stops and
     * the same TextView becomes the static success heading.
     */
    fun setPhase(signedIn: Boolean) {
        if (clay == signedIn) return
        clay = signedIn
        animate().cancel()
        alpha = 1f
        removeCallbacks(advanceRunnable)
        rotationScheduled = false
        if (clay) {
            setTextColor(ShaiPalette.clayInk)
            typeface = ShaiType.display(context)
            letterSpacing = -0.01f
            paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            isClickable = false
            isFocusable = false
            contentDescription = null
            accessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_NONE
            text = ""
        } else {
            setTextColor(headlineColors())
            typeface = ShaiType.sheetDisplay(context)
            letterSpacing = -0.015f
            accessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_POLITE
            applyMessage()
            syncRotation()
        }
        invalidate()
    }

    /** Pauses the loop without losing the current tip, for a stopped window. */
    fun stopRotation() {
        rotationEnabled = false
        suspendRotation()
    }

    /** Resumes the loop only when the view is actually on screen. */
    fun startRotation() {
        rotationEnabled = true
        syncRotation()
    }

    /** Called from the activity's teardown; detachment also calls this. */
    fun release() {
        removeCallbacks(advanceRunnable)
        rotationScheduled = false
        animate().cancel()
    }

    private fun applyMessage() {
        if (clay) return
        val message = MESSAGES[messageIndex]
        val buyNow = messageIndex == BUY_NOW_INDEX
        text = message
        setTextColor(headlineColors())
        paintFlags = if (buyNow) {
            paintFlags or Paint.UNDERLINE_TEXT_FLAG
        } else {
            paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        }
        isClickable = buyNow
        isFocusable = buyNow
        contentDescription = if (buyNow) {
            "Buy now! Opens the SHAI store"
        } else {
            message
        }
        invalidate()
    }

    private fun headlineColors(): ColorStateList = ColorStateList(
        arrayOf(intArrayOf(android.R.attr.state_pressed), intArrayOf()),
        intArrayOf(ShaiPalette.pencil, ShaiPalette.ink)
    )

    private fun advanceMessage() {
        rotationScheduled = false
        if (!shouldRotate()) return
        if (!ValueAnimator.areAnimatorsEnabled()) {
            messageIndex = (messageIndex + 1) % MESSAGES.size
            applyMessage()
            syncRotation()
            return
        }
        animate().cancel()
        animate()
            .alpha(0f)
            .setDuration(FADE_OUT_MS)
            .setInterpolator(ShaiMotion.easeOut)
            .withEndAction {
                if (!shouldRotate()) {
                    alpha = 1f
                    syncRotation()
                    return@withEndAction
                }
                messageIndex = (messageIndex + 1) % MESSAGES.size
                applyMessage()
                animate()
                    .alpha(1f)
                    .setDuration(FADE_IN_MS)
                    .setInterpolator(ShaiMotion.easeOut)
                    .withEndAction { syncRotation() }
                    .start()
            }
            .start()
    }

    private fun syncRotation() {
        if (shouldRotate()) {
            if (!rotationScheduled) {
                rotationScheduled = true
                postDelayed(advanceRunnable, HOLD_MS)
            }
        } else {
            suspendRotation()
        }
    }

    private fun suspendRotation() {
        removeCallbacks(advanceRunnable)
        rotationScheduled = false
        animate().cancel()
        alpha = 1f
    }

    private fun shouldRotate(): Boolean =
        rotationEnabled && !clay && isAttachedToWindow &&
            windowVisibility == View.VISIBLE && visibility == View.VISIBLE && isShown

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        syncRotation()
    }

    override fun onDetachedFromWindow() {
        release()
        super.onDetachedFromWindow()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        syncRotation()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        syncRotation()
    }

    override fun onVisibilityAggregated(isVisible: Boolean) {
        super.onVisibilityAggregated(isVisible)
        syncRotation()
    }

    override fun onDraw(canvas: Canvas) {
        if (!clay) drawHighlighter(canvas)
        super.onDraw(canvas)
    }

    /**
     * The marker follows the measured text, line by line, instead of tinting the
     * whole view. A one-line tip gets one stroke; a two-line tip gets two.
     */
    private fun drawHighlighter(canvas: Canvas) {
        val textLayout = layout ?: return
        if (textLayout.lineCount == 0) return
        highlightPaint.alpha = 196
        val offsetX = totalPaddingLeft + scrollX
        // Gravity is start + center so a one-line tip sits inside the reserved
        // two-line box. The marker has to follow that vertical offset too.
        val available = height - compoundPaddingTop - compoundPaddingBottom
        val verticalOffset = ((available - textLayout.height) * .5f).coerceAtLeast(0f)
        val offsetY = compoundPaddingTop + verticalOffset + scrollY
        for (line in 0 until textLayout.lineCount) {
            val left = textLayout.getLineLeft(line)
            val right = textLayout.getLineRight(line)
            if (right - left < 2f * density) continue
            val top = textLayout.getLineTop(line) + offsetY
            val bottom = textLayout.getLineBottom(line) + offsetY
            val lineHeight = bottom - top
            if (lineHeight <= 0f) continue
            val x0 = offsetX + left - 3f * density
            val x1 = offsetX + right + 5f * density
            val y0 = top + lineHeight * .46f
            val y1 = bottom - lineHeight * .06f
            val width = x1 - x0
            val reach = y1 - y0
            highlightPath.reset()
            highlightPath.moveTo(x0 + width * .012f, y0 + reach * .12f)
            highlightPath.cubicTo(
                x0 + width * .28f, y0 - reach * .06f,
                x0 + width * .62f, y0 + reach * .07f,
                x1, y0
            )
            highlightPath.lineTo(x1 - width * .006f, y1 - reach * .04f)
            highlightPath.cubicTo(
                x0 + width * .66f, y1 + reach * .07f,
                x0 + width * .30f, y1 - reach * .08f,
                x0, y1
            )
            highlightPath.close()
            canvas.drawPath(highlightPath, highlightPaint)
        }
        highlightPaint.alpha = 255
        if (isFocused && isClickable) {
            highlightRect.set(
                2f * density,
                1f * density,
                width - 2f * density,
                height - 1f * density
            )
            canvas.drawRoundRect(highlightRect, 8f * density, 8f * density, focusPaint)
        }
    }

    private fun dp(value: Int): Int = (value * density).toInt()
}
