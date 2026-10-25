# SHAI LOADER API endpoint setup

The app now uses:
- GET https://shaii.x10.mx/loader/catalog
- POST https://shaii.x10.mx/connect

The backend must first be deployed with the `loader_catalog` migration. Configure your release signing securely via user-local gradle.properties:

astral.signing.file=/your/private/release-keystore.jks
astral.signing.storePassword=YOUR_SECRET
astral.signing.keyAlias=YOUR_ALIAS
astral.signing.keyPassword=YOUR_SECRET

Do not commit credentials. Run the app with your standard Gradle toolchain and verify API requests on your own test instance.
