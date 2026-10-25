# SHAI Loader · Concept sheet → Violet court

## Direction and acceptance

- Subject: Android library-access loader for panel-issued game keys.
- Audience: phone users redeeming a key, then preparing and injecting one library.
- Job: redeem → choose a library explicitly → prepare → inject.
- Mode: upgrade, retaining the Kotlin / Android XML / Gradle stack and every view ID.
- Before authentication: a **concept sheet**. Ivory paper, graphite contour
  drawing, drafting margin, construction circle, annotation arrow and a violet
  pencil. The sheet includes a ghosted frame of the signed-in layout, so the
  first screen is literally a drawing of the second.
- After authentication: **violet court maximalism**. The sketch is built out:
  aubergine field, gilded double frame, girih-like guilloche rings, mirrored
  corner scrolls, star dust, serif display type, plasma crest, engraved
  license plaque, gold-foil primary action and an instrument panel.
- Dials: variance 6/10 sketch, 8/10 court; motion 2/10 sketch, 5/10 court;
  density 3/10 sketch, 8/10 court.
- Signature: one line drawing becomes the gilded crest, and the sheet's ghosted
  frame becomes the real frame.
- Rules: one primary action; every ornament is structural (margin, rule, frame,
  plaque, plate); the sketch is not a filled-in form, the court is not a recolour.

### Tokens

| Token | Value |
|---|---|
| paper | `#F7F2E7` |
| paper card | `#FDF9F0` |
| ink | `#211C24` |
| pencil | `#6F6270` |
| draft violet | `#6A4A8C` |
| court void | `#0A0610` |
| court plum | `#241232` |
| court amethyst | `#3A1F57` |
| gold | `#E9C77E` |
| deep gold | `#9D743A` |
| orchid | `#E2C7FF` |
| brand violet | `#925CF4` |
| mint / active | `#58E2AA` |

- Type: `casual` hand lettering and sans labels on the sheet; `serif` display,
  tracked sans metadata and monospace package data in the court.
- Shape: 3dp sketch contours, 7dp panels and 9dp primary action in the court.
- Motion: one 520ms assembled phase transition with a 420ms return, all
  curves and durations in `ShaiMotion`; every animation honours the system
  animator scale. No looping animation.
- Interaction: 100ms press-in / 160ms press-out feedback, a 320ms sheet
  entrance, a 300ms drawer with a capped row stagger, and a 760ms reveal
  before the drawer opens.
- Handoff: splash enters for 240ms, activity transitions are 220ms in /
  180ms out. The signed-in plaque replaces the license form and rises with
  the phase instead of racing it with its own fade.

## De-slopped controls (frontend-design-deslop audit)

Audited against `samber/cc-skills@frontend-design-deslop`'s slop checklist;
the committed direction and audit table live in `DESIGN.md`.

- **Signed-in gating.** `library_card`, `operation_section` (method, delay,
  mode) and `browse_button` are `gone` before login and revealed with a short
  stagger only after the key is accepted; leaving the signed-in phase hides
  them again. The unauthenticated sheet is only the key form.
- **Real dropdowns.** Method and delay were already spinners but had no drawn
  affordance; their cells now carry a gold `ic_chevron_down` over the
  phase-bordered surface, and they still open as `spinnerMode="dropdown"`.
- **Auto / Manual control.** The 1dp hidden `RadioGroup` stub is gone. In its
  place, inside the gated injection section, sit two labelled 48dp rows of
  square court boxes (18dp box, 4dp gold border): exactly one is ticked —
  checkbox look, radio behaviour. `LoaderPreferences.isAutoMode()` is now the
  source of truth when injecting, so auto injection no longer depends on a
  hidden widget's state.
- **Fixed: controls stayed disabled after login.** `beginLicenseRedemption`
  disabled the mode/delay controls but nothing re-enabled them on the success
  path; they now re-enable (and dim to 0.45 alpha when disabled) through one
  owner, `setInjectionControlsEnabled`.
- **Type floor.** 22 labels below 11sp were raised to 11sp; sentence hints to
  12sp. The floor is enforced by the QA contract.

## Restored controls and remembered key

- `delay_spinner` and `method_spinner` are visible 50dp selectors with labels,
  persisted through `LoaderPreferences`; they are no longer 1dp hidden stubs.
  Delay remains "time after the game opens, before the library loads".
- `remember_key` is an opt-in checkbox. While checked, the field's value is
  stored in app-private `SharedPreferences`; unchecking, or "Use another key",
  deletes it.
- One-shot auto-login runs after the catalog/version check: it fills the saved
  key and starts the **same** redemption flow. It never downloads, prepares or
  injects a library, never opens the game, and does not repeat in a session.
- The accepted key is shown only as a fingerprint (`KEY  ABCD ···· WXYZ`) in the
  signed-in plaque.
- Auto-login is skipped when the activity is recreated; the saved key is still
  prefilled so the user can redeem deliberately.
- License keys remain in app-private preferences, the same store the existing
  per-library license map already used. They are not encrypted at rest and are
  only sent to the configured panel endpoints.

## Changed files

- `app/src/main/res/layout/activity_main.xml` — two-phase hierarchy; restored
  selectors; remember control; fingerprint; ornament rules; plaque.
- `app/src/main/kotlin/astral/LoaderVisuals.kt` — paper/court backdrop painting,
  phase surfaces, gilded states, `OrnamentRuleView`.
- `app/src/main/kotlin/astral/MainActivity.kt` — phase styling, remembered-key
  flow, one-shot auto-login, control wiring, fingerprint, press feedback,
  drawer/row choreography, success reveal, keyboard and focus recovery.
- `app/src/main/kotlin/astral/ShaiMotion.kt` — shared curves, durations and
  reduced-motion timing.
- `app/src/main/kotlin/astral/LoaderPreferences.kt` — opt-in key storage.
- `app/src/main/kotlin/astral/SimpleSpinnerAdapter.kt` + spinner layouts —
  phase-aware cells, readable dark dropdown.
- `app/src/main/res/drawable/hero_sketch.xml`, `hero_ethereal.xml` — measured
  concept figure and the maximal crest.
- `app/src/main/res/drawable/seal_ring.xml` — double-ring plaque seal.
- `app/src/main/res/drawable/checkbox_sheet*.xml` — drawn remember-key control.
- `app/src/main/res/layout/activity_splash.xml`, `activity_splash` colors —
  the sheet language carried into the opening screen.
- `app/src/main/res/layout/item_library_drawer.xml`, `status_pill.xml`,
  `maintenance_pill.xml`, `colors.xml`, `themes.xml` — court palette and rows.
- `app/src/main/res/layout/spinner_item_simple.xml`,
  `res/drawable/ic_chevron_down.xml` — drawn dropdown affordance.
- `app/src/main/res/drawable/checkbox_court*.xml` — 18dp/4dp AUTO/MANUAL
  boxes; the old 1dp `RadioGroup` stub was removed from the layout.
- `app/src/main/kotlin/astral/LoaderPreferences.kt` — `isAutoMode()` is the
  injection-time source of truth.
- `DESIGN.md` — design system of record and the de-slop audit table.
- `app/src/main/kotlin/astral/AboutDialog.kt` — serif/gold treatment when signed
  in, staged entrance and press feedback.
- `app/src/main/kotlin/astral/SplashActivity.kt`, `res/anim/fade_in.xml`,
  `res/anim/fade_out.xml` — short eased handoff and splash sheet settle.
- `qa/verify_ui_contract.py` — checks for visible controls, remembered-key flow,
  distinct phase systems, and motion tokens/reduced-motion fallbacks.

## Verification

Environment: Temurin JDK 17.0.21, Android SDK Platform 35, Build-Tools 35.0.0,
Gradle 8.11.1, AGP 8.9.1, Kotlin 2.0.21. Build command:

```sh
bash ./gradlew :app:assembleDebug :app:testDebugUnitTest --no-daemon --no-parallel \
  -Dorg.gradle.jvmargs="-Xmx1536m -Dfile.encoding=UTF-8"
```

| Check | Result |
|---|---|
| `:app:assembleDebug` | **PASS** |
| `:app:testDebugUnitTest` | **PASS** (2 tests) |
| `:app:lintDebug` | **PASS**, 0 errors, warnings only |
| `python3 qa/verify_ui_contract.py` | **PASS**, all assertions |
| XML parse + ID uniqueness + resource resolution | **PASS** |
| Kotlin compilation | **PASS** |
| Motion contract (tokens, staged reveal, press/focus, IME) | **PASS** (source-level) |
| Signed-in gating, dropdown affordance, mode boxes, 11sp floor | **PASS** (source-level) |
| Slop checklist (`DESIGN.md`) | **PASS** after fixes; render-only items NOT RUN |
| Device/emulator rendering and touch checks | **NOT RUN** (no device attached) |
| Live panel redemption, download, injection | **NOT RUN** (requires a valid key and game package) |

Known non-blocking lint warnings: 186 total, 0 errors (down from 202 after
the radio stubs and sub-11sp labels were removed). Mostly hardcoded design
strings, small tracked labels, `TooManyViews` on the single scrolling screen,
unused legacy resources, and pre-existing security/permission warnings from
the loader core. No new errors.

The debug APK is produced at `app/build/outputs/apk/debug/app-debug.apk`; build
outputs are intentionally not committed.
