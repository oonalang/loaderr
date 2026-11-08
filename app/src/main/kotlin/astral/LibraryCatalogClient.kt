package astral.loader

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLException
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.net.URLEncoder

class LibraryCatalogClient(private val endpoint: String) {

    suspend fun load(): Result<JSONObject> = withContext(Dispatchers.IO) {
        runCatching {
            val primary = URL(endpoint)
            try {
                fetchCatalog(primary)
            } catch (first: Exception) {
                val alternate = URI(endpoint).resolve("/Loader/index.php").toURL()
                if (primary.toExternalForm() == alternate.toExternalForm()) throw first
                try {
                    fetchCatalog(alternate)
                } catch (second: Exception) {
                    throw IOException(
                        "Catalog unavailable. Primary: ${failureReason(first)}. " +
                            "Legacy: ${failureReason(second)}."
                    )
                }
            }
        }
    }

    private fun fetchCatalog(url: URL): JSONObject {
        val connection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            requestMethod = "GET"
            useCaches = false
            setRequestProperty("Accept", "text/plain, application/json")
        }
        return try {
            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val raw = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?: throw IOException("HTTP $status: empty response")
            if (status !in 200..299) throw IOException("HTTP $status")
            val body = raw.trim().removePrefix("\uFEFF").trim()
            if (body.isBlank()) throw IOException("empty response")
            if (body.startsWith("<") || connection.contentType?.contains("text/html", true) == true) {
                throw IOException("HTML response (HTTP $status)")
            }
            val decoded = try {
                String(Base64.decode(body, Base64.DEFAULT), Charsets.UTF_8).trim()
            } catch (_: IllegalArgumentException) {
                throw IOException("invalid catalog encoding")
            }
            if (!decoded.startsWith("{")) throw IOException("invalid catalog encoding")
            val catalog = try {
                JSONObject(decoded)
            } catch (_: Exception) {
                throw IOException("invalid catalog JSON")
            }
            if (catalog.optJSONArray("DATA") == null) {
                throw IOException("missing library list")
            }
            catalog
        } finally {
            connection.disconnect()
        }
    }

    private fun failureReason(error: Exception): String = when (error) {
        is UnknownHostException -> "DNS lookup failed"
        is SocketTimeoutException -> "request timed out"
        is ConnectException -> "connection refused"
        is SSLException -> "TLS error"
        is IOException -> when {
            error.message.orEmpty().startsWith("HTTP ") -> error.message.orEmpty()
            error.message.orEmpty() in setOf(
                "empty response", "invalid catalog encoding",
                "invalid catalog JSON", "missing library list"
            ) -> error.message.orEmpty()
            error.message.orEmpty().startsWith("HTML response") -> error.message.orEmpty()
            else -> "network error"
        }
        else -> "request failed"
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
