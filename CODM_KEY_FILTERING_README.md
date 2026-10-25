# SHAI Loader Android Source: CODM Key Filtering

This is a full Android Gradle source project, not a built APK.

- Supports CODM FREE (`CODMFREE`), CODM GARENA (`CODMGR`), CODM GLOBAL (`CODM`) keys created by the SHAI Panel Generate Keys page.
- Enter the key first. The app asks `POST https://shaii.x10.mx/loader/catalog/redeem` to obtain only the key's matching libraries.
- The existing `POST /connect` performs the final license verification and device registration.
- The selection UI is then populated with only matching game libraries.
- Switching between libraries of the same game revalidates through `/connect` before fetching the file.
- The old public catalog is only read for version metadata by this client. Old Android app releases still show the original public catalog until users install the updated build.
- Panel full ZIP needs to be deployed before the new client uses `/loader/catalog/redeem`.

To build: import this project into Android Studio with the Android SDK (compileSdk 35, JDK 17), sync Gradle and build a debug or signed release APK. Use signing credentials controlled by the app owner. Installation/build validation was not possible in the current environment because the Android SDK/Gradle toolchain is unavailable.
