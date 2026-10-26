package astral.loader

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.button.MaterialButton
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class AboutDialog(context: Context, private val signedIn: Boolean = false) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val back = findViewById<View>(R.id.about_back)
        back.setOnClickListener { dismiss() }
        installPress(back)
        val telegram = findViewById<View>(R.id.about_telegram)
        telegram.setOnClickListener { openTelegram() }
        installPress(telegram)
        bindDetails()
        applyPhaseStyle()
        playbackEntrance()
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
            R.id.about_logo,
            R.id.about_pill,
            R.id.about_details_card,
            R.id.about_info_card,
            R.id.about_telegram
        )
        ids.forEachIndexed { index, id ->
            val view = findViewById<View>(id)
            view.animate().cancel()
            view.alpha = 0f
            view.translationY = 10f * density
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(40L + index * 45L)
                .setDuration(280L)
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
        val details = listOf(
            Detail(R.id.detail_developer, R.drawable.ic_user, "Application", "SHAI LOADER"),
            Detail(R.id.detail_username, R.drawable.ic_about, "Username", "@Shaiaintshyy"),
            Detail(R.id.detail_channel, R.drawable.ic_telegram, "Channel", "t.me/Shaimainch"),
            Detail(R.id.detail_version, R.drawable.ic_package, "App Version", appVersion())
        )

        details.forEach { detail ->
            val row = findViewById<View>(detail.id)
            row.findViewById<ImageView>(R.id.about_detail_icon).setImageResource(detail.icon)
            row.findViewById<TextView>(R.id.about_detail_label).text = detail.label
            row.findViewById<TextView>(R.id.about_detail_value).text = detail.value
        }
    }


    private fun appVersion(): String = try {
        context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "Unknown"
    } catch (_: Exception) {
        "Unknown"
    }

    private fun applyPhaseStyle() {
        val base = if (signedIn) ShaiPalette.clayBg else ShaiPalette.paper
        val foreground = if (signedIn) ShaiPalette.clayInk else ShaiPalette.ink
        val secondary = if (signedIn) ShaiPalette.clayMuted else ShaiPalette.pencil
        val accent = if (signedIn) ShaiPalette.clayInk else ShaiPalette.ink
        findViewById<View>(R.id.about_scroll).setBackgroundColor(base)
        listOf(R.id.about_details_card, R.id.about_info_card).forEach { id ->
            findViewById<View>(id).background = PhaseSurfaceDrawable(
                context, PhaseSurfaceDrawable.Kind.CARD).apply { phase = if (signedIn) 1f else 0f }
        }
        fun visit(view: View) {
            when (view) {
                is TextView -> {
                    val sp = view.textSize / context.resources.displayMetrics.scaledDensity
                    view.setTextColor(if (sp >= 13f) foreground else secondary)
                    if (signedIn) {
                        // Fredoka heads this page, Nunito reads it; no serif fallback.
                        view.typeface = when {
                            sp >= 20f -> ShaiType.display(context)
                            sp >= 14f -> ShaiType.label(context)
                            else -> ShaiType.body(context)
                        }
                        view.letterSpacing = 0f
                    }
                    view.setBackgroundColor(Color.TRANSPARENT)
                }
                is ImageView -> if (view.id != View.NO_ID) view.setColorFilter(accent)
            }
            if (view is ViewGroup) for (i in 0 until view.childCount) visit(view.getChildAt(i))
        }
        visit(findViewById(R.id.about_scroll))
        // Keep the Telegram button consistent with the channel shown above.
        findViewById<MaterialButton>(R.id.about_telegram).apply {
            backgroundTintList = ColorStateList.valueOf(
                if (signedIn) ShaiPalette.clayPink else ShaiPalette.paperCard)
            setTextColor(foreground)
            strokeWidth = if (signedIn) 0 else (1.2f * context.resources.displayMetrics.density).toInt()
            strokeColor = ColorStateList.valueOf(if (signedIn) ShaiPalette.clayPinkDeep else ShaiPalette.ink)
            cornerRadius = ((if (signedIn) 22f else 4f) * context.resources.displayMetrics.density).toInt()
            typeface = if (signedIn) ShaiType.label(context) else Typeface.DEFAULT
        }
    }

    private fun openTelegram() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Shaimainch"))
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
