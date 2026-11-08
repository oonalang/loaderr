package astral.loader

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat


class NotificationPermissionRequester(
    private val activity: Activity,
    private val permissionLauncher: ActivityResultLauncher<String>
) {
    fun request(onSettled: () -> Unit) {
        val needsPrompt = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        if (!needsPrompt) {
            onSettled()
            return
        }
        pendingResult = onSettled
        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }


    fun onPermissionResult() {
        val callback = pendingResult
        pendingResult = null
        callback?.invoke()
    }

    private var pendingResult: (() -> Unit)? = null
}
