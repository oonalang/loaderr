# context.md — SHAI Loader: full project context

> Single-file briefing for any engineer (or agent) continuing work on this repo.
> Everything below reflects the code as inspected on 2026-10-25. Do not treat
> this file as a substitute for reading the source before editing.

## 1. What this project is

SHAI Loader ("SHAI / the keykeeper's notebook") is an **Android app** that:

1. Opens with a splash hand-off (`SplashActivity`), detects VPN interfaces and
   blocks if one is active.
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
  (`TextInputLayout` for the key field), coroutines/lifecycle
- **Built on-device in AndroidIDE** (`/storage/emulated/0/AideProjects/loader`)
  — there is no CI. The on-device `:app:assembleDebug` build is the compile
  gate. Always run `qa/verify_ui_contract.py` before pushing.

## 3. Source map (what lives where)

| Path | Role |
|---|---|
| `app/src/main/kotlin/astral/MainActivity.kt` (~1,690 lines) | Main screen + phase system (`updateTheme`, `animateToPhase`), redemption flow, drawer, entrance motion |
| `app/src/main/kotlin/astral/SplashActivity.kt` | Opening sheet (320ms), VPN detection, notification permission |
| `app/src/main/kotlin/astral/LoaderVisuals.kt` | `ShaiPalette` tokens, `LoaderBackdropView` (phase-aware graph-paper backdrop), `PhaseSurfaceDrawable` (all enclosed surfaces), `OrnamentRuleView` (self-drawing rule) |
| `app/src/main/kotlin/astral/ShaiMotion.kt` | Named motion tokens (PRESS/ENTRANCE/RULE_REVEAL/PHASE_IN 520ms/PHASE_OUT 420ms/CREST) + PathInterpolators |
| `app/src/main/kotlin/astral/LoaderPreferences.kt` | Remembered key, per-library keys, auto-mode preference |
| `app/src/main/res/layout/activity_main.xml` (~830 lines) | **One layout for both auth states**; gating is code-driven |
| `app/src/main/res/layout/activity_splash.xml` | Splash sheet |
| `app/src/main/res/font/patrick_hand.ttf` | Handwriting voice (OFL; license in `assets/OFL-patrick-hand.txt`) |
| `qa/verify_ui_contract.py` | 80 static source checks — the repo's design contract |

## 4. The phase system (read before touching any view)

The main screen cross-fades between two states on **one shared layout**:

- **Phase 0 — the concept sheet (pre-login / "before login"):** warm paper,
  faint blue graph ruling, sketch borders, handwriting margin notes, washi
  tape holding the hero sketch, highlighter-yellow primary action.
- **Phase 1 — the violet court (signed in):** ink-dark panels
  (`#15111D` family), gold accent `#E9C77E`, desaturated banner.

`updateTheme(progress: Float)` (called per frame of a 520/420ms
`ValueAnimator`) blends every color via `ShaiPalette.blend` and drives:

- `backdrop.phase`, all `PhaseSurfaceDrawable.phase` fields
- Text colors through id lists (`titleIds`, `secondaryIds`, accent ids)
- `sheet_plate` ("SHAI / 01" ↔ "SHAI / 02"), `sheet_note`
  ("Not yet verified" ↔ "READY"), hero art swap
  (`hero_sketch` ↔ `hero_ethereal`, identical pathData — QA-checked), tape
  alpha (`1 - phase`), hero paper tilt (-1.5° unsigned)
- Banner desaturation: `phaseColorMatrix.setSaturation(progress)`, then
  `appBanner.colorFilter = ColorMatrixColorFilter(phaseColorMatrix)`.
  **`ColorMatrixColorFilter` has no `setMatrix()`** — the filter copies the
  matrix at construction. This exact line caused a build break once; keep the
  construction pattern.

Visibility gating lives in `setSignedInControlsVisible()`
(`library_card`, `operation_section`, `browse_button`) and
`license_section` visibility flips in `resetLoginState`. Views added for the
pre-login sheet must either live inside a gated section or be made
phase-aware in `updateTheme`.

Per-frame hygiene: `updateTheme` runs on every animation frame — use the
`cv()`/`tv()` view cache, reuse the `phaseColorMatrix`, and never allocate
lookups per frame beyond the one `ColorMatrixColorFilter` construction.

## 5. Design system — "the keykeeper's notebook"

Genre: **Conceptual Sketch** — a designer's notebook. Committed on every
screen; do not blend with another style.

- **Palette roles:** paper `#F7F2E7` (background) · graph blue `#7E9CC4`
  (ruling, low alpha) · card `#FDF9F0` (surface) · ink `#211C24` (text) ·
  pencil `#6F6270` (muted/border) · **one accent** highlighter `#F5D442`
  (primary action fill + headline swipe only) · violet court tokens for
  phase 1 (see `ShaiPalette`).
- **Type:** sans-serif bold for display, sans-serif body; **handwriting
  (Patrick Hand) only for margin notes**. No serif anywhere.
- **Surfaces:** wobbly hand-drawn borders (sketch double-pass stroke in
  `PhaseSurfaceDrawable`), hatching/texture instead of shadows.
- **Motion:** sketch lines draw themselves (`OrnamentRuleView.reveal`, arrow
  AVD `avd_sketch_arrow` trimPathEnd); entrance orchestrated once in
  `playEntranceMotion()`. Everything respects
  `ValueAnimator.areAnimatorsEnabled()`.
- **Never:** gradients, glow blobs, glossy buttons, perfect vector shadows,
  emoji icons, uppercase-tracked eyebrows on every heading, fake
  statistics/testimonials, `transition: all`-style layout-shifting motion.
- Arrows and margin notes must point at **real features**; the page must read
  with all drawings removed.

## 6. QA contract — run before every push

```bash
python3 qa/verify_ui_contract.py
```

80 static checks, including:
- All res XML parses; view IDs unique; required views present in both phases
- `library_card` / `operation_section` / `browse_button` start `gone`
- No implicit downloads on redemption (`validateLicense` block audited)
- Spinner/manual-mode invariants, remembered-key flow invariants
- hero art pathData identical across auth states
- No text below 11sp; 48/52/56dp touch targets declared
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
python3 qa/verify_ui_contract.py      # before every commit
```

History note: the redesign history includes a period where UI commits shipped
without ever compiling. The 2026-10-25 session fixed the one outstanding
compile error (`setMatrix`, commit `d72f735`) — keep the discipline of
build-then-push.

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
  - this commit adds `context.md`

## 9. Working agreements for future edits

1. Preserve every named requirement/exclusion from the task brief; scope is
   added-to only when the user asks.
2. Keep the phase cross-fade intact: anything colored must blend via
   `ShaiPalette.blend`, anything pre-login-only must gate or fade.
3. QA contract green + native hashes unchanged are push prerequisites.
4. State honestly which validations ran; never claim a build or screenshot
   that did not happen.
5. Match the user's language (English, casual register is fine) in replies.
