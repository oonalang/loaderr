package astral.loader

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Choreographer
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import java.net.NetworkInterface

/**
 * The cover page. It exists to run the real launch checks — VPN gate and the
 * one-time notification prompt — and leaves as soon as they settle. It never
 * waits for the drawing to finish.
 */
class SplashActivity : AppCompatActivity() {

    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private lateinit var notificationPermissionRequester: NotificationPermissionRequester
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var leaving = false
    private var checksSettled = false

    companion object {
        var detectedVpn: String? = null

        fun getDetectedVPN(): String? {
            return try {
                NetworkInterface.getNetworkInterfaces()?.toList()?.firstOrNull { net ->
                    net.isUp &&
                    net.interfaceAddresses.isNotEmpty() &&
                    (
                        (net.name.contains("tun", true) ||
                        net.name.contains("ppp", true) ||
                        net.name.contains("wg", true) ||
                        net.name.contains("tap", true)) &&
                        !net.name.contains("vowifi", true) &&
                        !net.name.contains("rmnet", true)
                    )
                }?.name
            } catch (e: Exception) {
                null
            }
        }

        fun showDialog(context: AppCompatActivity, title: String, message: String) {
            AlertDialog.Builder(context, R.style.DarkAlertDialog)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->
                    context.finish()
                }
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(null)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.fade_in, R.anim.fade_out)
        } else {
            @Suppress("DEPRECATION")
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        setContentView(R.layout.activity_splash)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        val brand = findViewById<android.widget.TextView>(R.id.splash_brand)
        val art = findViewById<ShaiIllustrationView>(R.id.splash_art)
        val content = findViewById<android.view.View>(R.id.splash_content)
        ViewCompat.setOnApplyWindowInsetsListener(content) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            view.setPadding(
                view.paddingLeft,
                view.paddingTop + bars.top,
                view.paddingRight,
                view.paddingBottom + bars.bottom
            )
            insets
        }

        val sweep = HighlightSweepDrawable().apply {
            if (!ValueAnimator.areAnimatorsEnabled()) progress = 1f
        }
        brand.background = sweep
        brand.setPadding(
            (4 * resources.displayMetrics.density).toInt(),
            0,
            (8 * resources.displayMetrics.density).toInt(),
            0
        )
        if (ValueAnimator.areAnimatorsEnabled()) {
            ValueAnimator.ofFloat(0f, 1f).apply {
                startDelay = 90L
                duration = ShaiMotion.HIGHLIGHT_MS
                interpolator = ShaiMotion.easeOut
                addUpdateListener { sweep.progress = it.animatedValue as Float }
                start()
            }
        }
        art.playReveal(120L)

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Notifications are off", Toast.LENGTH_SHORT).show()
            }
            notificationPermissionRequester.onPermissionResult()
        }
        notificationPermissionRequester = NotificationPermissionRequester(this, permissionLauncher)

        // The real gate runs immediately; navigation waits only for the checks,
        // plus two frames so the cover has actually painted. No animation wait.
        Choreographer.getInstance().postFrameCallback {
            notificationPermissionRequester.request {
                checksSettled = true
                window.decorView.postOnAnimationDelayed({ if (checksSettled) openLoader() }, 32L)
            }
        }
    }

    private fun openLoader() {
        if (leaving || isFinishing || isDestroyed) return
        leaving = true
        detectedVpn = getDetectedVPN()
        if (detectedVpn != null) {
            showDialog(
                this,
                "VPN DETECTED",
                "Detected VPN Interface:\n$detectedVpn\n\nPlease disable your VPN and try again."
            )
            return
        }
        startActivity(
            Intent(this, MainActivity::class.java),
            ActivityOptionsCompat.makeCustomAnimation(this, R.anim.fade_in, R.anim.fade_out).toBundle()
        )
        finish()
    }

}
