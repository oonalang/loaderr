# SHAI Loader

Android app built with Kotlin and the Android SDK.

## Build

JDK 17 and Android SDK 35 are required.

Run `./gradlew assembleDebug`. Find the APK in `app/build/outputs/apk/debug/`.

## License and catalog

The loader reads `GET https://freeshai.x10.mx/loader/catalog` and attempts `/Loader/index.php` if the primary route fails. License validation uses `POST https://freeshai.x10.mx/connect`.

The panel must have the `loader_catalog` table and an active library entry. A valid license key is required to proceed. Keep database and signing credentials outside the repository.

## Audio

Screen sounds use Android media volume. The initial screen uses pencil and paper cues. Verified screens use sounds matching the selected theme.
