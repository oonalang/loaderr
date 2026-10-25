package astral.loader

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class LibraryCatalogClient(private val endpoint: String) {

    suspend fun load(): Result<JSONObject> = withContext(Dispatchers.IO) {
        runCatching {
            val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
                connectTimeout = TIMEOUT_MS
                readTimeout = TIMEOUT_MS
                requestMethod = "GET"
                useCaches = false
            }

            try {
                val encoded = connection.inputStream
                    .bufferedReader(Charsets.UTF_8)
                    .use { it.readText() }
                    .trim()

                if (encoded.isBlank()) {
                    throw IOException("Empty server response")
                }

                val decoded = Base64.decode(encoded, Base64.DEFAULT)
                if (decoded.isEmpty()) {
                    throw IOException("Empty decoded server response")
                }

                val json = String(decoded, Charsets.UTF_8).trim()
                if (json.isBlank()) {
                    throw IOException("Empty server JSON")
                }

                JSONObject(json)
            } finally {
                connection.disconnect()
            }
        }
    }


    /** Resolve the existing panel key to its one CODM game before listing libraries. */
    suspend fun redeem(licenseKey: String, serial: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        runCatching {
            val postUrl = endpoint.trimEnd('/') + "/redeem"
            val form = listOf("user_key" to licenseKey, "serial" to serial)
                .joinToString("&") { (key, value) ->
                    "${URLEncoder.encode(key, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
                }
            val connection = (URL(postUrl).openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                doInput = true
                useCaches = false
                connectTimeout = TIMEOUT_MS
                readTimeout = TIMEOUT_MS
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            }
            try {
                connection.outputStream.use { it.write(form.toByteArray(Charsets.UTF_8)) }
                val responseCode = connection.responseCode
                val stream = if (responseCode in 200..299) connection.inputStream else connection.errorStream
                val responseText = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                    ?: throw IOException("No server response (HTTP $responseCode)")
                val json = JSONObject(responseText.trim().removePrefix("\uFEFF"))
                if (!json.optBoolean("status")) {
                    throw IOException(json.optString("reason", "Key validation failed"))
                }
                val game = json.optString("game")
                if (game !in setOf("CODMFREE", "CODMGR", "CODM")) {
                    throw IOException("Unsupported game type")
                }
                json
            } finally {
                connection.disconnect()
            }
        }
    }

    private companion object {
        const val TIMEOUT_MS = 15_000
    }
}
