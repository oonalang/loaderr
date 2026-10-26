package astral.loader
internal object LicenseErrors {
    fun presentation(message:String):String = when {
        message.contains("syntax",true) || message.contains("key format",true) || message.contains("invalid characters",true) -> "Invalid key syntax."
        message.equals("Invalid license key",true) || message.equals("Key validation failed",true) -> "This key was not accepted."
        else -> message
    }
}
