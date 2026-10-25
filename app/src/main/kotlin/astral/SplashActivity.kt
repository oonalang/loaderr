package astral.loader

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import java.net.NetworkInterface

class SplashActivity : AppCompatActivity() {

    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private lateinit var handler: Handler
    private lateinit var progressBar: ProgressBar
    private lateinit var notificationPermissionRequester: NotificationPermissionRequester
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    // The splash marks the app handoff, not a simulated network task.
    private val openingDurationMs = 320L

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
        window.statusBarColor = android.graphics.Color.rgb(247, 242, 231)
        window.navigationBarColor = android.graphics.Color.rgb(247, 242, 231)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
            android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        progressBar = findViewById(R.id.progressBarSplash)
        progressBar.isIndeterminate = true

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (!isGranted) {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }

        notificationPermissionRequester = NotificationPermissionRequester(this, permissionLauncher)
        notificationPermissionRequester.request()

        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            detectedVpn = getDetectedVPN()

            if (detectedVpn != null) {
                showDialog(
                    this,
                    "VPN DETECTED",
                    "Detected VPN Interface:\n$detectedVpn\n\nPlease disable your VPN and try again."
                )
                return@postDelayed
            }

            val options = ActivityOptionsCompat.makeCustomAnimation(
                this,
                R.anim.fade_in,
                R.anim.fade_out
            )

            startActivity(
                Intent(this, MainActivity::class.java),
                options.toBundle()
            )
            finish()

        }, openingDurationMs)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
