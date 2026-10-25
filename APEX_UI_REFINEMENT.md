# SHAI Loader, APEX UI maintenance

This Android source archive is based on `SHAI_Loader_CODM_Key_Filtering_Android_Source.zip`.

Changes:
- Kept existing paper-to-violet phase artwork and overall layouts.
- Removed unfinished marketing copy from the main screen and clarified the license flow.
- Aligned existing XML color resources with the panel's black/purple identity.
- Used the existing dark alert dialog theme for the update-required dialog.

Intentionally unchanged: native library handling, injection commands, license request schema and `/connect` endpoint.

Verification:
- PASS: all 43 Android resource XML documents parsed.
- BLOCKED: `./gradlew --offline assembleDebug`, Gradle wrapper distribution not cached and network unavailable.
- NOT TESTED: Android device behavior, screenshots and APK installation.

Build with a configured Android SDK and cached or network-accessible Gradle distribution. No APK is included.
