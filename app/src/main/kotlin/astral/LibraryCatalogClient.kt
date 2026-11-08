package astral.loader

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
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
                setRequestProperty("Accept", "text/plain, application/json")
            }
            try {
                val status = connection.responseCode
                val stream = if (status in 200..299) connection.inputStream else connection.errorStream
                val raw = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                    ?: throw IOException("Catalog unavailable (HTTP $status)")
                if (status !in 200..299) throw IOException("Catalog unavailable (HTTP $status)")
                val body = raw.trim().removePrefix("\uFEFF").trim()
                if (body.isBlank()) throw IOException("Catalog returned an empty response")
                if (body.startsWith("<") || connection.contentType?.contains("text/html", true) == true) {
                    throw IOException("Catalog unavailable. The server returned a web page instead of catalog data.")
                }

                val decoded = try {
                    String(Base64.decode(body, Base64.DEFAULT), Charsets.UTF_8).trim()
                } catch (_: IllegalArgumentException) {
                    throw IOException("The catalog response is not valid Base64")
                }
                if (!decoded.startsWith("{")) throw IOException("The decoded catalog is not valid JSON")
                val catalog = try {
                    JSONObject(decoded)
                } catch (_: Exception) {
                    throw IOException("The decoded catalog is not valid JSON")
                }
                if (catalog.optJSONArray("DATA") == null) {
                    throw IOException("The catalog response does not contain a library list")
                }
                catalog
            } finally {
                connection.disconnect()
            }
        }
    }

    suspend fun redeem(licenseKey: String, serial: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        runCatching {
            val catalog = load().getOrThrow()
            val items = catalog.getJSONArray("DATA")
            val games = (0 until items.length())
                .mapNotNull { items.optJSONObject(it)?.optString("TYPE")?.trim() }
                .filter { it.isNotBlank() }
                .distinct()
                .take(12)

            if (games.isEmpty()) throw IOException("No libraries are currently available")
            val uri = URI(endpoint)
            val connect = uri.resolve("/connect").toURL()

            for (game in games) {
                val response = validateGame(connect, licenseKey, serial, game)
                if (response.optBoolean("status", false)) {
                    return@runCatching JSONObject()
                        .put("status", true)
                        .put("game", game)
                        .put("catalog", catalog)
                }
                val reason = response.optString("reason", "")
                if (reason.contains("database", true) ||
                    reason.contains("maintenance", true) ||
                    reason.contains("unavailable", true)) {
                    throw IOException(reason.ifBlank { "The validation service is unavailable" })
                }
            }
            throw IOException("This key was not accepted for the available libraries")
        }
    }

    private fun validateGame(url: URL, key: String, serial: String, game: String): JSONObject {
        val form = listOf("game" to game, "user_key" to key, "serial" to serial)
            .joinToString("&") { (name, value) ->
                "${URLEncoder.encode(name, "UTF-8")}=${URLEncoder.encode(value, "UTF-8")}"
            }
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true
            doInput = true
            useCaches = false
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            setRequestProperty("Accept", "application/json")
            setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        }
        return try {
            connection.outputStream.use { it.write(form.toByteArray(Charsets.UTF_8)) }
            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val body = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?.trim()?.removePrefix("\uFEFF")?.trim()
                ?: throw IOException("No validation response (HTTP $status)")
            if (body.startsWith("<") || connection.contentType?.contains("text/html", true) == true) {
                throw IOException("The panel returned a web page instead of a validation response (HTTP $status)")
            }
            if (!body.startsWith("{")) {
                throw IOException("Invalid panel response (HTTP $status)")
            }
            val result = try {
                JSONObject(body)
            } catch (_: Exception) {
                throw IOException("Invalid panel response (HTTP $status)")
            }
            if (status !in 200..299) {
                throw IOException(result.optString("reason").ifBlank { "Panel unavailable (HTTP $status)" })
            }
            result
        } finally {
            connection.disconnect()
        }
    }

    private companion object {
        const val TIMEOUT_MS = 15_000
    }
}
