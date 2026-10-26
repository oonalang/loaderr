# SHAI — a designer's notebook, brought to life

This file is the design system of record for the opening and unauthenticated
screens. The rendered frames in `qa/screenshots/` are the current truth; this
document explains the choices behind them.

## Direction

The app is a composed page of interface studies, not a themed form. Paper,
ruling, one contour drawing, one highlighter and handwritten notes are the
whole vocabulary. The drawing is authored, not decorative: the same lines carry
the identity into the signed-in court, where only the inks change.

Two states, one hand:

- **Sheet (before auth):** paper `#FDFCF7`, ruling `#C9DAF8`, ink `#1F2937`,
  pencil `#777B84`, highlighter `#FFF176`. Most of the screen is paper.
- **Court (signed in):** ink-dark surfaces with gold `#E9C77E` and lilac
  `#C2A3FF`, the same contours re-inked rather than redrawn.

## Palette

| Role | Value | Used for |
|---|---|---|
| Paper | `#FDFCF7` | page and toggle fills |
| Grid | `#C9DAF8` | 24dp ruling at 52% alpha, fades out in the court |
| Ink | `#1F2937` | linework, headlines, body, field outline |
| Pencil | `#777B84` | secondary copy, hatching, the ground line |
| Highlighter | `#FFF176` | primary action fill, headline sweep, the key's wash |

There is no sixth hue on paper. Emphasis is ink weight and the single
highlighter, which is why `sketch_draft` resolves to ink. Rules that are
"derived" colours must be documented here before they are used.

## Typography

- **Space Grotesk** is the functional voice: headings, controls, navigation,
  body. Bundled as static 400/500/700 faces behind `@font/space_grotesk`; the
  theme sets it, so no layout opts in individually.
- **Caveat** is the annotation voice and nothing else: one margin note with a
  real target.
- Body floor **16sp** on the sheet. Display 27–36sp with `-0.015em`-equivalent
  negative tracking; body tracking stays neutral. Sizes live in the layout, not
  in per-frame code, so authentication never reflows text.

## Illustration

`ShaiIllustrationView` inks the keykeeper with `Path` + `PathMeasure`: each
contour is one stroke, revealed by `getSegment` on a 0.54–0.90 timeline, with
slow-in/slow-out per stroke so the pen starts and stops with weight. Geometry is
authored in a 320 × 184 study space and scaled to fit, so it composes identically
on 360dp and 390dp phones.

- Line weights: 1.7 units for silhouette, 1.0 for detail/hatching, 2.4 for the
  key.
- Detail strokes use pencil; the key is the only object with extra weight and
  the only object the highlighter touches. That wash belongs to paper and fades
  out as the court arrives.
- The figure stands on a loose ground line; there is no frame, tape or sticker
  around it. It is drawn on the page, not pasted onto it.
- No texture bitmap, no gradient, no glow. The page is drawn at runtime
  (`LoaderBackdropView`, 24dp ruling, one pass per frame).

## Surfaces

- **Key field:** a stateful `SketchFieldDrawable` — four bowed edges that
  overshoot their corners plus a lighter second pass at a different offset.
  Bounds stay exact, so touch and accessibility remain honest; only the outline
  is imperfect. Focus thickens and darkens the stroke; disabled drops to 45%.
- **Primary action:** highlighter fill with the same double-pass contour;
  pressed state darkens, never scales the text.
- **Rules:** `OrnamentRuleView` draws from the centre outward once per entrance.
- Rectangles that are information (not controls) are separated by a single
  pencil rule, never a floating card with a shadow.

## Annotations

A handwritten note is allowed only with a genuine consequence and a real
target. The single note on the sheet sits directly above the Remember toggle,
reads "saved only if you tick this", and is connected by a drawn arrow that
lands on that toggle. Delete a note and its arrow together.

Removed in this pass: `SHAI / 01`, `SHAI / 02`, `SHEET 01`,
"The keykeeper's notebook", "Not yet verified", "Library access", "opens right
here", the bordered splash card, the taped-sketch strip, and every label that
only announced the style.

## Composition

- 390dp first; 360dp verified. 20dp page margins on the sheet, 32dp on the cover.
- One primary action per state: `Redeem key` before auth, then selection and
  preparation after it.
- Illustration → headline → caption → key field → mode note → action, in one
  reading order; the field's outline is the same ink as the drawing.
- 48dp minimum touch targets (56dp primary), `wrap_content` text heights, and a
  ScrollView that absorbs font scaling.
- Edge-to-edge on API 35: the page adds status-bar and cutout insets itself and
  pads for the IME; the field is scrolled above the keyboard on focus.

## Motion

One primary language: strokes reveal themselves.

| Moment | Token | Notes |
|---|---|---|
| Illustration | `ILLUSTRATION_MS = 720` | per-stroke stagger, `smoothstep` speed |
| Highlighter | `HIGHLIGHT_MS = 300` | one left-to-right clip, starts as the drawing lands |
| Sheet entrance | `ENTRANCE_MS = 320` | opacity + 14dp settle, once |
| Rule reveal | `RULE_REVEAL_MS = 360` | from the centre |
| Phase change | `PHASE_IN_MS = 520` / `PHASE_OUT_MS = 420` | ink blend, quicker on return |
| Press | `PRESS_IN_MS = 100` / `PRESS_OUT_MS = 160` | immediate, reversible |

Every animation can be cancelled by the next one and starts from the current
on-screen value (`ValueAnimator.ofFloat(phaseProgress, target)`), so an
interrupted phase change never jumps. With the system animation scale at zero,
each surface renders directly in its completed state: the illustration is
fully inked, the sweep full, the rule whole, controls visible. Nothing loops.

## Continuity into the court

The illustration view takes a `phase` value and blends every stroke:
ink → gold, pencil → lilac, paper wash → transparent. No bitmap swap, no second
artwork. The headline's highlighter becomes a quiet gold trace, the remember
toggle switches to the gold court box, and the sheet's 24dp ruling lifts as the
court's void arrives. The drawing is recognisably the same drawing.

## Verification

Rendered frames come from `PageRenderTest` (Robolectric, native graphics) at
390dp and 360dp; see `qa/screenshots/`. They are real Android rendering of the
real layouts, but not a hardware device: frame pacing, haptics and IME
behaviour remain unverified on hardware.
