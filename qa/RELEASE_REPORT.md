# SHAI Loader — opening and key page

Pass: Conceptual Sketch, second iteration (2026-10-26).
Scope: the cover (`SplashActivity`) and the unauthenticated page, plus the
continuity of that drawing into the signed-in court. Licensing, catalog,
selection, preparation and injection behaviour are unchanged.

Rendered frames: `qa/screenshots/` (390dp sheet, 360dp sheet, cover, court).

## What changed

### The cover
- The boxed splash card is gone. The cover is a page: wordmark with one
  highlighter pass, the keykeeper drawing, and a single honest status line.
- Removed `SHAI / 01`, "The keykeeper's notebook", "opens right here",
  "SHEET 01", and the indeterminate progress bar. There is no simulated
  percentage and no filler copy.
- The hand-off is no longer a timer. The cover paints, runs the VPN gate and
  the one-time notification prompt, and leaves the moment they settle
  (`Choreographer` + permission result, not `postDelayed(320)`). Two frames are
  posted after the checks only so the cover has actually drawn; no animation
  is awaited. With the system animation scale at zero it is unchanged.

### The key page
- Header is the wordmark plus real state ("Key required" / "Key verified");
  the sheet numbers, folio rule, subtitle and "Library access" eyebrow are
  gone.
- The illustration moved from a vector `ImageView` into
  `ShaiIllustrationView`: hand-authored contours drawn with Canvas and revealed
  stroke-by-stroke through `PathMeasure`.
- Typography is Space Grotesk (400/500/700, bundled) with Caveat reserved for
  one margin note. Sheet body text is now 16sp or larger; the headline is 30sp
  with display tracking.
- The key field is a drawn box (`SketchFieldDrawable`) over a `boxBackgroundMode="none"`
  input, so the control keeps Material's hint/error/accessibility while the
  outline is authored linework with a stateful focus stroke.
- One annotation with a real target: "saved only if you tick this", arrow
  landing on the Remember toggle.
- The primary action reads "Redeem key" in sentence case; the signed-in
  actions follow ("Choose library", "Prepare library", "Inject").
- Edge-to-edge insets for the status bar, cutout and IME; the field scrolls
  above the keyboard.
- Removed: the washi-tape strip (the drawing is on the page, not taped to it),
  the bitmap paper tile and its density PNGs, the old highlight vector, the
  old splash card shape, Patrick Hand.

### Continuity
The same drawing persists into the court: `phase` blends ink → gold and pencil
→ lilac per stroke, the highlighter wash fades out with the paper, the headline
sweep becomes a quiet gold trace, and the Remember box switches to the gold
court box. No second artwork, no bitmap cross-fade.

## Startup crash investigation

**Input available:** none. The brief asked to investigate "the reported crash";
no stack trace, `logcat`, device model or reproduction steps were supplied, and
the repository and its history contain no crash record. A root cause cannot be
asserted without evidence, so this is reported as an investigation, not a fix.

**Device/emulator attempt — BLOCKED.** The Android 35 x86_64 system image and
emulator were installed and an AVD created, but the emulator cannot start:

```
ProbeKVM: This user doesn't have permissions to use KVM (/dev/kvm).
ERROR | x86_64 emulation currently requires hardware acceleration!
```

`/dev/kvm` exists but is `root:kvm 0660` and the sandbox user is not in `kvm`;
`sudo` is refused ("no new privileges" flag). ARM images are not supported on
an x86_64 host, so there is no path to a booted device here.

**What was actually executed instead.** `PageRenderTest` starts both activities
under Robolectric with native graphics and renders the real layouts:
`MainActivity` reaches RESUMED, `onCreate` (Glide load, view binding,
`setupVisuals`, `updateTheme(0f)`, entrance animators, catalog coroutine) and
the first draw all complete without exception; `SplashActivity` creates and
inflates its page without exception. This is real JVM Android rendering, not a
hardware runtime.

**Launch-path defects found and fixed during the audit:**

1. **Orphaned permission dialog (real, reproducible by reading the code).**
   `SplashActivity` requested `POST_NOTIFICATIONS` and then finished 320ms
   later regardless of the answer. A prompt still on screen outlived its
   activity, and the denial path could `Toast` on a finishing activity. The
   requester now reports back (`onSettled` / `onPermissionResult`), the cover
   waits for the real result, and navigation is idempotent (`leaving`,
   `isFinishing`, `isDestroyed`).
2. **Startup timer.** The VPN gate ran after a fixed 320ms delay "to finish an
   animation". It now runs on the first painted frame, and navigation waits only for the checks plus two frames of cover.
3. **Edge-to-edge on API 35.** With `targetSdk 35`, content is laid out under
   the status bar; the sheet's header would sit under the clock on Android 15.
   Explicit `WindowCompat` insets are applied to both pages.
4. **First-frame view lookups.** `updateTheme()` runs inside `onCreate` and
   reads ~30 ids; removing an editorial label without removing its lookup is a
   guaranteed `NullPointerException` at startup. All references to the removed
   ids were deleted and the page is rendered in tests to prove the binding set
   is complete.

**Verdict: no crash reproduced; hardware startup is UNVERIFIED.** If the crash
persists, a `adb logcat -b crash` trace (or the Play Console stack) is needed to
go further; a hypothesis is not a root cause.

## Verification

Environment: Temurin JDK 17, Android SDK 35 / Build-Tools 35.0.0, Gradle 8.11.1,
AGP 8.9.1, Kotlin 2.0.21, Robolectric 4.14.1 (test only).

| Check | Result | Evidence |
|---|---|---|
| `:app:assembleDebug` | **PASSED** | APK produced |
| `:app:testDebugUnitTest` | **PASSED** (6 tests, 0 failures) | includes 4 render/startup tests |
| `:app:lintDebug` | **PASSED**, 0 errors, 180 warnings | 6 fewer than the previous pass |
| `python3 qa/verify_ui_contract.py` | **PASSED**, 139 assertions | includes 30 new contract checks |
| Rendered cover, 390dp | **PASSED** | `qa/screenshots/splash-390dp.png` |
| Rendered key page, 390dp | **PASSED** | `qa/screenshots/key-page-390dp.png` |
| Rendered key page, 360dp, no clipping | **PASSED** | `qa/screenshots/key-page-360dp.png` |
| Court re-ink (`updateTheme(1f)`) | **PASSED** | `qa/screenshots/court-phase-390dp.png` |
| Reduced motion (animator scale 0) | **PASSED** (source + contract) | illustration completes, sweep full |
| Startup on a hardware device | **BLOCKED** | KVM denied, no device attached |
| Frame pacing / haptics / IME on hardware | **UNVERIFIED** | requires a device |
| Live panel redemption, download, injection | **UNVERIFIED** | requires a valid key and game |

Notes: lint warnings are the pre-existing set (hardcoded design strings,
`TooManyViews`, legacy unused resources and loader security/permission notices).
`--- Failed to create image decoder with message 'unimplemented'` in the test
log is Robolectric's native-graphics decoder stub for the Glide-packed PNG
banner, not a failure.

## Changed files

- `app/src/main/kotlin/astral/ShaiIllustrationView.kt` — new Canvas
  illustration with per-stroke `PathMeasure` reveal, phase inking, one-shot
  animation and reduced-motion completion.
- `app/src/main/kotlin/astral/SketchSurfaces.kt` — new `SketchFieldDrawable`
  (drawn field, focus/disabled states) and `HighlightSweepDrawable`.
- `app/src/main/kotlin/astral/SplashActivity.kt` — cover page, insets, painted
  first frame, permission-aware hand-off, no timer.
- `app/src/main/kotlin/astral/NotificationPermissionRequester.kt` — reports
  whether the prompt settled.
- `app/src/main/kotlin/astral/MainActivity.kt` — sheet header/status, drawn
  field, illustration + sweep wiring, insets and IME scroll, court checkbox,
  sentence-case actions, removal of the label/typeface overrides.
- `app/src/main/kotlin/astral/LoaderVisuals.kt` — specified paper palette,
  24dp canvas ruling, no grain, no bitmap texture.
- `app/src/main/kotlin/astral/ShaiMotion.kt` — illustration and sweep tokens.
- `app/src/main/res/layout/activity_splash.xml` — cover composition.
- `app/src/main/res/layout/activity_main.xml` — sheet rework, 16sp floor,
  drawn field, annotation target, gated menu.
- `app/src/main/res/font/{space_grotesk*.ttf,space_grotesk.xml,caveat.ttf}` —
  bundled typefaces (OFL, licenses in `assets/`).
- `app/src/main/res/drawable/checkbox_sheet*.xml` — sheet inks.
- `app/src/test/kotlin/astral/PageRenderTest.kt` — JVM rendering/startup tests.
- `app/build.gradle.kts` — Robolectric (test only) + android resources.
- Deleted: `hero_sketch.xml`, `hero_ethereal.xml`, `highlight_swipe.xml`,
  `splash_sketch.xml`, `tape_piece.xml`, `paper_grid.xml`, five
  `paper_grid_tile.png` density tiles, `patrick_hand.ttf` and its licence.
- `qa/verify_ui_contract.py`, `DESIGN.md`, `qa/RELEASE_REPORT.md`, `context.md`.

## Independent critical review

**As an Android engineer.** The risky parts are covered: startup completes in a
real inflater/renderer, the new views respect `areAnimatorsEnabled`, animations
cancel before restarting, and the drawn field preserves exact bounds for touch.
The weak points are environmental: nothing here ran on a phone, so the stroke
timing, the 720ms reveal against a fast device's launch, and IME behaviour are
unmeasured. Robolectric also cannot catch OEM theme or hardware-accelerated
canvas differences. The page is one long `ScrollView` with a fixed 184dp
illustration; at 200% font scale the art does not grow, which is correct for an
illustration but means the page becomes very tall.

**As an editorial designer.** It reads as a page rather than a themed form: one
drawing, one highlighter, one note, one action, and a ruling that stays quiet.
The illustration is specific (a keykeeper with a key laid beside her) and is
inked, not pasted. The weakest composition is the header: a 27sp wordmark and a
30sp headline sit close in weight, and the right-aligned state label competes
slightly. The key floats beside the figure rather than being held — a deliberate
choice after a drawn forearm read as a mask at real size; it now behaves like an
object laid on the page, but it is less narrative than holding it.

**As a critic against the brief's reject criteria.**

| Criterion | Verdict |
|---|---|
| Artwork removable and the page still a generic form | Partly — the field and type are composed around the drawing, but the page would still function without it. Weakest point. |
| Meaningless handwriting or fake concept labels | Pass — all invented labels removed; one note with a real consequence. |
| Strokes mechanically perfect or randomly distorted | Pass — hand-authored cubics with fixed, deliberate irregularity. |
| Annotations without legitimate targets | Pass — the arrow lands on the Remember toggle. |
| Resemblance only via the background grid | Pass — the drawing, the field contour and the note carry the style. |
| Authenticated state loses the illustration | Pass — same strokes, re-inked; verified in the court render. |
| Effects interfering with function | Pass — sweep sits behind text, no overlay over controls; contrast held. |
| Startup crash uninvestigated | Fail to *prove*: investigated and exercised on the JVM, but device reproduction is blocked (see above). |
