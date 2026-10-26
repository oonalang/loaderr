# SHAI Loader — the sketch becomes the sculpture

Delivery report for the authenticated Claymorphism pass. Every claim below is
backed by a command that was run in this workspace; frames are real Android
rendering of the real layouts via Robolectric native graphics.

## What changed, in one line

The signed-in state is no longer a dark violet court. It is the same page,
sculpted: one layout, one `phase` float, and every surface morphing from a ruled
paper edge into matte pastel clay with a single upper-left light.

## 1. The clay design system

Exact values, as specified:

| Role | Value | Hex in code |
|---|---|---|
| Ground | `#F4EEFF` | `clay_bg` / `ShaiPalette.clayBg` |
| Primary text | `#2B2340` | `clay_ink` |
| Raised surface | `#FAF7FF` | `clay_raised` |
| Secondary text | `#5F536F` | `clay_muted` |
| Pastel pink | `#FFB8D9` | `clay_pink` |
| Pastel mint | `#A8E6CF` | `clay_mint` |
| Pastel periwinkle | `#B5C7FF` | `clay_peri` |

Two same-hue tonal steps exist for rims and grooves only (`clayPinkDeep
#EE98C5`, `clayPeriDeep #9BB0F5`); they are never a fill or a text colour. Gold,
lilac, violet and the near-black court are gone from code — the contract now
fails if `ShaiPalette.void` / `.gold` / `.orchid` / `.darkPanel` reappear.

**Depth** is drawn, not faked: a ten-ring stack shadow inside a reserved inset,
a fully opaque matte face, then one 135° bevel gradient (white .62 → shade .20)
stroked on the face edge and clipped to it, so only the inner half shows. The
bevel is the reason these read as moulded rather than as rounded rectangles with
a drop shadow.

**Recessed ≠ raised.** Search and both dropdowns cast no shadow and are lit from
the bottom-right instead. That single inversion is what keeps the screen from
being a field of identical cards.

Measured contrast (WCAG 2.1, sRGB): ink on ground **13.07:1**, ink on raised
**13.98:1**, ink on mint **10.50:1**, ink on pink **9.25:1**, ink on periwinkle
**8.88:1**, muted on ground **6.27:1** (all AA, most AAA).

## 2. Typography

- **Fredoka SemiBold** — wordmark, `Access granted.`, section titles, drawer
  title, library names.
- **Nunito** (Regular/SemiBold) — body, captions, buttons, chips, metadata.
- **Space Grotesk** still sets the unauthenticated sheet; **Caveat** never
  appears in clay.

Static instances were cut from the official variable fonts with fontTools and
shipped in `res/font` (three new faces, ~1.1 MB total, OFL texts added to
`assets/`). All faces resolve through `ShaiType`, and `applyTypefaces()` is the
one place the voice changes.

Floors were raised for the new material, since 11sp tracked caps look wrong on
soft surfaces: body 16–17sp, supporting 14–15sp, labels 13sp, chips 12sp. The
contract now rejects anything under 12sp.

## 3. The transformation

Authentication decides; the animator only follows. `animateToPhase(1f)` is
called from the successful-redemption path, and `PHASE_IN_MS` is now **640ms**
(inside the requested 500–750ms window), with **420ms** on the way out.

Layers, in the order they resolve:

1. The 24dp construction ruling fades out by `phase .45` and the page blends
   paper → lavender.
2. The crest hands over *staged*: contour and hatching leave first
   (`×3.1`, `×2.6`), and the sculpted form only begins at `phase .34`. This was
   a correction — see §7.
3. Surfaces grow radius, stack shadow and bevel together; the ruled edge fades
   by the same value.
4. The verified plaque rises with `phase`, not on its own timer.
5. The drawer waits `880ms`, longer than the change, so the finished object is
   legible before a panel covers it.

With the system animation scale at zero, `updateTheme(target)` is applied
directly: grid gone, clay complete, controls visible. Nothing loops. Every
animator starts from the current on-screen value, so an interruption never
jumps.

## 4. Preserved functionality

No behavioural change to: license redemption and validation, the panel
catalog/redeem flow, explicit library selection, preparation, injection,
method/delay persistence, remember-key autofill (one-shot, never downloads),
VPN gate, version gate, notification permission handshake, haptics, insets/IME
handling, back handling, drawer search, or the install/update paths.

Authentication still does **not** select or prepare anything: the verified state
shows the plaque and opens the library list; the user chooses; only then can the
primary action prepare. Isolation checks in `qa/verify_ui_contract.py` still
assert exactly that (`proceedWithDownload()` absent from the redemption path).

## 5. Files changed

**Modified (21):** `MainActivity.kt`, `LoaderVisuals.kt`, `SketchSurfaces.kt`,
`ShaiIllustrationView.kt`, `ShaiMotion.kt`, `SimpleSpinnerAdapter.kt`,
`AboutDialog.kt`, `activity_main.xml`, `activity_about.xml`,
`item_library_drawer.xml`, `item_about_detail.xml`, `spinner_item_simple.xml`,
`spinner_dropdown_item.xml`, `colors.xml`, `themes.xml`, `PageRenderTest.kt`,
`qa/verify_ui_contract.py`, `DESIGN.md`, `context.md`,
`qa/screenshots/key-page-390dp.png`.

**Added (15):** `ShaiType.kt`, `ClaySurfaceProbeTest.kt`, `ids.xml`,
`checkbox_clay{,_on,_off}.xml`, `pill_{mint,pink,peri}.xml`, `seal_clay.xml`,
`tile_pink.xml`, `tile_well.xml`, `fredoka_semibold.ttf`, `nunito_regular.ttf`,
`nunito_semibold.ttf`, `OFL-fredoka.txt`, `OFL-nunito.txt`,
`clay-page-390dp.png`, `clay-drawer-390dp.png`, `clay-mid-390dp.png`.

**Removed (4):** `checkbox_court.xml`, `checkbox_court_on.xml`,
`checkbox_court_off.xml`, `court-phase-390dp.png`.

## 6. Results

| Check | Command | Result |
|---|---|---|
| Debug build | `./gradlew assembleDebug` | **PASSED** — 16.97 MB APK |
| Unit + render tests | `./gradlew testDebugUnitTest` | **PASSED** — 9 tests, 0 failures |
| Design contract | `python3 qa/verify_ui_contract.py` | **PASSED** — 136 checks |
| Clay surface probe | `ClaySurfaceProbeTest` | **PASSED** — corners, face opacity, shadow inside bounds |
| Signed-in render at 390dp | `PageRenderTest.clayPageRendersTheFinishedObject` | **PASSED** — background is `#F4EEFF`, crest phase 1.0 |
| Drawer rows render | `PageRenderTest.clayDrawerRendersLibraryRows` | **PASSED** — 2 rows, `PhaseSurfaceDrawable` backgrounds |
| Mid-transition frame | `PageRenderTest.materialChangeMidpointKeepsBothVoices` | **PASSED** — blend sits between paper and clay |
| Startup path | `MainActivity` created in 5 tests + the cover | **PASSED** — reaches RESUMED, draws, no exception |
| Hardware device / emulator | — | **BLOCKED** — no `/dev/kvm`; x86_64 emulation refuses to boot, `sudo` refused |
| Frame pacing, haptics, IME on hardware | — | **UNVERIFIED** — JVM rendering cannot measure them |
| Reported startup crash | — | **UNVERIFIED / not reproducible** — no trace was supplied; five JVM startups of `MainActivity` and one of the cover activity render clean. The earlier audit's launch-path defects (permission prompt outliving the splash, VPN gate behind a timer, header under the status bar, stale id lookups) remain fixed |

Text-size audit: minimum on the authenticated page is 12sp (status chips);
nothing below the 12sp chip floor remains anywhere.

## 7. Independent critique — what I rejected and corrected

The first two crest attempts were rejected on inspection, not shipped:

1. **"Filled outline" crest.** Filling the profile polygon produced a white mask
   floating on a pink blob — the exact failure the brief calls out (illustration
   that could be removed). Rejected.
2. **Carved profile at 4× size.** Still a mask at 184dp; the mouth and brow read
   as a moustache. Rejected.
3. **Shipped:** a front-facing face plane re-authored for the medium inside the
   *unchanged* hooded silhouette, carved grooves with a white lower lip, and the
   key as its own object (bow, stem, one tooth) so it reads at 20dp.

Rendering also exposed four engineering defects that source review had missed:

- **`drawStackShadow` aliased its own `RectF`**, so each of the ten rings
  compounded the last and pushed the face off-canvas. Every surface rendered as
  a flat rectangle. Fixed with a separate scratch rect; `ClaySurfaceProbeTest`
  now guards it.
- **Density came from `Resources.getSystem()`**, which ignores the activity's
  display metrics — every dp value in the surfaces was wrong off the default
  density. Now threaded from the view's context.
- **A drawable with no state yet was treated as disabled** and drawn at 55%
  alpha. Empty state now means "not synced", not "disabled".
- **The drawer's closed offset ignored its new 10dp margin**, so the panel
  peeked 10dp across the whole page. Closed offset now includes the margin.

Composition corrections after looking at frames: the drafting rule leaves the
layout entirely rather than leaving a 20dp gap; the injection panel became a
moulded surface so the screen has two working objects instead of one; the drawer
row moved its status chip under the text (names were truncating to
"CODM GLOBA…"); buttons lost their tracked capitals ("Open library list",
"Visit my store"); hairlines fade out in clay; the scrim became plum at 32%
instead of black at 72%.

## 8. Known weaknesses, stated plainly

- **The crest is competent, not exceptional.** It reads as a hooded clay figure
  with a carved face holding a mint key, and it shares its silhouette with the
  pre-login drawing, but the silhouette is the sketch's (wide at the bottom from
  the shoulder line) and a stranger would not call it a keykeeper from the shape
  alone. It is the weakest element on the screen and the first thing I would
  commission a real illustrator to redraw.
- **Ring-stack shadows are a stand-in for a blur.** At 3× density the falloff is
  smooth; on a low-density screen (mdpi) ten 1px rings can band slightly. A
  `RenderEffect` per surface would be the next step, at the cost of a view per
  surface.
- **No hardware frame was measured.** `assembleDebug` proves it compiles and the
  JVM tests prove the layouts draw, but 60fps during the 640ms transition, the
  haptic on redemption, and IME behaviour on a real phone are unverified.
- **Pre-existing dead resources remain** (`drawer_panel.xml`, `loader_card.xml`,
  `search_box.xml`, `menu_button.xml`, `drawer_item*.xml`, `splash_modal.xml`
  and seven unused icons). They were already unreferenced at `HEAD`; deleting
  them is unrelated cleanup and was left alone.
- **`~/.agents/skills` is outside the repo**, so the four skills used for this
  pass (ui-ux-pro-max, Hallmark, animate/review-animations, Ponytail) do not
  travel with the project; reinstall with `npx skills add <repo> -g`.

## 9. Frames

| Frame | What it verifies |
|---|---|
| `qa/screenshots/clay-page-390dp.png` | Signed-in page: crest, plaque, card, panel, both actions |
| `qa/screenshots/clay-drawer-390dp.png` | Floating clay slab, inset search, two rows, mint/pink chips |
| `qa/screenshots/clay-mid-390dp.png` | The handover at `phase 0.5` — no double exposure |
| `qa/screenshots/key-page-390dp.png` | The unauthenticated sheet is unchanged |
| `qa/screenshots/splash-390dp.png` | The cover is unchanged |

The first three are regenerated by `PageRenderTest` into
`app/build/reports/ui/` on every test run.
