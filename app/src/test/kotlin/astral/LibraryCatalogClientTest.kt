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
        } finally {
            server.shutdown()
        }
    }

    @Test
    fun catalogRejectsAnHtmlResponse() = runBlocking {
        val server = MockWebServer()
        server.start()
        try {
            server.enqueue(MockResponse().setResponseCode(200)
                .addHeader("Content-Type", "text/html")
                .setBody("<!DOCTYPE html><html><body>Login</body></html>"))
            val client = LibraryCatalogClient(server.url("/loader/catalog").toString())
            val result = client.load()
            assertFalse(result.isSuccess)
            assertTrue(result.exceptionOrNull()?.message.orEmpty().contains("web page"))
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
            assertTrue(message, message.contains("web page"))
            assertFalse(message.contains("<!DOCTYPE"))
        } finally {
            server.shutdown()
        }
    }
}
