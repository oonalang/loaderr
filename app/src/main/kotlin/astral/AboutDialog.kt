package astral.loader

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

        findViewById<View>(R.id.about_back).setOnClickListener { dismiss() }
        findViewById<View>(R.id.about_telegram).setOnClickListener { openTelegram() }
        bindDetails()
        applyPhaseStyle()
    }

    override fun onStart() {
        super.onStart()
        window?.let { dialogWindow ->
            dialogWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val background = if (signedIn) ShaiPalette.void else ShaiPalette.paper
            dialogWindow.statusBarColor = background
            dialogWindow.navigationBarColor = background
            @Suppress("DEPRECATION")
            dialogWindow.decorView.systemUiVisibility = if (signedIn) 0 else (
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR)
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
        val base = if (signedIn) ShaiPalette.void else ShaiPalette.paper
        val foreground = if (signedIn) Color.rgb(245, 236, 255) else ShaiPalette.ink
        val secondary = if (signedIn) ShaiPalette.muted else ShaiPalette.pencil
        findViewById<View>(R.id.about_scroll).setBackgroundColor(base)
        listOf(R.id.about_details_card, R.id.about_info_card).forEach { id ->
            findViewById<View>(id).background = PhaseSurfaceDrawable(
                PhaseSurfaceDrawable.Kind.CARD).apply { phase = if (signedIn) 1f else 0f }
        }
        fun visit(view: View) {
            when (view) {
                is TextView -> {
                    val normalText = view.textSize / context.resources.displayMetrics.scaledDensity >= 13f
                    view.setTextColor(if (normalText) foreground else secondary)
                    if (!signedIn && normalText) view.typeface = Typeface.create("casual", Typeface.BOLD)
                    view.setBackgroundColor(Color.TRANSPARENT)
                }
                is ImageView -> if (view.id != View.NO_ID) view.setColorFilter(if (signedIn) ShaiPalette.lilac else ShaiPalette.ink)
            }
            if (view is ViewGroup) for (i in 0 until view.childCount) visit(view.getChildAt(i))
        }
        visit(findViewById(R.id.about_scroll))
        // Keep the Telegram button consistent with the channel shown above.
        findViewById<MaterialButton>(R.id.about_telegram).apply {
            backgroundTintList = ColorStateList.valueOf(
                if (signedIn) ShaiPalette.darkPanel else ShaiPalette.paperCard)
            setTextColor(foreground)
            strokeColor = ColorStateList.valueOf(if (signedIn) ShaiPalette.violet else ShaiPalette.ink)
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
