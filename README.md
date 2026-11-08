# SHAI Loader

Android Kotlin project for the SHAI library interface.

## Build

Use JDK 17 and Android SDK 35. Run `./gradlew assembleDebug` from the project root. The debug APK is written to `app/build/outputs/apk/debug/`.

## Interface

The key-entry screen uses paper-style audio feedback. After verification, touch feedback follows the selected theme. Audio plays through media volume.

Library access requires a valid key and the configured backend. The repository includes the Gradle wrapper, runtime assets, and tests.
