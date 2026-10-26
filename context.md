# context.md — SHAI Loader: full project context

> Single-file briefing for any engineer (or agent) continuing work on this repo.
> Everything below reflects the code as inspected on 2026-10-25. Do not treat
> this file as a substitute for reading the source before editing.

## 1. What this project is

SHAI Loader ("SHAI / the keykeeper's notebook") is an **Android app** that:

1. Opens with a notebook cover (`SplashActivity`): wordmark, one drawing, one
   honest status line. It paints one frame, runs the VPN gate and the one-time
   notification prompt, then hands off. No timer, no fake percentage.
2. Asks for a **license key** (paste from the user's panel), redeems it against
   a panel endpoint (`PanelLicenseValidator`).
3. On success, lets the user pick **one library** from a catalog
   (`LibraryCatalogClient` fetches JSON; drawer UI lists entries).
4. Configures injection options (method 1–3, launch delay 0–5s, AUTO/MANUAL
   mode) and prepares the selected library via `LibraryDownloader` +
   `InjectionCommandBuilder`, running loader binaries through
   `CommandRunner` (`.so` assets shipped in `app/src/main/assets/`).

Package: `astral.loader` (plus a legacy `kea.loader` MainActivity kept for
compatibility with the loader core). Native components named above are
**hash-pinned** in `qa/unchanged_native_components_sha256.txt` — do not modify
`CommandRunner.kt`, `InjectionCommandBuilder.kt`, `LibraryDownloader.kt`.

## 2. Technical profile

- Language: **Kotlin**, native Views (XML layouts, no Compose)
- `compileSdk = 35`, `targetSdk = 35`, **`minSdk = 28`**
- AGP 8.9.1 / Gradle 8.11.1, Kotlin JVM 17 toolchain
- Dependencies: AndroidX AppCompat, Material Components
  (`TextInputLayout` for the key field), coroutines/lifecycle, Glide
- Test-only: JUnit 4 + **Robolectric 4.14.1** (native graphics). This project
  is built on a phone with no emulator, so `PageRenderTest` renders the real
  layouts on the JVM and writes frames to `app/build/reports/ui/`.
- **Built on-device in AndroidIDE** (`/storage/emulated/0/AideProjects/loader`)
  — there is no CI. The on-device `:app:assembleDebug` build is the compile
  gate. Always run `qa/verify_ui_contract.py` before pushing.

## 3. Source map (what lives where)

| Path | Role |
|---|---|
| `app/src/main/kotlin/astral/MainActivity.kt` (~1,720 lines) | Main screen + phase system (`updateTheme`, `animateToPhase`), redemption flow, drawer, entrance motion, window insets |
| `app/src/main/kotlin/astral/SplashActivity.kt` | Opening sheet (320ms), VPN detection, notification permission |
| `app/src/main/kotlin/astral/LoaderVisuals.kt` | `ShaiPalette` tokens, `LoaderBackdropView` (24dp canvas ruling, phase-aware), `PhaseSurfaceDrawable` (all enclosed surfaces), `OrnamentRuleView` (self-drawing rule) |
| `app/src/main/kotlin/astral/ShaiIllustrationView.kt` | The keykeeper, drawn on Canvas; per-stroke `PathMeasure` reveal; phase re-inking |
| `app/src/main/kotlin/astral/SketchSurfaces.kt` | `SketchFieldDrawable` (drawn key field) and `HighlightSweepDrawable` (headline marker) |
| `app/src/main/kotlin/astral/ShaiMotion.kt` | Named motion tokens (PRESS/ENTRANCE/RULE_REVEAL/PHASE_IN 520ms/PHASE_OUT 420ms/CREST) + PathInterpolators |
| `app/src/main/kotlin/astral/LoaderPreferences.kt` | Remembered key, per-library keys, auto-mode preference |
| `app/src/main/res/layout/activity_main.xml` (~830 lines) | **One layout for both auth states**; gating is code-driven |
| `app/src/main/res/layout/activity_splash.xml` | Splash sheet |
| `app/src/main/res/font/space_grotesk*` | Functional voice: Space Grotesk 400/500/700 (OFL) behind `@font/space_grotesk` |
| `app/src/main/res/font/caveat.ttf` | Annotation voice only (OFL); licences in `assets/` |
| `app/src/test/kotlin/astral/PageRenderTest.kt` | JVM render/startup tests → `qa/screenshots/` |
| `qa/verify_ui_contract.py` | 80 static source checks — the repo's design contract |

## 4. The phase system (read before touching any view)

The main screen cross-fades between two states on **one shared layout**:

- **Phase 0 — the concept sheet (pre-login):** paper `#FDFCF7`, 24dp
  `#C9DAF8` ruling, ink `#1F2937` contours, one Caveat margin note, the drawn
  keykeeper, highlighter `#FFF176` primary action.
- **Phase 1 — clay (signed in):** lavender `#F4EEFF`, moulded pastel surfaces
  (pink `#FFB8D9`, mint `#A8E6CF`, periwinkle `#B5C7FF`), one upper-left light,
  Fredoka + Nunito. The violet court is retired — no court token survives.

`updateTheme(progress: Float)` (called per frame of a 640/420ms
`ValueAnimator`) blends every color via `ShaiPalette.blend` and drives:

- `backdrop.phase`, all `PhaseSurfaceDrawable.phase` fields,
  `fieldSurface.phase`, `headlineSweep.phase`, `heroIllustration.phase`
- Text colors through id lists (`titleIds`, `secondaryIds`, accent ids)
- `sheet_note` (real state: "Key required" ↔ "Key verified"), the same
  illustration geometry sculpted rather than re-inked, the Remember/mode boxes
  (ink on paper ↔ clay beads), the verified emblem (ring ↔ mint bead), the
  drafting rule (drawn ↔ `GONE`), and the hero's paper tilt (-1.5° unsigned)
- Type voices through one call, `applyTypefaces(clay)` (Space Grotesk ↔
  Fredoka + Nunito); never set a typeface anywhere else
- Banner desaturation: `phaseColorMatrix.setSaturation(progress)`, then
  `appBanner.colorFilter = ColorMatrixColorFilter(phaseColorMatrix)`.
  **`ColorMatrixColorFilter` has no `setMatrix()`** — the filter copies the
  matrix at construction. This exact line caused a build break once; keep the
  construction pattern.

Visibility gating lives in `setSignedInControlsVisible()`
(`library_card`, `operation_section`, `browse_button`,
`library_menu_button`) and
`license_section` visibility flips in `resetLoginState`. Views added for the
pre-login sheet must either live inside a gated section or be made
phase-aware in `updateTheme`.

Per-frame hygiene: `updateTheme` runs on every animation frame — use the
`cv()`/`tv()` view cache, reuse the `phaseColorMatrix`, and never allocate
lookups per frame beyond the one `ColorMatrixColorFilter` construction.

## 5. Design system — "the keykeeper's notebook"

Genre: **Conceptual Sketch** — a designer's notebook. Committed on every
screen; do not blend with another style. The full rationale, palette table and
reject criteria live in `DESIGN.md`.

- **Palette roles (exact):** paper `#FDFCF7` · grid `#C9DAF8` · ink `#1F2937`
  · pencil `#777B84` · **one accent** highlighter `#FFF176` (primary fill,
  headline sweep, the key's wash). No sixth hue on paper — `draft` resolves to
  ink. Clay tokens for phase 1 (see `ShaiPalette` and `DESIGN.md`).
- **Type:** **Space Grotesk** on the sheet, **Fredoka SemiBold** (display) and
  **Nunito** (body/controls) in clay, all resolved through `ShaiType`.
  **Caveat only for genuine annotations** (currently one, pointing at the
  Remember toggle) and never in clay. Body floor 16sp, supporting 14sp, labels
  13sp, chips 12sp. No serif anywhere.
- **Clay (phase 1):** every functional surface is `PhaseSurfaceDrawable`, which
  morphs a ruled paper edge into a moulded form: stack shadow inside a reserved
  inset, matte face, one 135° bevel gradient clipped to the face. Recessed
  controls (`SEARCH`, `SELECT`) invert the light and cast no shadow.
  **The inset must stay ≥ the shadow's reach** or the falloff is clipped flat.
  Radii: card/panel 30dp, plaque 26dp, primary 22dp, secondary/row 18–20dp,
  wells 16dp. See `DESIGN.md` § *The lighting recipe*.
- **Surfaces:** bowed, overshooting contour strokes (`SketchFieldDrawable`,
  `PhaseSurfaceDrawable` double pass); hatching instead of shadows; rules
  separate information instead of cards.
- **Motion:** the drawing inks itself (`ShaiIllustrationView` + `PathMeasure`,
  `ILLUSTRATION_MS = 720`), then the headline sweep (`HIGHLIGHT_MS = 300`);
  rules draw from their centre, the arrow AVD plays once. Every animation is
  interruptible, starts from the current value, and completes instantly when
  animations are disabled. Nothing loops.
- **Never:** gradients, glow blobs, glossy buttons, perfect vector shadows,
  emoji icons, uppercase-tracked eyebrows on every heading, fake
  statistics/testimonials, `transition: all`-style layout-shifting motion.
- Arrows and margin notes must point at **real features**; the page must read
  with all drawings removed.

## 6. QA contract — run before every push

```bash
python3 qa/verify_ui_contract.py
```

139 static checks, including:
- All res XML parses; view IDs unique; required views present in both phases
- `library_card` / `operation_section` / `browse_button` start `gone`
- No implicit downloads on redemption (`validateLicense` block audited)
- Spinner/manual-mode invariants, remembered-key flow invariants
- hero art pathData identical across auth states
- No text below 11sp; 48/52/56dp touch targets declared
- Sheet contract: exact palette values, Space Grotesk theme, Caveat only for
  the note, 16sp body floor, no invented labels, no bitmap paper, no splash
  progress bar, no startup timer, drawn field, annotation target, motion tokens
- Anchored `inside()` slices of MainActivity (e.g. success-reveal block ends
  at `phaseColorMatrix.setSaturation(phaseProgress)`) — moving those anchor
  lines breaks the script; update anchors **deliberately** in the same commit
  if the code must change.

Two known false positives exist in a separate sandbox ref-checker
(`stat_sys_download` is `android.R.drawable`; density-bucket PNGs are not
scanned). The QA script itself has no false positives.

## 7. Build & run

```bash
./gradlew :app:assembleDebug          # on-device (AndroidIDE) or any SDK 35 setup
./gradlew :app:testDebugUnitTest      # includes the JVM render tests
python3 qa/verify_ui_contract.py      # before every commit
```

The render tests write `splash.png`, `key-page.png`, `key-page-narrow.png` and
`court-page.png` to `app/build/reports/ui/`; reviewed copies live in
`qa/screenshots/`. There is **no emulator in this cloud workspace** (KVM is not
granted to the sandbox user), so hardware behaviour is still unverified.

History note: the redesign history includes a period where UI commits shipped
without ever compiling. Keep the discipline of build-then-push; the JVM render
tests exist so a missing view id or a broken `onCreate` fails before a device
does.

## 8. Git notes

- Branch: `main` is the only branch; remote `github.com/oonalang/loaderr`
- Pushes use a fine-grained PAT supplied in-session; **tokens are pasted in
  chat and must be revoked after use** — never commit them, never leave them
  in the remote URL after pushing
- Recent history (newest last):
  - `d4fda45` version code bump
  - `540e6ab` deslop pass (two-ink discipline, quieter labels, perf-safe phase animation)
  - `d72f735` compile fix (drop `ColorMatrixColorFilter.setMatrix`)
  - `1d7a1c9` Conceptual Sketch pass on the pre-login sheet
  - `22df918` latest: compile fixes after the sketch pass
  - uncommitted 2026-10-26 pass: cover + key page rebuilt against the exact
    palette/type/motion brief (see `qa/RELEASE_REPORT.md`)

## 9. Working agreements for future edits

1. Preserve every named requirement/exclusion from the task brief; scope is
   added-to only when the user asks.
2. Keep the phase cross-fade intact: anything colored must blend via
   `ShaiPalette.blend`, anything pre-login-only must gate or fade.
3. QA contract green + native hashes unchanged are push prerequisites.
4. State honestly which validations ran; never claim a build or screenshot
   that did not happen.
5. Match the user's language (English, casual register is fine) in replies.
