# SHAI Loader · Concept / Ethereal refinement

## Direction and acceptance

- Subject: Android library-access loader.
- Audience: phone users redeeming keys and choosing libraries.
- Job: redeem, **choose a library explicitly**, then prepare only on explicit action.
- Mode: upgrade, retaining existing Kotlin / Android XML / Gradle stack.
- Direction: hand-drawn conceptual sketch before authentication, restrained ethereal violet after authentication.
- Variance 6/10, motion 4/10, density 4/10.

### Design system

| Token | Value |
|---|---|
| paper | `#FAF7EF` |
| paper ink | `#1E1920` |
| ethereal background | `#0B0B10` |
| ethereal surface | `#15131D` |
| primary text | `#F3F0FA` |
| muted text | `#B6AEC6` |
| violet | `#925CF4` |

- Type: Android casual face on sketch headings, Android sans-serif on signed-in headings; sans-serif for UI data.
- Layout, left-aligned content: `brand header → illustration + headline → license or success status → library card → action → secondary list → footer`.
- Signature: one symbol changing from a rough hand-drawn figure to a violet sigil. No unrelated decorative tiles.
- Rules: one main action; no auto-selection; readable library rows with state descriptions.

## Changed files

- `app/src/main/res/layout/activity_main.xml`: reorganized mobile visual hierarchy while keeping IDs and core controls.
- `app/src/main/res/layout/item_library_drawer.xml`: increased library row type and tap areas.
- `app/src/main/res/drawable/hero_sketch.xml`, `hero_ethereal.xml`: original vector art for the two states.
- `app/src/main/kotlin/astral/MainActivity.kt`: explicit library choice, responsive visual states, restrained motion, inline license feedback, key-switch recovery, drawer focus and empty states.
- `app/src/main/kotlin/astral/LoaderVisuals.kt`: selected-row outline respecting current theme.
- `app/src/main/kotlin/astral/SplashActivity.kt` and `app/src/main/res/layout/activity_splash.xml`: removed fake percent progress and reduced splash delay.
- `app/src/main/AndroidManifest.xml`: keyboard adjusts content height.
- `app/build.gradle.kts`: version 1.2 (code 3).

## Tested

- Python static UI contract assertions: `python3 qa/verify_ui_contract.py`.
- All Android XML resources parsed with XML parser.
- Gradle Android debug APK build **BLOCKED**: `gradle-8.11.1-bin.zip` could not be fetched, `UnknownHostException: services.gradle.org`.
- Kotlin standalone direct probe finds unresolved Android imports because the Android SDK and Gradle dependencies are unavailable. It is **not** proof of compilation.
- Android device visual/accessibility, login, network and runtime integration checks **NOT RUN**.

## Manual acceptance checklist (run after building APK)

1. Fresh launch starts as paper/sketch. No staged library is downloaded.
2. Redeem a valid supported key; ethereal state appears, and the library drawer opens.
3. Closing drawer without tapping a library leaves the selection empty; no download starts.
4. Tapping a library sets its name and displays a preparation action; still no download starts.
5. Test library search, empty results, very long names and accessibility focus.
6. Test rotate/relaunch, keyboard, animation-disabled accessibility setting, and network timeout.
7. Test incorrect key and zero matching libraries; use the "Use another key" recovery link.
8. Confirm the existing licensed app integration independently with its owner.

Build on an Android machine with the project’s Gradle version and a configured Android SDK:

```sh
./gradlew :app:assembleDebug
```

The package contains **source code**, not an installed APK. It does not contain account credentials.
