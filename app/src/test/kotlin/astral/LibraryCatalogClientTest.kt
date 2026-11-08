package astral.loader

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Base64
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class LibraryCatalogClientTest {

    private fun encodedCatalog(): String {
        val catalog = """{"META":{},"DATA":[{"TYPE":"CODMFREE","NAME":"Free"},{"TYPE":"CODMGR","NAME":"Global"}]}"""
        return Base64.getEncoder().encodeToString(catalog.toByteArray(Charsets.UTF_8))
    }

    @Test
    fun redeemsThroughExistingConnectRoute() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "text/plain")
                .setBody(encodedCatalog()))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":false,"reason":"Incorrect Key"}"""))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":true,"data":{"token":"ok"}}"""))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":true,"catalog":{"DATA":[{"TYPE":"CODMGR","NAME":"Owned"}]}}"""))

            val client = LibraryCatalogClient(server.url("/loader/catalog").toString())
            val result = client.redeem("TEST_KEY", "test-device")
            assertTrue(result.exceptionOrNull()?.message.orEmpty(), result.isSuccess)
            val redeemed = result.getOrThrow()
            assertEquals("CODMGR", redeemed.getString("game"))
            assertEquals(2, redeemed.getJSONObject("catalog").getJSONArray("DATA").length())

            val catalogRequest = server.takeRequest(2, TimeUnit.SECONDS)
            val firstValidation = server.takeRequest(2, TimeUnit.SECONDS)
            val secondValidation = server.takeRequest(2, TimeUnit.SECONDS)
            assertEquals("/loader/catalog", catalogRequest?.path)
            assertEquals("/connect", firstValidation?.path)
            assertEquals("POST", firstValidation?.method)
            assertTrue(firstValidation?.body?.readUtf8()?.contains("game=CODMFREE") == true)
            assertEquals("/connect", secondValidation?.path)
            assertTrue(secondValidation?.body?.readUtf8()?.contains("game=CODMGR") == true)
            val assignedRequest = server.takeRequest(2, TimeUnit.SECONDS)
            assertEquals("/loader/catalog/assigned", assignedRequest?.path)
            assertTrue(assignedRequest?.body?.readUtf8()?.contains("game=CODMGR") == true)
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun ownerLibraryLoadsWhenPublicCatalogIsEmpty() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setResponseCode(200)
                .setBody(Base64.getEncoder().encodeToString(
                    """{"DATA":[]}""".toByteArray(Charsets.UTF_8))))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":true}"""))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":true,"catalog":{"DATA":[{"TYPE":"CODMGR","NAME":"Private"}]}}"""))
            val result = LibraryCatalogClient(server.url("/loader/catalog").toString())
                .redeem("TEST_KEY", "test-device")
            assertTrue(result.exceptionOrNull()?.message.orEmpty(), result.isSuccess)
            assertEquals("CODMGR", result.getOrThrow().getString("game"))
            assertEquals(1, result.getOrThrow().getJSONObject("catalog").getJSONArray("DATA").length())
            assertEquals("/loader/catalog", server.takeRequest(2, TimeUnit.SECONDS)?.path)
            assertEquals("/connect", server.takeRequest(2, TimeUnit.SECONDS)?.path)
            assertEquals("/loader/catalog/assigned", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun rejectsUnauthorizedOwnedLibraries() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setBody(Base64.getEncoder().encodeToString(
                """{"DATA":[]}""".toByteArray(Charsets.UTF_8))))
            server.enqueue(MockResponse().setBody("""{"status":true}"""))
            server.enqueue(MockResponse().setResponseCode(403)
                .addHeader("Content-Type", "application/json")
                .setBody("""{"status":false,"reason":"Device has not been authorized"}"""))
            val result = LibraryCatalogClient(server.url("/loader/catalog").toString())
                .redeem("TEST_KEY", "unauthorized-device")
            assertFalse(result.isSuccess)
            assertTrue(result.exceptionOrNull()?.message.orEmpty().contains("Device has not been authorized"))
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun fallsBackToTheExistingLegacyCatalogRoute() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setResponseCode(404)
                .addHeader("Content-Type", "text/html")
                .setBody("<!DOCTYPE html><html><body>Not found</body></html>"))
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "text/plain")
                .setBody(encodedCatalog()))
            val result = LibraryCatalogClient(server.url("/loader/catalog").toString()).load()
            assertTrue(result.exceptionOrNull()?.message.orEmpty(), result.isSuccess)
            assertEquals(2, result.getOrThrow().getJSONArray("DATA").length())
            assertEquals("/loader/catalog", server.takeRequest(2, TimeUnit.SECONDS)?.path)
            assertEquals("/Loader/index.php", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun catalogRejectsHtmlOnBothRoutesWithAnActionableError() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            repeat(2) {
                server.enqueue(MockResponse().setResponseCode(200)
                    .addHeader("Content-Type", "text/html")
                    .setBody("<!DOCTYPE html><html><body>Login</body></html>"))
            }
            val client = LibraryCatalogClient(server.url("/loader/catalog").toString())
            val result = client.load()
            assertFalse(result.isSuccess)
            val message = result.exceptionOrNull()?.message.orEmpty()
            assertTrue(message, message.contains("Primary: HTML response"))
            assertTrue(message, message.contains("Legacy: HTML response"))
            assertFalse(message.contains("<!DOCTYPE"))
            assertEquals("/loader/catalog", server.takeRequest(2, TimeUnit.SECONDS)?.path)
            assertEquals("/Loader/index.php", server.takeRequest(2, TimeUnit.SECONDS)?.path)
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun validationRejectsAnHtmlErrorWithoutShowingHtml() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "text/plain")
                .setBody(encodedCatalog()))
            server.enqueue(MockResponse().setResponseCode(404)
                .addHeader("Content-Type", "text/html")
                .setBody("<!DOCTYPE html><html><body>Not found</body></html>"))
            val client = LibraryCatalogClient(server.url("/loader/catalog").toString())
            val result = client.redeem("TEST_KEY", "test-device")
            assertFalse(result.isSuccess)
            val message = result.exceptionOrNull()?.message.orEmpty()
            assertTrue(message, message.contains("HTML page"))
            assertFalse(message.contains("<!DOCTYPE"))
        } finally {
            server.shutdown()
        }
    }
}
