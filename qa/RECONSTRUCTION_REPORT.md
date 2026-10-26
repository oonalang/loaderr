# SHAI reconstruction — 2026-10-26

Implemented in native Android Views; no framework or backend changes.

## Changes and root causes

- `LocalGreeting.kt`: device-local greeting with the six requested time ranges, one callback at the next boundary, time/timezone/date broadcasts, and start/stop lifecycle handling. A separate `hero_greeting` leaves the promotional headline and Buy now link independent.
- `MainActivity.kt`, `activity_main.xml`: removed authenticated success headline/caption and redundant selected-library sentence. License surface contains only the check, label, masked key and Change key. Operational messages now sit beside the primary action. Existing redemption, catalog and explicit preparation flow remain.
- `LoaderVisuals.kt` and layouts: reduced accumulated gray shadows and wide bevels, tighter surface geometry, smaller illustration area, compact option groups, larger artwork, flexible text heights and a radio-group mode selector. Active segments have a periwinkle fill and checkmark. Children disable with the controls.
- `ShaiSounds.kt`: the old code loaded each sample on first use then discarded that event, making one-time success/error cues silent. It now preloads all four samples, retains a pending event for at most 500ms, checks system volume/ringer state again at playback, and releases on stop. The old preference and visible setting are removed. SoundPool call handling is tested; audible playback is NOT verified.
- `RoundedArtworkView.kt`: clips actual image drawing, including asynchronous replacements and transparency. Empty logo selections clear the old artwork. Drawer counts use Android plurals.
- Insets include main content, keyboard, drawer bars and side cutouts. Large-font options stack vertically.
- `RotatingHeadlineView.kt`: fixes promotional rotation failing to resume after returning to key entry.

Inspected: main activity, layout, drawer layout, palette/material drawable, type and motion helpers, promotional headline, sounds, illustration, spinner adapter/layouts, existing render/behavior tests, project context and release workflow. Modified files are recorded in this commit's diff.

## Validation

| Check | Result |
| --- | --- |
| Debug APK and minified release APK | PASSED, Gradle build successful |
| JVM tests | PASSED: 24 tests, 0 failures/errors |
| Greeting schedule including 10:22/evening, next boundary, resume and timezone broadcast | PASSED in automated tests |
| Exclusive/persisted mode state on repeated taps | PASSED |
| Image corners after replacement | PASSED, native graphics pixel assertions |
| First sound event queues until loaded; silent/stopped states suppress playback | PASSED with SoundPool shadow; not audible verification |
| Source UI contract | PASSED |
| Protected loader components | PASSED, SHA-256 hashes unchanged |
| Real-layout rendering | PASSED in Robolectric native graphics, not an emulator/device |
| Actual audio output, hardware startup, full authenticated backend flow | UNVERIFIED locally |
| Enlarged-font visual review, physical keyboard/system bar behavior | UNVERIFIED on device |

The renders in `qa/reconstruction` are Android layouts rendered by Robolectric. The signed-in fixture manually sets authentication and does not set the primary action label, so its “Redeem key” button is a fixture limitation; production redemption sets “Choose library”. No claim of live authentication is made.

## Review and remaining checks

Independent engineering review identified a RadioGroup type mismatch, disabled child controls, promo rotation restart and side inset issues; all were corrected before the passing build. The actual rendered screen was inspected and compared with the prior capture. Independent art/anti-slop reviews were started but interrupted by the request to publish; they are not claimed complete. The requested Pro Max, Hallmark, Ponytail and animate skills were unavailable at recorded paths. The installed Emil Kowalski apple-design instructions were read for interruptibility, feedback and accessibility.

Device audio playback and live key/library/preparation checks remain required. Local release APK is unsigned; the existing GitHub workflow publishes its installable debug APK after emulator startup checks.
