# SHAI LOADER | paper sketch to ethereal

## Android IDE setup

1. Extract the ZIP. Open the folder containing `settings.gradle.kts` in Android IDE.
2. Use Java 17 and Android SDK 35. Allow Android IDE to sync Gradle dependencies.
3. Use the `app` debug variant, then run `assembleDebug` (or Android IDE's Build APK command).
4. Connect to the same x10hosting panel already configured under `https://shaii.x10.mx`.
5. Test a valid license against an active library. A rejected key stays in the paper theme.

Project uses Android Gradle Plugin 8.9.1, Kotlin 2.0.21, Gradle 8.11.1, minSdk 28, targetSdk 35. The NDK is not needed to compile this Android project: pre-existing native assets are distributed as precompiled files and remain untouched.

## What changed

- The user-visible app name and Gradle project name are **SHAI LOADER**.
- New `LoaderVisuals.kt` draws an off-white, lightly textured *handmade paper* background, uneven ink card borders and casual headline lettering before validation.
- Following a successful license server response, an 850ms interpolation transitions paper into the **black-and-violet ethereal** presentation. The signed-in state adds softly lit surfaces, a purple horizon ring, subtle stars, a success panel and updated headings.
- Respect Android's system animator scale setting; reduced/disabled animation performs a direct transition.
- A saved key does not automatically authenticate or switch the visual mode. Switching libraries resets the authenticated visual state and requires validation for the newly selected library.
- Removed generic and nonfunctional support tiles from the main screen. Library selection, About, and the existing Telegram channel remain accessible through real controls.
- Updated custom SHAI symbol used as the app icon and fallback library logo.
- Restyled the splash screen and About panel to fit the two-phase visual language.
- Kept library catalog and license endpoints, request fields, validation flow, downloader and existing native workflow unchanged.
- Updated Gradle wrapper to 8.11.1 to match AGP 8.9.1 and enabled debugging in the debug build variant.

## Verification limits

The source has been checked for XML well-formedness, resource IDs used by activities, theme-transition hooks and API contract strings. This does not replace compiling the APK, running on a device, or testing server-side license validation. Build, runtime appearance and responsiveness remain **unverified** until Android IDE successfully compiles and launches the project.

## Intentional compatibility

The Android `applicationId` and Kotlin namespace remain `astral.loader` to preserve existing update/install identity and avoid breaking code references. Branding displayed to users has been renamed to SHAI LOADER. The existing panel integration is not modified in this UI pass.

## Brand and store synchronization

- About profile and About footer show `@Shaiaintshyy`, matching the main footer.
- About channel and its Telegram button open `https://t.me/Shaimainch`.
- The existing `VISIT MY STORE` entry in the library drawer opens
  `https://ethnir-storefront.vercel.app/#home` using the device browser.
- Existing paper-to-ethereal UI states, user-edited copy, licensing endpoints,
  assets, and application package identity are preserved.
