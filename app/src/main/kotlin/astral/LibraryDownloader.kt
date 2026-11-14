package astral.loader

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.Locale
import java.util.concurrent.TimeUnit

class LibraryDownloader(private val context: Context) {

    private val client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val handler = Handler(Looper.getMainLooper())
    private val maxRetries = MAX_RETRIES
    private val retryDelayMs = RETRY_DELAY_MS
    var failureReason: String? = null
        private set

    companion object {
        const val NOTIFICATION_ID = 1696900
        private const val CHANNEL_ID = "download_channel"
        private const val MAX_RETRIES = 3
        private const val RETRY_DELAY_MS = 1000L

        fun cancelNotification(context: Context) {
            try {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                Handler(Looper.getMainLooper()).post {
                    notificationManager.cancel(NOTIFICATION_ID)
                }
            } catch (_: Exception) {
            }
        }

        fun createNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val existingChannel = notificationManager.getNotificationChannel(CHANNEL_ID)
                    if (existingChannel == null) {
                        val channel = NotificationChannel(
                            CHANNEL_ID,
                            "Download Notifications",
                            NotificationManager.IMPORTANCE_LOW
                        )
                        channel.description = "Shows download progress"
                        notificationManager.createNotificationChannel(channel)
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    init {
        createNotificationChannel(context)
    }

    fun download(url: String, destinationName: String, onProgress: (Int) -> Unit = {}): Boolean {
        var retryCount = 0
        failureReason = null
        val destinationFile = File(context.cacheDir, destinationName)
        val temporaryFile = File(context.cacheDir, "$destinationName.part")

        val notification = buildProgressNotification(0, 0, 0)
        notificationManager.notify(NOTIFICATION_ID, notification)

        while (retryCount < maxRetries) {
            try {
                temporaryFile.delete()
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->

                    if (response.isSuccessful) {
                        val totalBytes = response.body?.contentLength() ?: 0

                        if (response.body == null) {
                            failureReason = "Library server returned an empty response"
                            handler.post { notificationManager.cancel(NOTIFICATION_ID) }
                            return false
                        }

                        var downloadedBytes: Long = 0
                        response.body?.byteStream()?.use { inputStream ->
                            temporaryFile.outputStream().use { outputStream ->
                                val buffer = ByteArray(8192)
                                var bytesRead: Int
                                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                                    outputStream.write(buffer, 0, bytesRead)
                                    downloadedBytes += bytesRead.toLong()

                                    val progress = if (totalBytes > 0) (downloadedBytes * 100 / totalBytes).toInt() else 0

                                    handler.post {
                                        val updatedNotification = buildProgressNotification(
                                            progress,
                                            downloadedBytes,
                                            totalBytes
                                        )
                                        notificationManager.notify(NOTIFICATION_ID, updatedNotification)
                                        onProgress(progress)
                                    }
                                }
                            }
                        }

                        val fileSize = temporaryFile.length()
                        if (totalBytes > 0 && fileSize != totalBytes) {
                            failureReason = "Incomplete library download"
                            temporaryFile.delete()
                            return false
                        }
                        val signature = ByteArray(4)
                        val readCount = if (fileSize >= 4) temporaryFile.inputStream().use { it.read(signature) } else 0
                        if (readCount != 4 || signature[0] != 0x7f.toByte() ||
                            signature[1] != 'E'.code.toByte() || signature[2] != 'L'.code.toByte() ||
                            signature[3] != 'F'.code.toByte()) {
                            failureReason = "Library server did not return a valid .so file"
                            temporaryFile.delete()
                            return false
                        }
                        Files.move(temporaryFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING)

                        handler.post {
                            val finalNotification = buildProgressNotification(100, totalBytes, totalBytes)
                            notificationManager.notify(NOTIFICATION_ID, finalNotification)
                            handler.postDelayed({
                                notificationManager.cancel(NOTIFICATION_ID)
                            }, 1500)
                        }
                        return true
                    } else {
                        failureReason = "Library server returned HTTP ${response.code}"
                        handler.post { notificationManager.cancel(NOTIFICATION_ID) }
                        return false
                    }
                }
            } catch (error: IOException) {
                failureReason = error.message?.take(120) ?: "Network error downloading library"
                retryCount++
                if (retryCount >= maxRetries) {
                    handler.post { notificationManager.cancel(NOTIFICATION_ID) }
                    return false
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(retryDelayMs)
                } catch (_: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            } catch (error: Exception) {
                failureReason = error.message?.take(120) ?: "Unable to save downloaded library"
                handler.post { notificationManager.cancel(NOTIFICATION_ID) }
                return false
            }
        }
        handler.post { notificationManager.cancel(NOTIFICATION_ID) }
        return false
    }

    private fun buildProgressNotification(progress: Int, downloadedBytes: Long, totalBytes: Long): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Downloading updates...")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOngoing(true)
            .setAutoCancel(false)
            .setOnlyAlertOnce(true)
            .setSilent(true)

        fun formatSize(bytes: Long): String {
            val kb = bytes / 1024.0
            val mb = kb / 1024.0
            val gb = mb / 1024.0
            return when {
                gb >= 1 -> String.format(Locale.US, "%.2f GB", gb)
                mb >= 1 -> String.format(Locale.US, "%.2f MB", mb)
                kb >= 1 -> String.format(Locale.US, "%.2f KB", kb)
                else -> "$bytes B"
            }
        }

        val downloaded = formatSize(downloadedBytes)
        val total = formatSize(totalBytes)
        if (totalBytes > 0) {
            builder.setContentText("$progress% ($downloaded / $total)")
                .setProgress(100, progress, false)
        } else {
            builder.setContentText("Downloaded $downloaded")
                .setProgress(0, 0, true)
        }
        return builder.build()
    }

}
