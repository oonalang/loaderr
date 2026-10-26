---
name: apex-skill-router
description: "Project Apex routing for SHAI Loader (astral.loader, Android Views + Canvas). Coordinates ui-ux-pro-max (design authority), hallmark (design critic), animate + review-animations (motion authority), and ponytail (engineering restraint). Use when designing, building, auditing, animating, or simplifying SHAI UI, or when a request names any of those skills."
---

# Project Apex skill router

The four skill systems are installed globally in `~/.agents/skills/` and symlinked
into `~/.claude/skills/`. Read the target skill's own `SKILL.md` before applying it;
this file only routes between them and translates them to this project.

| Skill | Path | Role |
|---|---|---|
| ui-ux-pro-max | `~/.agents/skills/ui-ux-pro-max/SKILL.md` | Design authority |
| hallmark | `~/.agents/skills/hallmark/SKILL.md` | Independent design critic |
| animate | `~/.agents/skills/animate/SKILL.md` | Motion authority (build) |
| review-animations | `~/.agents/skills/review-animations/SKILL.md` | Independent motion review |
| ponytail | `~/.agents/skills/ponytail/SKILL.md` | Engineering restraint |

## Precedence

1. The user's explicit instruction for this task.
2. `DESIGN.md` — the design system of record — plus the existing implementation:
   `ShaiMotion`, `ShaiType`, `SketchSurfaces` / `PhaseSurfaceDrawable`,
   `ShaiIllustrationView`, and the current layouts.
3. The skills' mandatory rules, as translated for Android below.
4. The skills' optional style, palette or mood suggestions.

Never cut accessibility, security, validation, or a requested state. This is
Ponytail's own boundary, not an exception to it.

## Order of work

1. **Ponytail** — scope the smallest complete change; list every caller it touches.
2. **UI/UX Pro Max** — settle hierarchy, typography, palette, spacing, a11y.
3. **animate** — decide whether it animates, name the purpose, pick the Android tool.
4. **Implement**, reusing the existing drawables and motion tokens.
5. **Hallmark** — critique generic patterns, decoration, hierarchy.
6. **review-animations** — findings table plus Block / Approve.
7. **Ponytail** — prune before adding, remove what the task does not need.
8. Run the existing rendering tests and the accessibility checks below.

## Commands

```bash
# UI/UX Pro Max (Python 3 required; verified working)
python3 ~/.agents/skills/ui-ux-pro-max/scripts/search.py "<product or style>" --design-system
python3 ~/.agents/skills/ui-ux-pro-max/scripts/search.py "claymorphism" --domain style
python3 ~/.agents/skills/ui-ux-pro-max/scripts/search.py "animation" --stack jetpack-compose

# Hallmark web linter (HTML/CSS/JS only — never point it at Kotlin or XML)
node ~/.agents/skills/hallmark/scripts/sloplint.mjs <file-or-dir> --tier floor
```

`animate`, `review-animations` and `ponytail` are instruction-only skills; they have
no runnable scripts. Invoke them by naming the skill in the request.

## Android translation (mandatory)

The project is `astral.loader`: minSdk 28, compile/target 35, XML views with custom
`View` + `Canvas` rendering, `viewBinding = false`, no Compose, and no new
dependencies without an explicit request.

- **Tokens:** web `:root` variables map to `res/values/colors.xml`, `dimens.xml`,
  `styles.xml`, and the existing `ShaiType` / `ShaiMotion` constants. Never create a
  second token system beside them.
- **Clay material:** follow the `DESIGN.md` lighting recipe — stacked rounded rects in
  the view's reserved inset, matte face, one 135° bevel. Do not substitute
  `RenderEffect` or `BlurMaskFilter`; `DESIGN.md` explains why they are rejected.
- **CSS transitions:** `ValueAnimator` / `ViewPropertyAnimator` over `translationX/Y`,
  `scaleX/Y`, `alpha`, `rotation`. Animate from the current on-screen value and
  cancel the previous animator, as `DESIGN.md` specifies.
- **Easing and duration:** `PathInterpolator` tokens from `ShaiMotion` only. Do not
  invent a curve or fork the duration scale. A deliberate exception gets recorded in
  `DESIGN.md` first.
- **transform-origin:** `pivotX` / `pivotY` or the anchor rect for trigger-anchored
  popups; never centre for those. Modals stay centred.
- **prefers-reduced-motion:** `ValueAnimator.areAnimatorsEnabled()` (API 26+) and the
  system animator duration scale. Render the completed state; do not merely shorten.
- **Hover and focus:** touch has no hover; use pressed and focused states, and
  `View.isHovered` for pointer devices. `:focus-visible` means an always-visible
  keyboard/accessibility focus ring (the 2dp ring in `DESIGN.md`).
- **Web-only rules — do not apply:** `overflow-x: clip`, `:root`, `1280 x 800` hero,
  "80 words per screen", `16px` body. Translate to `390dp`/`360dp`, `16sp` body,
  primary action visible without scrolling, no clipping at font scale.
- **Hallmark web artifacts — do not run or apply:** `sloplint`, `theme.mjs`, the
  Hallmark stamp, `.hallmark/` logs. Use its judgement from `references/gates.md`,
  `references/craft.md`, `references/anatomy.md` and `references/layout.md`.
- **UI/UX Pro Max's `claymorphism-mobile` row is React Native/Expo** (nested `View`,
  Reanimated, `BlurView`). Translate it to nested draw layers, `ValueAnimator`, and
  `HapticFeedbackConstants`; do not add Compose, Reanimated, or `BlurView`.

## SHAI visual direction

Before authentication the app is a Conceptual Sketch; after authentication it is
Claymorphism. One `phase` float (0 → 1) drives both, and the transformation must read
as an unfinished illustration becoming a finished, tangible object. The silhouette,
reading order and single primary action per state survive unchanged; only the
material changes.

`DESIGN.md` already implements this direction with the paper/clay palettes, four
fonts, lighting recipe and motion table. Treat the skills as critique and refinement
of that system, not as a greenfield redesign. Any palette, typography or material
change updates `DESIGN.md` first.

## Conflict rules

- **Hallmark vs UI/UX Pro Max:** Hallmark can reject generic decoration and weak
  hierarchy; UI/UX may not reintroduce a pattern Hallmark flagged as slop.
  `DESIGN.md` outranks both.
- **animate vs review-animations:** the ten standards in `review-animations` decide.
  A justified exception (for example the 640ms material change, which is rare and
  interruptible) must be recorded in `DESIGN.md`, not argued ad hoc.
- **Ponytail vs design fidelity:** Ponytail cuts code, dependencies and rewrites, never
  the specified material, accessibility, validation, or a requested state. It reuses
  `PhaseSurfaceDrawable` and the existing animators before adding anything.
- **UI/UX vs motion:** UI/UX owns hierarchy and palette; `animate` owns timing and
  curves. UI/UX must not assign ad-hoc durations.
- **Rejected unless the user explicitly asks:** a new dependency, a Compose migration,
  a second layout for the signed-in state, or a parallel design-token system.

## Definition of done

- `./gradlew testDebugUnitTest` passes (`PageRenderTest`, `ClaySurfaceProbeTest`).
- Verified at 390dp and 360dp, with enlarged font scale and with animation scale 0.
- TalkBack labels present, 48dp minimum touch targets, focus visible.
- The report names which skill made which decision, and any finding waived.
