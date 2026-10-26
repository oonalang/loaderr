# SHAI Loader — GLM-5.3 one-shot directive, delivery report

Pass date: 2026-10-26. Every claim below is backed by a command run in this
workspace; nothing is asserted from inspection alone. Frames in
`qa/screenshots/` are real Android rendering of the real layouts via
Robolectric native graphics (regenerated at 13:15–13:43).

## What changed, in one line

The identity the brief demanded is now drawn, not described: a worried keykeeper
holds a clearly **closed** padlock on the unauthenticated sheet, and after
redemption the same lady — same silhouette, same hand — is smiling in clay with
the **shackle swung open**, plus the previously missing quiet UI-sound kit, the
retired `Key required` badge, and the About version row.

## The two identity fences

- `qa/verify_ui_contract.py` grew to **216 checks**, including: `Key required`
  header note fully removed; `App version` removed from About (icon, label,
  value, row); the padlock authored as geometry whose shackle opens in clay
  (`arcTo` + `rotate(-40f * strength)`); the keyhole stays carved in the mint
  body; the sound kit plays four bundled cues, respects silent mode and a
  persisted toggle, and never loops; the toggle row is wired to its preference;
  all four `.wav` files ship.
- `ShaiSoundAndLockTest.kt` renders the illustration at phase 0 and phase 1 and
  samples pixels: the paper lock is a drawing (no mint fill), the clay body is
  mint, the shackle's right leg is seated while locked and lifts clear once
  open (a rotation, not a swap), and halfway through the blend the leg is
  already moving. It also exercises the persisted mute preference.

## Padlock + expression (ShaiIllustrationView.kt)

- Paper: closed padlock (shackle arc seated into the body, body, keyhole),
  inked on a 0.58–0.94 reveal timeline; a short arm and three finger lines grip
  the body's near edge (the full forearm was rejected before — it reads as a
  mask at 184dp, so the grip stays shallow).
- The paper face is now worried: lifted brow with one crease, a flat closed
  eye (arc flipped), and a downturned mouth — the previous shapes read as a
  smile.
- Clay: the same closed geometry rotates open about its seated leg by
  `-40° × strength`, the body inflates into mint with a lit upper-left edge,
  the keyhole is a carved groove with a white lower lip, and a pink mitt from
  the cloak keeps holding the near edge so the object stays in her hand.
- The old key (bow + stem) is gone everywhere, including the highlighter wash,
  which now sits behind the lock.

## The logo bleed (shai_mark.xml, @4.1)

The fallback mark was a 96×96 **full-bleed near-black square**, which sat inside
the 20dp-radius pink tile with exposed pink edges — the exact defect in the
marked-up screenshot. The vector now carries its own rounded plate (corner
radius 24/96) so it clips as a rounded badge inside the tile at any size; the
card banner, drawer placeholder, and Glide placeholder all benefit without a
layout change. The verified clay frame shows no exposed backplate.

## Sounds (ShaiSounds.kt, res/raw, §6)

- Four self-authored synthesized WAVs (`qa/make_ui_sounds.py` regenerates them;
  no sampled or licensed audio): a ~45ms soft tap, a two-note success bell, a
  low unresolved error tone, and a warm ready chord. All peak well below full
  scale (tap peak 0.16, cues 0.2).
- Cue map exactly as briefed: tap on meaningful buttons/toggles/selectors
  (press feedback listener, gated on clickable/enabled so inert text stays
  silent), success **only** on a verified redemption (paired with the one
  `HapticFeedbackConstants.CONFIRM`), error **only** on real
  validation/preparation failures (inside `resetLoginState`), ready **only**
  after preparation finishes.
- A lazy `SoundPool` (max 2 streams, `USAGE_ASSISTANCE_SONIFICATION`), played
  no-earlier-than-loaded (no frame hitch, no duplicate callbacks), a 110ms tap
  rate cap, silent/vibrate ringer respected, released on `onDestroy`. No
  ambience, no music, nothing loops.
- Persistent **UI sounds** row (checkbox + title + caption) between the primary
  action and the footer, in both phases, 48dp target, styled through the one
  `updateTheme` path (drawn box on paper, clay bead in clay, blended colors and
  typefaces). Muting changes nothing functional — the caption says so.

## Pre-login (§3)

| Requirement | Status |
|---|---|
| Boxed rule, `SHAI / 01`, `not yet opened`, `opens right here`, sheet numbers | Already gone (previous pass); contract still rejects them |
| `Key required` badge + spacing, completely removed | **Done this pass** — view deleted; state text removed from `updateTheme`; contract line `the Key required header note is fully removed` |
| Field hint exactly `Redeem your key.` | Verified verbatim in layout and in `clearAuthenticatedPhase` |
| Six exact rotating messages, 4s hold, fades only, 2-line reservation, fitted marker | Verified verbatim by `RefinementTest.rotatingHeadlineStartsInOrderAndReservesTwoLines` + the rendered marker |
| Only `Buy now!` tappable → https://shaistore.vercel.app on explicit tap, no auto-redirect | Verified by `onlyBuyNowOpensTheExactStoreUrlAndOnlyOnce` |
| Sad/worried lady, sketch style, holding a clearly LOCKED padlock | **Done this pass** — verified in `key-page-390dp.png` + pixel test |

## Post-login (§4)

| Requirement | Status |
|---|---|
| Logo clipped/fit in the pink tile, no exposed edges | **Done this pass** (rounded plate in `shai_mark.xml`) |
| Library card hierarchy, selected state, proportions | Already rebuilt (PhaseSurfaceDrawable & co.); verified in the clay frame |
| `Open library list` removed with no orphaned whitespace | Verified: no string, no id, no handler survives (contract checks) |
| Options restructured, AUTO/MANUAL unmistakable, persistence preserved | Already rebuilt; `checkbox_clay`, mode preference, `setInjectionControlsEnabled` intact |
| `Prepare library` as the tactile primary action with available/busy/disabled/error states | Already rebuilt; press feedback + status chips READY/PREPARING verified |
| Verification de-duplicated (`Key verified` noise removed) | Already deduped (single plaque; header note now gone entirely) |
| Same lady smiling, UNLOCKED padlock in clay | **Done this pass** — verified in `clay-page-390dp.png` + ShaiSoundAndLockTest pixel test |
| About: back button disciplined, no black-square placeholder, App Version removed, quiet attribution | Back button is a lit clay pill; art is the real keykeeper; **App version removed this pass** (`activity_about.xml`, `AboutDialog.kt`, contract + test); credit stays a quiet line |
| Layout, insets, small widths, 48dp targets, font scaling, contrast | On top of the rebuilt edge-to-edge + IME handling, `key-page-narrow-390dp.png` verified at 360dp; contrast table unchanged from `qa/CLAY_REPORT.md` (worst 6.27:1) |

## Transformation (§5)

Unchanged machinery, upgraded content: still one layout, one `phase` float,
640ms in / 420ms out (`PHASE_IN_MS` within the 500–750 window), staged handover
(contours at ×3.1/×2.6, form from phase .34), state-driven, instant final state
when animations are disabled, no downloads or preparation on the way. The brief's
locked→unlocked and worried→smiling beats now ride that same timeline as
rotations and cross-fades, not a screenshot swap.

## Verification

Environment: Temurin JDK 17.0.21, Android SDK platform 35 / build-tools 35.0.0,
Gradle 8.11.1, AGP 8.9.1, Kotlin 2.0.21, Robolectric 4.14.1 (tests only).

| Check | Result |
|---|---|
| `./gradlew :app:assembleDebug` | **PASSED** — `app-debug.apk` 16.5 MB |
| `./gradlew :app:testDebugUnitTest` | **PASSED** — 20 tests, 0 failures (incl. 7 render/startup frames, padlock pixel tests, sound-preference test) |
| `python3 qa/verify_ui_contract.py` | **PASSED** — 216 checks |
| Frames regenerated & reviewed | `splash`, `key-page`, `key-page-narrow`, `clay-page`, `clay-drawer`, `clay-mid`, `about-clay` — reviewed visually this pass |
| Hardware device / emulator | **BLOCKED** — no KVM in this workspace (unchanged from `qa/RELEASE_REPORT.md`); frame pacing, haptics, IME and real audio output on hardware remain UNVERIFIED |
| Live panel redemption, download, injection | **UNVERIFIED** — requires a valid key and network backend, unchanged native flow |
| Sound audibility on real hardware | **UNVERIFIED** — JVM renders layouts but does not play audio |

## Checked build defect found on the way

`AppCompatCheckBox` applies the theme's compound-button tint even when the XML
declares `android:buttonTint="@null"`, washing the checked drawn box into a
solid plum blob; the sound toggle now gets the same explicit
`buttonTintList = null` treatment the remember toggle already had, and the
checked sketch state was redrawn as a paper face with one firm ink tick.

## Independent critique — remaining flaws, stated plainly

1. **The grip is competent, not characterful.** It reads as a held lock at a
   glance, but a stranger would not see whose hand it is; a real illustrator
   would draw the forearm. The clay mitt is a shallow pellet behind the mint
   body — correct light, minimal detail.
2. **The open shackle reads as clipped-loop.** At -40° the arc floats clear of
   the body; some viewers may read it as decorative. The keyhole and body help,
   and the pixel test pins the motion, but the pose could be tighter (pivot
   lower, arc shorter).
3. **Sounds are function-decorated, not tuned.** The four cues are placed and
   quiet, but a sound designer would still retune envelopes against real phone
   speakers; volume trust comes from the assets' low peaks, not from a
   device-side measurement.
4. **Pre-login density at 360dp is still tight** around the field + note +
   remember rows (verified non-clipping only). Nothing we changed makes it
   worse.
