package astral.loader

internal object LicenseErrors {
    fun presentation(message: String): String = when {
        message.contains("syntax", true) ||
            message.contains("key format", true) ||
            message.contains("invalid characters", true) -> "Invalid key syntax."
        message.equals("Invalid license key", true) ||
            message.equals("Key validation failed", true) ||
            message.contains("not accepted", true) -> "This key was not accepted."
        message.contains("web page", true) ||
            message.contains("html", true) -> "The panel sent a web page instead of a validation response. Check the server configuration."
        message.contains("catalog", true) -> "Unable to load the library catalog. Check the server connection."
        else -> message
    }
}
