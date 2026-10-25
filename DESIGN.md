# DESIGN.md — SHAI Loader

Single source of truth for the loader's design system. Skill of record:
`samber/cc-skills@frontend-design-deslop` (4.6K installs, MIT). The longer
narrative record lives in `qa/RELEASE_REPORT.md`; if CSS/layout ever drifts
from this file, this file wins.

## Discovery

- **Artifact:** Android mobile app — library-access loader. An auth flow plus
  an operational tool, not a landing page. Layout grammar: one scrolling
  column, one primary action, controls that appear when they become relevant.
- **Audience:** phone users redeeming a panel-issued key, then choosing,
  preparing and injecting exactly one game library.
- **Primary outcome:** redeem → choose one library explicitly → prepare → inject.
- **Committed adjectives:** drafted, gilded, deliberate, dense, ceremonial.
- **Aesthetic essence:** *pencil concept sheet → maximal violet court.*
- **Signature move:** the sheet is literally a drawing of the signed-in
  screen. One line drawing becomes the gilded crest; the sheet's ghosted
  frame becomes the real frame.

## Why violet and ivory are not slop here

The checklist flags the indigo/violet band and cream backgrounds as
convergence tells *by reflex*. Both are brief-driven here: the violet court
was specified by the product owner, and the ivory paper is phase one of a
documented two-phase system (see `qa/RELEASE_REPORT.md`). Committed choices
with a stated rationale are the opposite of reflex; sampled defaults would be.

## Typography

- Display: `casual` hand lettering on the sheet → `serif` bold in the court.
- Body and labels: tracked sans; package data monospace in the court.
- Scale: sheet headline 26sp → court 31sp; control labels 11–13sp.
- **Floor: no text below 11sp anywhere in `res/layout`** (enforced by
  `qa/verify_ui_contract.py`). Sentences get 12–14sp.
- All-caps only for short labels with tracking; never for sentences.

## Color (tokens: `colors.xml`, `ShaiPalette`, `LoaderVisuals`)

| Role | Value |
|---|---|
| paper (phase-1 bg) | `#F7F2E7` |
| paper card | `#FDF9F0` |
| ink | `#211C24` |
| pencil | `#6F6270` |
| draft violet | `#6A4A8C` |
| court void (phase-2 bg) | `#0A0610` |
| court plum | `#241232` |
| court amethyst | `#3A1F57` |
| gold accent | `#E9C77E` |
| deep gold | `#9D743A` |
| orchid | `#E2C7FF` |
| mint / active | `#58E2AA` |

- Distribution ≈ 60-30-10: one dominant field (paper or void), plum surfaces,
  gold reserved for structure and the primary action.
- Near-black is `#0A0610`, never `#000`; light field is ivory, never `#FFF`.
- Meaning is never color-alone: a ticked box always carries its label.

## Spacing and shape

- Sketch contours 3dp; court panels 7dp; primary action 9dp; mode boxes 18dp
  with a 4dp border.
- Touch targets ≥ 48dp (spinner rows, mode boxes, checkbox rows).
- Rhythm by relationship: 6–16dp inside a group, 18–26dp between sections.

## Motion (tokens: `ShaiMotion.kt`)

- Feedback: press in/out 100/160ms; enter on `PathInterpolator(0.23, 1, 0.32, 1)`.
- Entrance 320ms, rule reveal 360ms, drawer 300/220ms, row stagger 35ms
  capped at 12 rows, activity handoff 220/180ms.
- One rare long moment: paper→court 520ms in / 420ms out; the plaque is
  revealed by that timeline and the drawer opens 760ms later.
- Only transform and opacity animate; every path honours
  `ValueAnimator.areAnimatorsEnabled()`.

## Components and states (craft layer)

- **Ranking:** one filled primary action (`login_button`); secondary actions
  are outlined or text-level.
- **Text input:** visible label, inline error that keeps the typed value, and
  a rejected key returns focus to the field.
- **Dropdowns (method, delay):** 50dp bordered phase surface with a gold
  chevron (`ic_chevron_down`), `spinnerMode="dropdown"`, dark popup — the
  affordance is drawn, not implied.
- **Mode control:** two labelled square boxes (`checkbox_court`) — an 18dp
  box with a 4dp gold border — where exactly one is ticked: checkbox look,
  radio semantics. Persisted as `injection_mode`; `LoaderPreferences.isAutoMode()`
  is the source of truth when injecting.
- **States:** checked, unchecked, pressed, focused and disabled are all drawn.
  Disabled controls drop to 0.45 alpha through `setInjectionControlsEnabled`.
- **Gating:** `library_card`, `operation_section` (mode + delay + method) and
  `browse_button` start `gone` and are revealed only after the key is
  verified; the unauthenticated sheet contains only the key form, the
  remember toggle, the primary action and the footer.
- **Empty / loading / error:** drawer empty and no-results states,
  VERIFYING → CHOOSE → PREPARE → INJECT button copy, inline key errors, and
  a no-libraries plaque state.
- **Accessibility:** ≥48dp targets, visible focus rings on every phase
  surface, content descriptions on both mode boxes, reduced-motion fallbacks
  throughout.

## Slop audit (frontend-design-deslop checklist)

| Tell | Result |
|---|---|
| Violet/indigo accent by reflex | PASS — brief-demanded, documented above |
| Cream background by reflex | PASS — committed phase-1 paper |
| Gradient text, glow halos, glassmorphism | PASS |
| Text below the 11px floor | **FAIL → fixed** — 22 labels raised; QA now enforces the floor |
| Controls with no affordance | **FAIL → fixed** — spinners had no visible chevron; added |
| Hidden or inert controls | **FAIL → fixed** — 1dp radio stub replaced by visible mode boxes; controls stayed disabled after login; now enabled on success and dimmed when disabled |
| Rest-only components | PASS — full state matrix drawn |
| Reduced motion ignored | PASS — all paths branch on animator settings |
| Targets under 44dp | PASS — 48dp rows |
| Signature move present | PASS — sheet → court |
| Layout-defect and build-correctness items needing a render | NOT RUN — no device/emulator attached |

Changelog: 2026-10-25 — signed-in control gating, visible mode boxes,
dropdown chevrons, 11sp type floor, disabled-state treatment, audit fixes.
