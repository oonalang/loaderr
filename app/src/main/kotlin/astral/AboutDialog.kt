package astral.loader

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * About is part of the product, not a legacy settings sheet. When it is opened
 * after redemption it is the same clay page; opened from the sheet it is still
 * a notebook page. Both states share one layout and one set of surfaces.
 */
internal class AboutDialog(
    context: Context,
    private val signedIn: Boolean = false,
    private val sounds: ShaiSounds? = null
) : Dialog(context) {

    private val phase: Float get() = if (signedIn) 1f else 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        applyInsets()

        val back = findViewById<View>(R.id.about_back)
        back.setOnClickListener {
            sounds?.play(ShaiSounds.Cue.TAP)
            dismiss()
        }
        installPress(back)
        val telegram = findViewById<View>(R.id.about_telegram)
        telegram.setOnClickListener {
            sounds?.play(ShaiSounds.Cue.TAP)
            openTelegram()
        }
        installPress(telegram)

        bindDetails()
        applyPhaseStyle()
        playbackEntrance()
    }

    private fun applyInsets() {
        val scroll = findViewById<View>(R.id.about_scroll)
        ViewCompat.setOnApplyWindowInsetsListener(scroll) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(0, bars.top, 0, bars.bottom)
            insets
        }
        ViewCompat.requestApplyInsets(scroll)
    }

    /** Same short press language as the main screen, adapted to a dialog. */
    private fun installPress(view: View) {
        view.setOnTouchListener { target, event ->
            when (event.actionMasked) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    target.animate().cancel()
                    if (ValueAnimator.areAnimatorsEnabled()) {
                        target.animate().scaleX(.97f).scaleY(.97f)
                            .setDuration(ShaiMotion.PRESS_IN_MS)
                            .setInterpolator(ShaiMotion.easeOut)
                            .start()
                    }
                }
                android.view.MotionEvent.ACTION_UP,
                android.view.MotionEvent.ACTION_CANCEL -> {
                    target.animate().cancel()
                    target.animate().scaleX(1f).scaleY(1f)
                        .setDuration(ShaiMotion.PRESS_OUT_MS)
                        .setInterpolator(ShaiMotion.easeOut)
                        .start()
                }
            }
            false
        }
    }

    private fun playbackEntrance() {
        findViewById<View>(R.id.about_scroll).scrollTo(0, 0)
        if (!ValueAnimator.areAnimatorsEnabled()) return
        val density = context.resources.displayMetrics.density
        val ids = intArrayOf(
            R.id.about_back,
            R.id.about_title,
            R.id.about_art,
            R.id.about_details_card,
            R.id.about_telegram,
            R.id.about_credit
        )
        ids.forEachIndexed { index, id ->
            val view = findViewById<View>(id)
            view.animate().cancel()
            view.alpha = 0f
            view.translationY = 10f * density
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(40L + index * 40L)
                .setDuration(260L)
                .setInterpolator(ShaiMotion.easeOut)
                .start()
        }
    }

    override fun onStart() {
        super.onStart()
        window?.let { dialogWindow ->
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val background = if (signedIn) ShaiPalette.clayBg else ShaiPalette.paper
            dialogWindow.statusBarColor = background
            dialogWindow.navigationBarColor = background
            @Suppress("DEPRECATION")
            dialogWindow.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            dialogWindow.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }
    }

    private fun bindDetails() {
        val iconTint = if (signedIn) ShaiPalette.clayMuted else ShaiPalette.pencil
        // The version row is intentionally not listed: a loader whose catalog
        // can force an update dialog never shows a number readers could mistake
        // for the currently supported one.
        val details = listOf(
            Detail(R.id.detail_developer, R.drawable.ic_user, "Application", "SHAI LOADER"),
            Detail(R.id.detail_username, R.drawable.ic_about, "Username", "@Shaiaintshyy")
        )

        details.forEach { detail ->
            val row = findViewById<View>(detail.id)
            row.findViewById<ImageView>(R.id.about_detail_icon).apply {
                setImageResource(detail.icon)
                imageTintList = ColorStateList.valueOf(iconTint)
            }
            row.findViewById<TextView>(R.id.about_detail_label).text = detail.label
            row.findViewById<TextView>(R.id.about_detail_value).text = detail.value
        }
    }

    private fun applyPhaseStyle() {
        val clay = signedIn
        val ink = if (clay) ShaiPalette.clayInk else ShaiPalette.ink
        val muted = if (clay) ShaiPalette.clayMuted else ShaiPalette.pencil
        val background = if (clay) ShaiPalette.clayBg else ShaiPalette.paper

        findViewById<View>(R.id.about_root).setBackgroundColor(background)
        findViewById<View>(R.id.about_scroll).setBackgroundColor(background)
        surface(R.id.about_back, PhaseSurfaceDrawable.Kind.SECONDARY, phase)
        surface(R.id.about_details_card, PhaseSurfaceDrawable.Kind.CARD, phase)
        surface(R.id.about_telegram, PhaseSurfaceDrawable.Kind.SECONDARY, phase)

        findViewById<ShaiIllustrationView>(R.id.about_art).apply {
            this.phase = this@AboutDialog.phase
            completeReveal()
            rotation = if (clay) 0f else -1.5f
        }

        findViewById<ImageButton>(R.id.about_back).imageTintList = ColorStateList.valueOf(ink)
        findViewById<ImageView>(R.id.about_channel_icon).imageTintList = ColorStateList.valueOf(ink)
        findViewById<ImageView>(R.id.about_channel_arrow).imageTintList = ColorStateList.valueOf(muted)

        val display = if (clay) ShaiType.display(context) else ShaiType.sheetDisplay(context)
        val body = if (clay) ShaiType.body(context) else ShaiType.sheet(context)
        val label = if (clay) ShaiType.label(context) else ShaiType.sheetDisplay(context)

        findViewById<TextView>(R.id.about_title).apply {
            typeface = display
            setTextColor(ink)
            letterSpacing = if (clay) -0.008f else -0.015f
        }
        val detailRows = intArrayOf(R.id.detail_developer, R.id.detail_username)
        detailRows.forEach { id ->
            val row = findViewById<View>(id)
            row.findViewById<TextView>(R.id.about_detail_label).apply {
                typeface = body
                setTextColor(muted)
            }
            row.findViewById<TextView>(R.id.about_detail_value).apply {
                typeface = label
                setTextColor(ink)
            }
        }
        findViewById<TextView>(R.id.about_channel_title).apply {
            typeface = label
            setTextColor(ink)
        }
        findViewById<TextView>(R.id.about_channel_value).apply {
            typeface = body
            setTextColor(muted)
        }
        findViewById<TextView>(R.id.about_credit).apply {
            typeface = body
            setTextColor(muted)
        }
    }

    private fun surface(id: Int, kind: PhaseSurfaceDrawable.Kind, phase: Float) {
        findViewById<View>(id).background = PhaseSurfaceDrawable(context, kind).apply {
            this.phase = phase
        }
    }

    private fun openTelegram() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Shaimainch")).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
        }
        runCatching { context.startActivity(intent) }
            .onFailure {
                Toast.makeText(context, "Unable to open Telegram", Toast.LENGTH_SHORT).show()
            }
    }

    private data class Detail(
        val id: Int,
        val icon: Int,
        val label: String,
        val value: String
    )
}
