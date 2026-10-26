# SHAI — the sketch becomes the sculpture

This file is the design system of record. The rendered frames in
`qa/screenshots/` are the current truth; this document explains the choices
behind them.

## Direction

The app opens as a composed page of interface studies — paper, ruling, one
contour drawing, one highlighter, handwritten notes — and then that same page is
*made*. Nothing is swapped out for a different product: the drawing is the
blueprint, and signing in sculpts it out of matte pastel clay. The silhouette,
the reading order and the one primary action per state survive the change
unchanged; only the material does.

- **Sheet (before auth):** paper `#FDFCF7`, ruling `#C9DAF8`, ink `#1F2937`,
  pencil `#777B84`, highlighter `#FFF176`. Most of the screen is paper.
- **Clay (signed in):** pale lavender `#F4EEFF`, one ink `#2B2340`, raised
  `#FAF7FF`, and three pastels — pink `#FFB8D9`, mint `#A8E6CF`, periwinkle
  `#B5C7FF` — moulded with a single upper-left light.

Both states are one layout driven by one `phase` float (0 → 1). There is no
second activity, no second layout, and no cross-faded pair of screenshots.

## Palette

### Sheet

| Role | Value | Used for |
|---|---|---|
| Paper | `#FDFCF7` | page and toggle fills |
| Grid | `#C9DAF8` | 24dp ruling at 52% alpha, drawn out before the clay sets |
| Ink | `#1F2937` | linework, headlines, body, field outline |
| Pencil | `#777B84` | secondary copy, hatching, the ground line |
| Highlighter | `#FFF176` | primary action fill, headline sweep, the key's wash |

There is no sixth hue on paper. Emphasis is ink weight and the single
highlighter, which is why `sketch_draft` resolves to ink.

### Clay

| Role | Value | Used for |
|---|---|---|
| Ground | `#F4EEFF` | the page behind every surface |
| Ink | `#2B2340` | headings, body, carved features, shadows (at low alpha) |
| Muted | `#5F536F` | supporting copy, metadata, package names |
| Raised | `#FAF7FF` | cards, panel, row faces, face plane |
| Sunken | `#E9E1F7` | inset wells: search, both dropdowns, check boxes |
| Pink | `#FFB8D9` | primary action, the crest's clay, the mark's tile |
| Mint | `#A8E6CF` | verified state, the clay key, ACTIVE chips |
| Periwinkle | `#B5C7FF` | secondary action, the selected row, neutral chips |

`clayPinkDeep #EE98C5` and `clayPeriDeep #9BB0F5` are same-hue tonal steps used
only for rims, grooves and rings; they are never used as a fill or as text.
Gold, lilac, violet and the near-black court are retired — no court token
survives in code.

### The lighting recipe

Every raised surface shares one light, from the upper left:

1. **Stack shadow** inside the view's reserved inset: ten concentric rounded
   rects, alpha falling off quadratically (`clayInk` at ~15%). Drawn, not
   filtered — `BlurMaskFilter` is not dependable on a hardware canvas and a
   `RenderEffect` would need a separate view per surface.
2. **Matte face**, fully opaque.
3. **Bevel**: a single 135° `LinearGradient` (white `.62` → transparent →
   `clayInk` `.20`) stroked on the face edge and clipped to it, so only the
   inner half shows. Top-left catches light, bottom-right falls into shade.

The inset is never smaller than the shadow's reach; otherwise the falloff is cut
off flat at the view edge and the clay turns into a sticker. That is why the
primary action is 68dp tall for a 52dp face, and why the logo tiles and rows
carry real padding.

**Recessed controls invert the recipe.** Search and both dropdowns cast no
shadow at all and are lit from the bottom-right instead, with a darker inset
face. Raised = you can press it; recessed = you type in it. This one rule keeps
the interface from being a screen full of identical rounded rectangles.

## Typography

- **Space Grotesk** (static 400/500/700) sets the sheet: drafting, functional.
- **Fredoka SemiBold** sets clay display: the wordmark, `Access granted.`,
  section titles, the drawer title, library names.
- **Nunito** sets everything you read or press in clay — body, captions,
  buttons, chips — with a Semibold for labels and controls.
- **Caveat** is the annotation voice and appears on paper only. It never
  appears in clay.

Static instances are cut from the variable fonts at build-prep time and shipped
in `res/font`; all four faces resolve through `ShaiType`, so a screen can never
introduce a fifth. `applyTypefaces()` is the single point where the voice
changes, alongside the colour blend.

Floors: body 16sp (17sp on the sheet's caption), supporting 14sp, labels 13sp,
chips 12sp, display 27–36sp. Sizes live in the layout, not in per-frame code.

## Illustration

`ShaiIllustrationView` is one piece of geometry with two renderings.

**On paper** it inks the keykeeper with `Path` + `PathMeasure`: each contour is
revealed by `getSegment` on a 0.54–0.90 timeline with slow-in/slow-out, so the
pen starts and stops with weight. Silhouette 1.7, detail 1.0, key 2.4. Detail
and hatching are pencil; the key is the only object with extra weight and the
only object the highlighter touches.

**In clay** the same 320 × 184 study space is sculpted:

- the silhouette fills with matte pink clay (`clayPink` → `clayPinkDeep`
  diagonal gradient) with a clipped white rim light;
- the face is re-authored for the medium — a front-facing plane inside the hood
  at 55% raised-pink, because a filled *profile* reads as a mask at 184dp;
- features are carved grooves (`clayPinkDeep` + a white lower lip), not ink;
- hatching, the ground line and both contour passes are drawn *out*, and a soft
  contact shadow takes the ground line's place;
- the key inflates into a mint object: bow, straight stem, one tooth, plus its
  own highlight and drop.

Hatching and contour leave before the form arrives (`clay × 3.1` for the
silhouette, `× 2.6` for detail, form starting at `phase .34`). Without that
stagger the midpoint of the transition is a double exposure with two faces on
one head; with it, the stage reads as a smudge of clay resolving into form.

## Surfaces

All of them are one drawable, `PhaseSurfaceDrawable`, blending between a ruled
paper edge and a moulded clay form. Roles: `CARD`, `PRIMARY`, `SECONDARY`,
`SUCCESS`, `DRAWER`, `SEARCH`, `SELECT`, `ROW`, `PLAQUE`.

| Role | Face | Radius | Inset | Depth |
|---|---|---|---|---|
| Card / settings panel | raised | 30dp | 9dp | shadow + bevel |
| Primary action | pink | 22dp | 8dp | shadow + bevel |
| Secondary action | periwinkle | 20dp | 8dp | shadow + bevel |
| Verified plaque | mint | 26dp | 9dp | shadow + bevel |
| Drawer slab | raised | 28dp | — | view elevation, outline-shaped |
| Search / dropdowns | sunken | 16dp | 3dp | inner light only |
| Library row | raised (periwinkle when selected) | 18dp | 9dp | shallow shadow |

- **Key field:** a stateful `SketchFieldDrawable` — four bowed edges that
  overshoot their corners plus a lighter second pass. On paper it is drawn;
  in clay it becomes a rounded well with an inset shadow. Bounds stay exact, so
  touch and accessibility remain honest; only the outline is imperfect.
- **Pressed:** the surface state (not a tint overlay) sinks the face 1.2dp,
  cuts the shadow to a third and deepens the face colour. The view itself
  compresses to 0.975 on the same 100/160ms tokens.
- **Focused:** a 2dp ring in `clayPeriDeep` (ink on the pink action) inside the
  surface, always visible for keyboard and switch access.
- **Rules:** `OrnamentRuleView` draws from the centre outward once per entrance,
  then leaves the layout entirely in clay rather than leaving a gap.
- **Chips:** mint = ready/active, periwinkle = choose/neutral, pink =
  maintenance. They are restyled once per material change, not per frame.

## Composition (clay)

Reading order: wordmark → crest → headline → caption → verified plaque →
library card → injection panel → primary action → secondary action → footer.
One primary action per state, and it is always the last loud thing before the
footer.

- The crest, the headline and the plaque form the top third; the card and panel
  are the working surface below it.
- Two raised surfaces never touch: 18–22dp of ground between them, so each
  shadow has somewhere to fall.
- The library card is the only surface with a pastel tile inside it (the mark),
  which is why it reads as the most important object after the action.
- 390dp first; 360dp verified. 20dp page margins, 22dp card padding.
- 48dp minimum targets (68dp primary), `wrap_content` text heights, ScrollView
  absorbs font scaling, and the field scrolls above the IME.
- Edge-to-edge on API 35: the page adds status-bar and cutout insets itself.
  Both materials are light surfaces, so system bars always take dark icons.

## Motion

One primary language: strokes reveal themselves on paper; on clay the form
rises into the same silhouette.

| Moment | Token | Notes |
|---|---|---|
| Illustration | `ILLUSTRATION_MS = 720` | per-stroke stagger, `smoothstep` speed |
| Highlighter | `HIGHLIGHT_MS = 300` | one left-to-right clip, starts as the drawing lands |
| Sheet entrance | `ENTRANCE_MS = 320` | opacity + 14dp settle, once |
| Rule reveal | `RULE_REVEAL_MS = 360` | from the centre |
| Material change | `PHASE_IN_MS = 640` / `PHASE_OUT_MS = 420` | the one rare, long moment; quicker on return |
| Verified plaque | driven by `phase` | rises and settles with the material, not on its own timer |
| Drawer | `DRAWER_OPEN_MS = 300` / `DRAWER_CLOSE_MS = 220` | rows follow in a capped stagger |
| Press | `PRESS_IN_MS = 100` / `PRESS_OUT_MS = 160` | immediate, asymmetric, reversible |

Every animation can be cancelled by the next and starts from the current
on-screen value (`ValueAnimator.ofFloat(phaseProgress, target)`), so an
interrupted change never jumps. With the system animation scale at zero, each
surface renders directly in its completed state — the illustration fully inked,
the sweep full, the rule whole, controls visible, the page already clay. Nothing
loops, and no animation gates a state: `licenseVerified` decides, the animator
only follows.

The drawer waits `DRAWER_REVEAL_DELAY_MS = 880` — longer than the material
change — so the finished object is legible before a panel slides over it.

## Continuity between the states

The illustration view takes the same `phase` value as every surface:

- contours fade as the filled form arrives, in the same place on the page;
- the pink clay of the crest is the highlighter's yellow *made solid*, and the
  mint key is the one object the marker ever touched;
- the paper's 24dp ruling lifts as the clay's lavender arrives, so the blueprint
  is visibly underneath the finished object rather than beside it;
- the same wordmark, the same reading order, the same single action.

## Verification

Rendered frames come from `PageRenderTest` (Robolectric, native graphics) at
390dp, including the signed-in page, the library drawer with two rows, and the
mid-transition frame; see `qa/screenshots/`. `ClaySurfaceProbeTest` checks the
material itself — corners, shadow, opacity — because a drawable bug is invisible
in source review. These are real Android rendering of the real layouts, but not
a hardware device: frame pacing, haptics and IME behaviour remain unverified on
hardware.
