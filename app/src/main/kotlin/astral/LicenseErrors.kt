package astral.loader

internal object LicenseErrors {
    fun presentation(message: String): String = when {
        message.contains("syntax", true) ||
            message.contains("key format", true) ||
            message.contains("invalid characters", true) -> "Invalid key syntax."
        message.equals("Invalid license key", true) ||
            message.equals("Key validation failed", true) ||
            message.contains("not accepted", true) -> "This key was not accepted."
        message.startsWith("Catalog unavailable. Primary: ") -> message
        message.contains("web page", true) ||
            message.contains("html", true) -> "The panel sent an HTML response instead of JSON. Check the server routes."
        message.contains("catalog", true) -> "The library catalog is unavailable. Check the server routes or database."
        else -> message
    }
}
