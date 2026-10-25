# SHAI — The keykeeper's notebook

## Evidence and scope
Native Android Views, not Compose: `activity_main.xml` is a scrolling form and overlay library drawer. `astral/MainActivity.kt` binds IDs and owns authentication, selection, preparation and phase styling. `LoaderVisuals.kt` supplies Canvas backdrops, stateful surface drawables and rules. Vector XML supplies the art. SplashActivity and AboutDialog are separate entry/help surfaces. AGP 8.9.1, Kotlin 2.0.21, SDK 35, minimum API 28; existing Gradle wrapper and JUnit tests retained.

No reference sketch was attached to this conversation. The character artwork is an original interpretation; matching the user's reference remains blocked. No screenshot or device render has been inspected.

## Source-based design audit
- `drawable/hero_sketch.xml`: faceless circular bust reads as a placeholder rather than an illustration with a subject.
- `drawable/hero_ethereal.xml`: unrelated crown/gem replaces the bust; identity is lost after authentication.
- `LoaderVisuals.kt / LoaderBackdropView`: random stars, radial glows, guilloche rings and double frames compete with the actual task. Percentage-positioned arrows cannot track form fields when content scrolls.
- `LoaderVisuals.kt / PhaseSurfaceDrawable`: almost every surface receives gilded double borders and corner ticks, flattening hierarchy.
- `activity_main.xml / hero_block`: 104dp thumbnail plus narrow text column relegates the drawing to an icon; casual typeface is a system fallback, not hand lettering.
- `MainActivity.updateTheme`: 26→31sp and casual→serif changes cause avoidable text reflow at the state boundary.
- `AboutDialog.applyPhaseStyle`: all text ≥13sp becomes bold display type, erasing body hierarchy.
- `activity_about.xml`: generic promotional copy and a noninteractive chevron promise information that is not actionable.
- Previous DESIGN.md claimed no halos despite explicit halo rendering. This document supersedes that claim.

## One direction
An illustrated notebook, not a dashboard or fantasy badge collection. An asymmetric portrait of a cloaked keykeeper holding a physical key anchors the page. Visible facial features, hair strands, fabric folds and hatching give it a specific subject. The same paths persist in the night-ink authenticated state; only inks change.

### Composition
Single scroll column, 20dp outer margins. Full-width 184dp illustration above left-aligned heading and explanatory text. Header is a quiet folio. Authentication and library operations retain their existing gates. No decorative overlays cross the form. Background uses sparse deterministic paper grain and edge binding ticks only. Rows and success information use single rules; input and primary action remain enclosed. No floating pills, halos or nested ornamental frames.

### Typography
Platform serif bold for display, 28sp hero and 25sp brand in BOTH states. Sans-serif body 14sp, labels 12sp, metadata 11–12sp. Monospace is reserved for folio/data. Display tracking 0; short labels at most .10em. Body is never universally bolded. Platform fonts require no network or bundled font dependency; OEM rendering can vary.

### Color
Paper #F7F2E7, paper surface #FDF9F0, ink #211C24, secondary #6F6270, annotation violet #6A4A8C. Night paper #0A0610, surfaces #160C22, secondary #BCADD1, lettering #F6EFFF, gold #E9C77E. Violet is an illustration ink, not a glow. Gold belongs to the physical key, primary action and selected state. Status retains text as well as color. Keep existing semantic status colors.

### Spacing / interaction
4dp base rhythm: 8 within small groups, 12–16 between related elements, 24 between sections. Controls at least 48dp high, content-driven text height and scrolling. Hero is decorative to accessibility services; task text announces state. Press/focus/disabled states must remain visible.

### Motion
Retain existing named motion and reduced-motion gates: press 100/160ms, entrance 320ms, phase 520/420ms, drawer 300/220ms. No ambient animation or shimmer. Authentication changes inks and reveals controls; display metrics remain stable. No network, licensing, preparation, persistence or injection behavior is changed for visual effect.

## Verification policy
XML parsing and source contracts are not device tests. Build, lint, JUnit and device results are reported separately in `qa/REDESIGN_REPORT.md`. Reference fidelity, font scaling, TalkBack and visual approval require the missing sketch and an Android device/emulator.
