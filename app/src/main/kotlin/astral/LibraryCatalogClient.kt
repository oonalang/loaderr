package astral.loader

import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
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
    private val supportedGames = listOf("CODMGR", "CODMFREE", "CODM", "PBS", "MLBB", "FL84", "8BP")

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
            val publiclyListed = load().getOrNull()
            val knownGames = publiclyListed?.optJSONArray("DATA")?.let { entries ->
                (0 until entries.length()).mapNotNull { entries.optJSONObject(it)?.optString("TYPE") }
            }.orEmpty()
            val games = (knownGames + supportedGames)
                .map { it.trim() }
                .filter { it.matches(Regex("[A-Za-z0-9_-]{1,32}")) }
                .distinct()
            val connectUrl = URI(endpoint).resolve("/connect").toURL()

            for (game in games) {
                val response = postJson(connectUrl, listOf(
                    "game" to game, "user_key" to licenseKey, "serial" to serial
                ))
                if (response.optBoolean("status", false)) {
                    val assigned = readAssignedCatalog(licenseKey, serial, game)
                    val owned = assigned.optJSONArray("DATA") ?: JSONArray()
                    val public = publiclyListed?.optJSONArray("DATA") ?: JSONArray()
                    val visible = JSONArray()
                    for (i in 0 until owned.length()) {
                        val row = owned.optJSONObject(i) ?: continue
                        if (row.optString("TYPE") == game) visible.put(row)
                    }
                    for (i in 0 until public.length()) {
                        val row = public.optJSONObject(i) ?: continue
                        if (row.optString("TYPE") != game) continue
                        val duplicate = (0 until visible.length()).any { j ->
                            val existing = visible.optJSONObject(j)
                            existing != null && existing.optString("NAME") == row.optString("NAME") &&
                                existing.optString("DOWNLOAD") == row.optString("DOWNLOAD")
                        }
                        if (!duplicate) visible.put(row)
                    }
                    val catalog = JSONObject()
                        .put("META", publiclyListed?.optJSONObject("META") ?: JSONObject())
                        .put("DATA", visible)
                    return@runCatching JSONObject()
                        .put("status", true)
                        .put("game", game)
                        .put("catalog", catalog)
                }
                val reason = response.optString("reason")
                if (listOf("database", "maintenance", "unavailable", "configuration mismatch").any {
                    reason.contains(it, ignoreCase = true)
                }) {
                    throw IOException(reason.ifBlank { "License service unavailable" })
                }
            }
            throw IOException("This key was not accepted for any supported license type")
        }
    }

    private fun readAssignedCatalog(key: String, serial: String, game: String): JSONObject {
        val url = URI(endpoint).resolve("/loader/catalog/assigned").toURL()
        val response = try {
            postJson(url, listOf("user_key" to key, "serial" to serial, "game" to game))
        } catch (e: IOException) {
            if (e.message.orEmpty().contains("HTTP 404")) {
                throw IOException("The panel is missing its assigned-library endpoint", e)
            }
            throw e
        }
        if (!response.optBoolean("status", false)) {
            throw IOException(response.optString("reason", "Assigned library catalog unavailable"))
        }
        return response.optJSONObject("catalog")
            ?: throw IOException("Invalid assigned-library catalog response")
    }

    private fun postJson(url: URL, values: List<Pair<String, String>>): JSONObject {
        val form = values.joinToString("&") { (name, value) ->
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
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val body = stream?.bufferedReader(Charsets.UTF_8)?.use { it.readText() }
                ?.trim()?.removePrefix("\uFEFF")?.trim()
                ?: throw IOException("No validation response (HTTP $code)")
            if (body.startsWith("<") || connection.contentType?.contains("text/html", true) == true) {
                throw IOException("The panel returned an HTML page (HTTP $code)")
            }
            if (!body.startsWith("{")) throw IOException("Invalid panel response (HTTP $code)")
            val result = try { JSONObject(body) }
                catch (_: Exception) { throw IOException("Invalid panel JSON (HTTP $code)") }
            if (code !in 200..299) {
                throw IOException(result.optString("reason").ifBlank { "Panel unavailable (HTTP $code)" })
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
