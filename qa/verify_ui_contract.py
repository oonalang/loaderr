"""Source-level checks only. They do not replace running the Android APK."""
from pathlib import Path
import re
from xml.etree import ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
APP = ROOT / 'app/src/main'
KOTLIN = (APP / 'kotlin/astral/MainActivity.kt').read_text()
SPLASH = (APP / 'kotlin/astral/SplashActivity.kt').read_text()
BASE = (APP / 'res/layout/activity_main.xml')


def inside(a: str, b: str) -> str:
    return KOTLIN.split(a, 1)[1].split(b, 1)[0]


def check(condition: bool, label: str):
    if not condition:
        raise AssertionError(label)
    print('PASS:', label)


# Both states use the same layout. All referenced IDs are unique.
for path in APP.rglob('*.xml'):
    ET.parse(path)
print('PASS: all Android XML files parse')

# AAPT accepts missing animation target names and properties unsupported by a
# vector path. Android resolves these only when the animation starts. Validate
# the resource relationship here, before the hardware-rendered launch check.
animatable_properties = {
    'vector': {'alpha'},
    'group': {'rotation', 'pivotX', 'pivotY', 'scaleX', 'scaleY', 'translateX', 'translateY'},
    'path': {'pathData', 'fillColor', 'strokeColor', 'strokeWidth', 'strokeAlpha',
             'fillAlpha', 'trimPathStart', 'trimPathEnd', 'trimPathOffset'},
    'clip-path': {'pathData'},
}
android = '{http://schemas.android.com/apk/res/android}'
for path in sorted((APP / 'res/drawable').glob('*.xml')):
    animated = ET.parse(path).getroot()
    if animated.tag != 'animated-vector':
        continue
    vector_ref = animated.get(android + 'drawable', '')
    check(vector_ref.startswith('@drawable/'), f'{path.name}: vector reference resolves locally')
    vector = ET.parse(APP / 'res/drawable' / (vector_ref.split('/')[-1] + '.xml')).getroot()
    targets = {node.get(android + 'name'): node.tag for node in vector.iter()
               if node.get(android + 'name')}
    for target in animated.findall('target'):
        name = target.get(android + 'name')
        check(name in targets, f'{path.name}: target {name} has a matching android:name')
        animators = list(target.iter('objectAnimator'))
        animation_ref = target.get(android + 'animation')
        if animation_ref:
            resource_type, resource_name = animation_ref.removeprefix('@').split('/')
            animation = ET.parse(APP / 'res' / resource_type / (resource_name + '.xml')).getroot()
            animators.extend(animation.iter('objectAnimator'))
        check(bool(animators), f'{path.name}: target {name} has an animator to validate')
        for animator in animators:
            prop = animator.get(android + 'propertyName')
            check(prop in animatable_properties.get(targets[name], set()),
                  f'{path.name}: {prop} is supported by {targets[name]} target {name}')

tree = ET.parse(BASE)
ns = '{http://schemas.android.com/apk/res/android}'
ids = [node.attrib[ns + 'id'] for node in tree.iter() if ns + 'id' in node.attrib]
check(len(ids) == len(set(ids)), 'view IDs unique')
required = {'hero_block','hero_art','license_section','success_section','library_card','login_button','library_drawer', 'change_key_link'}
check(required <= {item.split('/')[-1] for item in ids}, 'both phases retain essential views')

# Explicit selection: no hidden Spinner selection callback and no automatic
# library fetch inside redemption or catalog population.
spinner = inside('    private fun updateSpinnerWithFilteredData()', '    fun handleSelectedJsonData')
check('onItemSelectedListener = null' in spinner, 'hidden spinner callbacks disabled')
check('applyLibrarySelection' not in KOTLIN, 'no implicit spinner selection')
redeem = inside('    private fun validateLicense(', '    private fun showToast(')
check('proceedWithDownload()' not in redeem, 'no download initiated by key redemption')
check('verifySelectedLibraryAndDownload()' not in redeem, 'no library preparation initiated by key redemption')
select = inside('    fun handleSelectedJsonData(', '    private fun verifySelectedLibraryAndDownload()')
check('proceedWithDownload()' not in select and 'verifySelectedLibraryAndDownload()' not in select,
      'selecting a library does not download it')
primary = inside('        loginButton.setOnClickListener {', '        appBanner.setOnLongClickListener {')
check('verifySelectedLibraryAndDownload()' in primary and 'licenseVerified' in primary,
      'explicit preparation follows the primary action')
check('selectedLibrary.name.isBlank()' in primary, 'preparation checks explicit library selection')

# Visual phases and reduced motion are represented in the implementation.
ILLUSTRATION = (APP / 'kotlin/astral/ShaiIllustrationView.kt').read_text()
check('class ShaiIllustrationView' in ILLUSTRATION and 'PathMeasure' in ILLUSTRATION and
      'getSegment' in ILLUSTRATION,
      'one illustration, stroke-revealed with PathMeasure')
check('ValueAnimator.areAnimatorsEnabled()' in KOTLIN,
      'transition respects Android animator setting')
check('startFakeLoading()' not in SPLASH, 'removed simulated splash percentage')
check('R.id.change_key_link' in KOTLIN, 'authenticated state includes key-switch recovery')

# Restored controls: method and delay must be real, visible selectors, not
# 1dp compatibility stubs.
by_id = {}
for node in tree.iter():
    key = node.attrib.get(ns + 'id')
    if key:
        by_id[key.split('/')[-1]] = node
for control in ('delay_spinner', 'method_spinner'):
    node = by_id.get(control)
    check(node is not None, f'{control} exists')
    check(node.attrib.get(ns + 'visibility', 'visible') != 'gone', f'{control} is visible')
    check(node.attrib.get(ns + 'layout_height') not in ('1dp', '1px'),
          f'{control} has a real touch height')

# Opt-in remember-key autofills and runs the ordinary redemption flow.
prefs = (APP / 'kotlin/astral/LoaderPreferences.kt').read_text()
check(all(name in prefs for name in ('getRememberedKey', 'saveRememberedKey', 'clearRememberedKey')),
      'remembered key is stored in preferences')
check('R.id.remember_key' in KOTLIN and 'R.id.remember_title' in KOTLIN and
      'remember_key' in BASE.read_text(),
      'remember-key toggle is wired')
remember = inside('    private fun maybeAutoLogin()', '    private fun beginLicenseRedemption()')
check('licenseKeyInput.setText(key)' in remember, 'saved key autofills the field')
check('beginLicenseRedemption()' in remember, 'saved key starts the normal redemption flow')
check('proceedWithDownload' not in remember and 'verifySelectedLibraryAndDownload' not in remember,
      'auto login never downloads or prepares a library')
check('autoLoginStarted' in KOTLIN, 'auto login is one-shot per launch')
change = inside('findViewById<View>(R.id.change_key_link).setOnClickListener {',
                'loginButton.setOnClickListener {')
check('forgetRememberedKey()' in change, 'switching keys clears the remembered key')
check('saveRememberedKey(licenseKey)' in redeem, 'accepted key is persisted for the next launch')

# The redesign deliberately shares composition across paper and night inks.
visuals = (APP / 'kotlin/astral/LoaderVisuals.kt').read_text()
visuals_illustration = (APP / 'kotlin/astral/ShaiIllustrationView.kt').read_text()
type_file = (APP / 'kotlin/astral/ShaiType.kt').read_text()
check('ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.clayBg, phase)' in visuals,
      'paper and clay backgrounds remain phase-aware')
def palette_line(name):
    for line in visuals.splitlines():
        s = line.strip()
        if s.startswith(f'val {name} ') and ('get()' in s or s.startswith(f'val {name} =')):
            return s
    return ''
for token, rgb in (('clayBg', '244, 238, 255'), ('clayInk', '43, 35, 64'),
                   ('clayPink', '255, 184, 217'), ('clayMint', '168, 230, 207'),
                   ('clayPeri', '181, 199, 255'), ('clayRaised', '250, 247, 255'),
                   ('clayMuted', '95, 83, 111')):
    check(f'Color.rgb({rgb})' in palette_line(token),
          f'clay token {token} matches the palette')
check(all(f'ShaiPalette.{old}' not in KOTLIN for old in ('void', 'gold', 'orchid', 'darkPanel')),
      'the violet court is no longer referenced by the screen')
check(all(term not in visuals for term in ('RadialGradient', 'drawCornerScroll', 'guilloche', 'sheen')),
      'notebook removes halos, ornamental frames and shimmer')
check('if (ruled)' in visuals and 'state_pressed' in visuals and 'state_focused' in visuals,
      'ruled surfaces preserve pressed and focus rendering')
check('hero_rule' not in KOTLIN and 'hero_rule' not in BASE.read_text(),
      'the decorative rule under the wordmark is gone')
check('OrnamentRuleView' not in KOTLIN and 'OrnamentRuleView' not in visuals,
      'the retired ornament rule is not referenced or shipped')
check('heroIllustration.phase = materialProgress' in KOTLIN,
      'the crest is the same drawing re-inked by the phase blend')
check('hero_sketch' not in KOTLIN and 'hero_ethereal' not in KOTLIN,
      'no bitmap art swap remains')
check(by_id['hero_art'].attrib.get(ns + 'layout_width') == 'match_parent',
      'illustration is full width rather than a thumbnail')
check(by_id['hero_art'].attrib.get(ns + 'importantForAccessibility') == 'yes' and
      bool(by_id['hero_art'].attrib.get(ns + 'contentDescription')),
      'the interactive illustration announces its reaction affordance')
check('android:textSize="30sp"' in BASE.read_text() and 'TypedValue' not in KOTLIN,
      'display size is fixed in the layout, not resized at runtime')
check(by_id['drawer_close'].attrib.get(ns + 'layout_height') == '48dp',
      'drawer close has a 48dp touch target')

# Motion is a named system with short feedback and one deliberate phase change.
motion_path = APP / 'kotlin/astral/ShaiMotion.kt'
check(motion_path.is_file(), 'motion tokens live in one file')
motion = motion_path.read_text()
check('PathInterpolator' in motion, 'motion uses explicit easing curves')
check('PRESS_IN_MS = 100L' in motion, 'press duration is an intentional token')
phase_ms = int(re.search(r'PHASE_IN_MS = (\d+)L', motion).group(1))
check(500 <= phase_ms <= 750, f'the material change sits in the 500-750ms window ({phase_ms}ms)')
check('DRAWER_OPEN_MS' in motion and 'ROW_STAGGER_MS' in motion,
      'drawer and row staging are tokenised')
check('AccelerateDecelerateInterpolator' not in KOTLIN and
      'DecelerateInterpolator' not in KOTLIN,
      'no legacy ad-hoc interpolators remain in the main screen')
press_obj = (APP / 'kotlin/astral/InteractionMotion.kt').read_text()
check('installPressFeedback(' in KOTLIN and press_obj.count('.975f') >= 1 and
      'InteractionMotion.press' in KOTLIN,
      'primary controls have press feedback')
check('animateDrawerRows()' in KOTLIN and 'ROW_STAGGER_MS' in KOTLIN,
      'drawer rows are staged behind the panel')
success_reveal = inside('        val successSection = cv(R.id.success_section)',
                        '        phaseColorMatrix.setSaturation(phaseProgress)')
check('successSection.alpha = reveal' in success_reveal and
      'translationY' in success_reveal,
      'signed-in plaque is revealed by the phase timeline')
check('browse_button' not in KOTLIN and 'library_menu_button' not in KOTLIN,
      'the removed list button and menu control have no surviving handlers')
check('browse_button' not in BASE.read_text() and 'library_menu_button' not in BASE.read_text(),
      'the removed list button and menu control have no layout space')
check('setLibraryDrawerOpen(true)' not in inside(
          '                    // The verified state never opens or selects anything.',
          '                }\n            }'),
      'authentication never opens the library drawer by itself')
check('HapticFeedbackConstants.CONFIRM' in KOTLIN,
      'verified state gives a confirmation haptic')
check('revealKeyEntry()' in KOTLIN and 'requestFocus()' in KOTLIN,
      'failed keys return focus to the field')
check('imeOptions="actionDone"' in BASE.read_text() and
      'imeOptions="actionSearch"' in BASE.read_text(),
      'both text fields offer a meaningful keyboard action')
fades = (APP / 'res/anim/fade_in.xml').read_text() + (APP / 'res/anim/fade_out.xml').read_text()
check('duration="450"' not in fades and 'decelerate_interpolator' in fades,
      'activity handoff is short and eased')
check('R.id.splash_brand' in SPLASH and 'R.id.splash_art' in SPLASH,
      'the cover is a wordmark, one drawing and one status line')
check('postDelayed' not in SPLASH and 'Choreographer' in SPLASH,
      'the cover leaves when its launch checks settle, not on a timer')
about = (APP / 'kotlin/astral/AboutDialog.kt').read_text()
check('playbackEntrance' in about and 'R.id.about_art' in about,
      'about sheet has a staged entrance on the real illustration')
check('R.id.about_logo' not in about and 'R.id.about_info_card' not in about,
      'the dark logo square and duplicate info card are gone')

# Signed-in gating: the working card and settings belong to the clay state
# and never appear on the unauthenticated sheet.
gating = inside('    private fun setSignedInControlsVisible(',
                '    /** One place owns enablement')
check('R.id.library_card' in gating and 'R.id.operation_section' in gating,
      'signed-in controls are gated together')
check('browse_button' not in gating and 'library_menu_button' not in gating,
      'no duplicate library-list controls remain')
check('setSignedInControlsVisible(true)' in KOTLIN,
      'signed-in controls revealed after redemption')
clear_phase = inside('    private fun clearAuthenticatedPhase()', '    private fun animateToPhase')
check('setSignedInControlsVisible(false)' in clear_phase,
      'leaving the signed-in phase hides them again')
for gated in ('library_card', 'operation_section'):
    node = by_id.get(gated)
    check(node is not None, f'{gated} exists')
    check(node.attrib.get(ns + 'visibility') == 'gone',
          f'{gated} starts hidden before login')

# Method and delay are real dropdowns with a drawn affordance.
layout_text = BASE.read_text()
check('spinnerMode="dropdown"' in layout_text, 'spinners open as dropdowns')
spinner_code = (APP / 'kotlin/astral/ClaySpinner.kt').read_text()
check("canvas.drawPath(path,paint)" in spinner_code and 'chevron' in spinner_code,
      'dropdown cells draw a chevron affordance')
check((APP / 'res/drawable/ic_chevron_down.xml').is_file(), 'chevron drawable exists')

# Auto/manual: visible square boxes, exactly one ticked, preference is truth.
mode_group = (APP / 'kotlin/astral/LiquidModeGroup.kt').read_text()
check(by_id['radioGroup'].tag == 'astral.loader.LiquidModeGroup' and
      'class LiquidModeGroup' in mode_group and ': RadioGroup' in mode_group and
      by_id['radioGroup'].get(ns + 'layout_height') == 'wrap_content',
      'mode selector is a visible RadioGroup')
check(by_id['radio_auto'].tag == 'RadioButton' and by_id['radio_manual'].tag == 'RadioButton',
      'mode segments have native radio semantics')
check('@+id/radio_auto' in layout_text and '@+id/radio_manual' in layout_text,
      'AUTO and MANUAL boxes keep their ids')
check('@+id/mode_label' in layout_text, 'mode row is labelled')
prefs = (APP / 'kotlin/astral/LoaderPreferences.kt').read_text()
check('fun isAutoMode()' in prefs, 'auto mode has a source of truth')
check('loaderPreferences.isAutoMode()' in KOTLIN,
      'injection reads the mode preference')
check('radioGroup.setOnCheckedChangeListener' in KOTLIN,
      'RadioGroup owns exclusive selection')
check('setInjectionControlsEnabled' in KOTLIN,
      'control enablement and disabled state have one owner')
check('setInjectionControlsEnabled(true)' in inside(
          '                animateToPhase(1f)', '                if (filteredLibraries.isEmpty())'),
      'controls are re-enabled when the key is accepted')

# Legibility floor: 16sp body, 14sp supporting, 13sp labels, 12sp chips.
under_floor = []
for path in (APP / 'res/layout').glob('*.xml'):
    for match in re.finditer(r'android:textSize="(\d+)sp"', path.read_text()):
        if int(match.group(1)) < 12:
            under_floor.append(f'{path.name}:{match.group(1)}sp')
check(not under_floor, f'no text below 12sp ({under_floor})')
check('android:textSize="17sp"' in BASE.read_text(), 'body copy reads at 17sp, above the 16sp floor')

# Main controls are comfortable to tap; XML declaration does not assert runtime size.
text = BASE.read_text()
for item in ['android:minHeight="64dp"', 'android:layout_height="52dp"',
             'android:layout_height="48dp"']:
    check(item in text, f'touch target declared {item}')

# --- Conceptual sketch contract (2026-10 pass) ---------------------------------
colors = (APP / 'res/values/colors.xml').read_text()
for role, hex_value in (('paper', '#FDFCF7'), ('grid', '#C9DAF8'), ('ink', '#1F2937'),
                        ('pencil', '#777B84'), ('highlight', '#FFF176')):
    check(f'name="sketch_{role}">{hex_value}<' in colors, f'{role} uses the specified value')
check('paper = Color.rgb(253, 252, 247)' in visuals and 'gridBlue = Color.rgb(201, 218, 248)' in visuals,
      'canvas tokens match the palette')
check('highlight = Color.rgb(255, 241, 118)' in visuals, 'one accent, highlighter yellow')
check('draft = ink' in visuals, 'paper has no sixth hue')

# Typography: Space Grotesk for everything functional, Caveat only for notes.
theme = (APP / 'res/values/themes.xml').read_text()
check('android:fontFamily">@font/space_grotesk<' in theme, 'theme sets Space Grotesk')
check((APP / 'res/font/space_grotesk.xml').is_file() and (APP / 'res/font/caveat.ttf').is_file(),
      'both typefaces ship with the app')
illustration_ink = (APP / 'kotlin/astral/ShaiIllustrationView.kt').read_text()
check('R.font.caveat' in illustration_ink, 'annotations use Caveat')
check('patrick_hand' not in ''.join(p.read_text() for p in (APP / 'res/layout').glob('*.xml')),
      'handwriting is never used for form text')
pre_auth_text = ['hero_caption', 'remember_title',
                 'footer_text', 'license_heading']
for view in pre_auth_text:
    node = by_id[view]
    size = int(node.attrib.get(ns + 'textSize', '11sp').replace('sp', ''))
    check(size >= 16, f'{view} meets the 16sp body floor ({size}sp)')
check('android:textSize="16sp"' in (APP / 'res/layout/activity_splash.xml').read_text(),
      'cover status is set at body size')

# No fake editorial metadata, no image paper, no duplicated splash card.
layout_text = BASE.read_text()
for label in ('SHAI / 01', 'SHAI / 02', 'Not yet verified', 'The keykeeper’s notebook',
              'Library access', 'sheet_plate', 'chapter_label', 'brand_subtitle'):
    check(label not in layout_text, f'removed invented label: {label}')
check(not (APP / 'res/drawable/paper_grid.xml').exists() and
      not list((APP / 'res').glob('drawable-*dpi/paper_grid_tile.png')),
      'paper is drawn, not tiled from a bitmap')
check('GRID_STEP_DP = 24f' in visuals and 'GRID_ALPHA' in visuals,
      'ruling is 24dp canvas geometry')
splash_layout = (APP / 'res/layout/activity_splash.xml').read_text()
check('LoaderBackdropView' in splash_layout and 'splash_card' not in splash_layout,
      'the cover is a page, not a bordered card')
check('ProgressBar' not in splash_layout and 'progressBarSplash' not in SPLASH,
      'no simulated loading percentage')
for phrase in ('opens right here', 'SHEET 01', 'SHAI / 01'):
    check(phrase not in (APP / 'res/layout/activity_splash.xml').read_text(), f'no fake copy: {phrase}')

# The drawn field and the annotated toggle.
check('android:id="@+id/key_field"' in layout_text, 'the key field is a drawn surface')
check('boxBackgroundMode="none"' in layout_text, 'no Material box behind the drawn field')
check('SketchFieldDrawable' in KOTLIN and 'class SketchFieldDrawable' in
      (APP / 'kotlin/astral/SketchSurfaces.kt').read_text(), 'field frame is authored linework')
check('boxBackgroundColor' not in KOTLIN, 'the Material box is no longer painted')
check('Remember this key' in BASE.read_text(),
      'the note states a real consequence, not decoration')
check(by_id['remember_key'].attrib.get(ns + 'layout_height') == '48dp',
      'the toggle keeps a 48dp target')
check(by_id['login_button'].attrib.get(ns + 'minHeight') == '64dp',
      'the primary action keeps a flexible 64dp minimum target, with room for its own shadow')
check('android:text="Redeem key"' in layout_text and 'textAllCaps="false"' in layout_text,
      'one primary action in sentence case')

# Motion: strokes first, then the annotation, and nothing loops.
motion = (APP / 'kotlin/astral/ShaiMotion.kt').read_text()
check('ILLUSTRATION_MS' in motion and 'HIGHLIGHT_MS' in motion,
      'illustration and sweep timings are tokens')
check('completeReveal()' in KOTLIN and 'completeReveal()' in ILLUSTRATION,
      'reduced motion shows the finished drawing')
headline = (APP / 'kotlin/astral/RotatingHeadlineView.kt').read_text()
check('RotatingHeadlineView' in KOTLIN and 'headlineView.setPhase' in KOTLIN,
      'the headline rotates through one TextView and stops in clay')
check('postDelayed(advanceRunnable, HOLD_MS)' in headline and 'shouldRotate()' in headline,
      'the tip timer is owned by the visible view')
check('onDetachedFromWindow' in headline and 'removeCallbacks(advanceRunnable)' in headline,
      'rotation callbacks are cancelled with the view')
check('ValueAnimator.areAnimatorsEnabled()' in headline,
      'the rotating tip respects reduced motion')
for message in ('Shai Premium is better.', 'Shai updates every week!', 'Buy now!',
                'Suggest more feats!', 'Hey did you know?', 'Promo drops every week too!'):
    check(message in headline, f'exact rotating tip present: {message}')
check('shaistore.vercel.app' in headline and 'SHAI store' in headline,
      'Buy now points at the SHAI store')
check('isClickable = buyNow' in headline and 'messageIndex == BUY_NOW_INDEX' in headline,
      'only Buy now is actionable')
check('HL_FADE' not in headline and 'FADE_OUT_MS' in headline and 'FADE_IN_MS' in headline,
      'the tip handover is a fade, not a typing animation')
check('animator?.cancel()' in ILLUSTRATION, 'the reveal is interruptible')
check('ValueAnimator.areAnimatorsEnabled()' in ILLUSTRATION,
      'stroke reveal respects the system animation scale')
check('while (true)' not in ILLUSTRATION and 'repeat(' not in ILLUSTRATION,
      'no continuous decorative motion')

# Insets and IME keep the action reachable.
check('applyWindowInsets()' in KOTLIN and 'WindowInsetsCompat.Type.ime()' in KOTLIN,
      'the sheet handles status bar and keyboard insets')
check('WindowCompat.setDecorFitsSystemWindows(window, false)' in KOTLIN,
      'edge-to-edge is explicit')
check('smoothScrollTo' in KOTLIN, 'the field is brought above the keyboard')

# Startup: the permission prompt outlives neither activity by accident.
notifier = (APP / 'kotlin/astral/NotificationPermissionRequester.kt').read_text()
check('onSettled' in notifier and 'onPermissionResult()' in SPLASH,
      'the cover waits for a real permission result instead of a timer')
check('openLoader()' in SPLASH and 'isFinishing' in SPLASH,
      'navigation is idempotent and lifecycle-safe')


# --- Clay contract (2026-10 pass) ---------------------------------------------
colors_text = (APP / 'res/values/colors.xml').read_text()
for role, hex_value in (('bg', '#F4EEFF'), ('ink', '#2B2340'), ('muted', '#5F536F'),
                        ('raised', '#FAF7FF'), ('pink', '#FFB8D9'), ('mint', '#A8E6CF'),
                        ('peri', '#B5C7FF')):
    check(f'name="clay_{role}">{hex_value}<' in colors_text, f'clay colour {role} is the specified value')

# Depth is drawn, not faked with a border or a flat drop shadow.
check('drawStackShadow' in visuals and 'SHADOW_RINGS' in visuals,
      'raised surfaces cast a drawn stack shadow')
check('drawBevel' in visuals and 'LinearGradient' in visuals,
      'surfaces are lit from one upper-left source')
check('clipRect(rect)' in visuals, 'the bevel is clipped to the moulded form')
check('clayShadow(): Boolean' in visuals and 'Kind.SEARCH, Kind.SELECT -> false' in visuals,
      'recessed controls take an inner light instead of casting a shadow')
check('clayInset()' in visuals and 'at least the shadow reach' in visuals,
      'every raised surface reserves room for its own shadow')
check('state.any { it == android.R.attr.state_pressed }' in visuals,
      'press feedback comes from the surface state, not a tint')
check('override fun getOutline' in visuals,
      'elevation shadows follow the moulded radius')

# The crest is the same geometry, sculpted.
check('drawClayForm' in visuals_illustration, 'the crest has a clay form pass')
check('cloak' in visuals_illustration and 'FACE_OVAL' in visuals_illustration,
      'the sculpted silhouette is authored geometry, not a bitmap')
check('Ink.HATCH' in visuals_illustration and 'Ink.GROUND' in visuals_illustration,
      'construction marks are named so they can leave the page')
check('1f - clay' in visuals_illustration or 'clay * 1.9f' in visuals_illustration,
      'hatching and the ground line fade out as clay arrives')

# Typography switches with the material.
for font in ('fredoka_semibold.ttf', 'nunito_regular.ttf', 'nunito_semibold.ttf'):
    check((APP / 'res/font' / font).is_file(), f'{font} ships with the app')
check('object ShaiType' in type_file, 'typefaces resolve through one place')
check('applyTypefaces(clay)' in KOTLIN, 'the material change swaps the type voice')
check('letterSpacing = -0.015f' in headline and 'letterSpacing = -0.01f' in headline,
      'display tracking is set per material in the headline view')

# The transformation is state-driven and staged.
check('animateToPhase(1f)' in KOTLIN, 'only an accepted key starts the material change')
check('ValueAnimator.areAnimatorsEnabled()' in KOTLIN,
      'with animations off the finished object is shown directly')
check('DRAWER_REVEAL_DELAY_MS' not in motion,
      'the removed automatic drawer reveal has no timing token')

# Every authenticated surface belongs to the same system.
check('settingsSurface' in KOTLIN, 'the injection panel is a moulded surface too')
check('seal_clay' in KOTLIN, 'the verified mark is a clay bead, not the old dark ring')
check('pill_mint' in KOTLIN and 'pill_pink' in KOTLIN,
      'status chips carry the pastel material')
check('applyPill' in KOTLIN and 'clay_pill' in KOTLIN,
      'chips are restyled once per material, not per frame')
about = (APP / 'kotlin/astral/AboutDialog.kt').read_text()
about_layout = (APP / 'res/layout/activity_about.xml').read_text()

# 2026-10-26 brief: the "Key required" badge is gone; the near-black version
# row is gone from About; the padlock identity and the UI sound kit exist.
layout_text_again = BASE.read_text()
check('sheet_note' not in layout_text_again and 'sheet_note' not in KOTLIN,
      'the Key required header note is fully removed')
check('detail_version' not in about and 'detail_version' not in about_layout and
      'App version' not in about,
      'App version is removed from About, icon, label, value and row')
sounds = (APP / 'kotlin/astral/ShaiSounds.kt').read_text()
check('class ShaiSounds' in sounds and 'SoundPool' in sounds and
      'R.raw.ui_tap' in sounds and 'R.raw.ui_success' in sounds and
      'R.raw.ui_error' in sounds and 'R.raw.ui_ready' in sounds,
      'the UI sound kit plays four bundled cues through SoundPool')
check('RINGER_MODE_NORMAL' in sounds and 'STREAM_SYSTEM' in sounds,
      'the sound kit respects silent mode and system volume')
check(all(term not in sounds for term in ("setLooping", "LoopPoint")),
      'no cue loops or runs as ambience')
check('sound_toggle' not in layout_text_again and 'sound_toggle' not in KOTLIN and
      'ui_sounds_enabled' not in sounds,
      'sound feedback is enabled without a visible or stale preference toggle')
for raw in ('ui_tap.wav', 'ui_success.wav', 'ui_error.wav', 'ui_ready.wav'):
    check((APP / 'res/raw' / raw).is_file(), f'{raw} ships with the app')
check('Ink.LOCK' in visuals_illustration and 'arcTo' in visuals_illustration and
      'rotate(-40f * strength' in visuals_illustration,
      'the padlock is authored geometry whose shackle opens in clay')
check('addCircle(256f, 134f' in visuals_illustration,
      'the lock\'s keyhole stays carved in the mint body')

check('ShaiPalette.clayBg' in about and 'ShaiType.display' in about,
      'the about sheet belongs to the clay system')
check('about_art' in about_layout and 'about_details_card' in about_layout,
      'about uses the keykeeper illustration and a real details card')
check('@Shaiaintshyy' in (APP / 'kotlin/astral/AboutDialog.kt').read_text(),
      'creator attribution is part of the page')
check('about_logo' not in about_layout and 'about_pill' not in about_layout and
      'about_info_card' not in about_layout,
      'the dark square and duplicated about cards are gone')
check('@color/loader_background' not in about_layout and
      '@drawable/about_card' not in about_layout,
      'about does not use the retired dark surfaces')
check('MaterialButton' not in about and 'backgroundTintList' not in about,
      'the Telegram action is a clay surface, not a dark Material button')
check('Creator channel' in about_layout and '@Shaimainch' in about_layout and
      't.me/Shaimainch' in about,
      'creator channel copy and destination are preserved')

print('\nSTATIC UI CONTRACT CHECKS PASSED (not an APK runtime test).')
