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
check('R.drawable.hero_sketch' in KOTLIN and 'R.drawable.hero_ethereal' in KOTLIN,
      'sketch and ethereal art switch with auth state')
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
check('R.id.remember_key' in KOTLIN and 'R.id.remember_caption' in KOTLIN,
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
check('ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.void, phase)' in visuals,
      'paper and night backgrounds remain phase-aware')
check('gold = Color.rgb(233, 199, 126)' in visuals, 'night accent token retained')
check(all(term not in visuals for term in ('RadialGradient', 'drawCornerScroll', 'guilloche', 'sheen')),
      'notebook removes halos, ornamental frames and shimmer')
check('if (ruled)' in visuals and 'state_pressed' in visuals and 'state_focused' in visuals,
      'ruled surfaces preserve pressed and focus rendering')
check('class OrnamentRuleView' in visuals and 'OrnamentRuleView' in KOTLIN,
      'ornament rule is phase-aware')
check('hero_ethereal' in KOTLIN and 'hero_sketch' in KOTLIN, 'crest swaps with auth state')
crest = (APP / 'res/drawable/hero_ethereal.xml').read_text()
check('#E9C77E' in crest and '#C2A3FF' in crest, 'night illustration uses gold and lilac inks')
sketch = ET.parse(APP / 'res/drawable/hero_sketch.xml').getroot()
night = ET.parse(APP / 'res/drawable/hero_ethereal.xml').getroot()
check([p.attrib.get(ns + 'pathData') for p in sketch] ==
      [p.attrib.get(ns + 'pathData') for p in night],
      'keykeeper geometry remains identical across authentication')
check(by_id['hero_art'].attrib.get(ns + 'layout_width') == 'match_parent',
      'illustration is full width rather than a thumbnail')
check(by_id['hero_art'].attrib.get(ns + 'importantForAccessibility') == 'no',
      'decorative illustration does not repeat task announcements')
check('if (handMode) 26f else 31f' not in KOTLIN and
      'heroHeadline.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)' in KOTLIN,
      'display size is stable across authentication')
check(by_id['drawer_close'].attrib.get(ns + 'layout_height') == '48dp',
      'drawer close has a 48dp touch target')

# Motion is a named system with short feedback and one deliberate phase change.
motion_path = APP / 'kotlin/astral/ShaiMotion.kt'
check(motion_path.is_file(), 'motion tokens live in one file')
motion = motion_path.read_text()
check('PathInterpolator' in motion, 'motion uses explicit easing curves')
check('PRESS_IN_MS = 100L' in motion and 'PHASE_IN_MS = 520L' in motion,
      'press and phase durations are intentional tokens')
check('DRAWER_OPEN_MS' in motion and 'ROW_STAGGER_MS' in motion,
      'drawer and row staging are tokenised')
check('AccelerateDecelerateInterpolator' not in KOTLIN and
      'DecelerateInterpolator' not in KOTLIN,
      'no legacy ad-hoc interpolators remain in the main screen')
check('installPressFeedback(' in KOTLIN and 'scaleX(0.975f)' in KOTLIN,
      'primary controls have press feedback')
check('animateDrawerRows()' in KOTLIN and 'ROW_STAGGER_MS' in KOTLIN,
      'drawer rows are staged behind the panel')
success_reveal = inside('        val successSection = cv(R.id.success_section)',
                        '        phaseColorMatrix.setSaturation(phaseProgress)')
check('successSection.alpha = reveal' in success_reveal and
      'translationY' in success_reveal,
      'signed-in plaque is revealed by the phase timeline')
check('schedulePostAuthReveal(openDrawer = true)' in KOTLIN and
      'DRAWER_REVEAL_DELAY_MS' in KOTLIN,
      'drawer waits for the crest and plaque reveal')
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
splash_motion = 'splash_card' in SPLASH and 'ShaiMotion.easeOut' in SPLASH
check(splash_motion, 'splash sheet eases in rather than appearing finished')
about = (APP / 'kotlin/astral/AboutDialog.kt').read_text()
check('playbackEntrance' in about and 'R.id.about_logo' in about,
      'about sheet has a staged entrance')

# Signed-in gating: the library selector, mode/delay controls and browse
# action belong to the court and never appear on the unauthenticated sheet.
gating = inside('    private fun setSignedInControlsVisible(',
                '    /** One place owns enablement')
check('R.id.library_card' in gating and 'R.id.operation_section' in gating and
      'R.id.browse_button' in gating,
      'signed-in controls are gated together')
check('setSignedInControlsVisible(true)' in KOTLIN,
      'signed-in controls revealed after redemption')
clear_phase = inside('    private fun clearAuthenticatedPhase()', '    private fun animateToPhase')
check('setSignedInControlsVisible(false)' in clear_phase,
      'leaving the signed-in phase hides them again')
for gated in ('library_card', 'operation_section', 'browse_button'):
    node = by_id.get(gated)
    check(node is not None, f'{gated} exists')
    check(node.attrib.get(ns + 'visibility') == 'gone',
          f'{gated} starts hidden before login')

# Method and delay are real dropdowns with a drawn affordance.
layout_text = BASE.read_text()
check('spinnerMode="dropdown"' in layout_text, 'spinners open as dropdowns')
spinner_cell = (APP / 'res/layout/spinner_item_simple.xml').read_text()
check('drawableEnd="@drawable/ic_chevron_down"' in spinner_cell,
      'dropdown cells draw a chevron affordance')
check((APP / 'res/drawable/ic_chevron_down.xml').is_file(), 'chevron drawable exists')

# Auto/manual: visible square boxes, exactly one ticked, preference is truth.
check('<RadioGroup' not in layout_text, 'hidden 1dp radio stub is gone')
check('@drawable/checkbox_court' in layout_text,
      'mode control uses the court checkbox boxes')
check('@+id/radio_auto' in layout_text and '@+id/radio_manual' in layout_text,
      'AUTO and MANUAL boxes keep their ids')
check('@+id/mode_label' in layout_text, 'mode row is labelled')
prefs = (APP / 'kotlin/astral/LoaderPreferences.kt').read_text()
check('fun isAutoMode()' in prefs, 'auto mode has a source of truth')
check('loaderPreferences.isAutoMode()' in KOTLIN,
      'injection reads the mode preference')
check('radioManual.isChecked = false' in KOTLIN and 'radioAuto.isChecked = false' in KOTLIN,
      'exactly one mode box is ticked at a time')
check('setInjectionControlsEnabled' in KOTLIN,
      'control enablement and disabled state have one owner')
check('setInjectionControlsEnabled(true)' in inside(
          '                animateToPhase(1f)', '                if (filteredLibraries.isEmpty())'),
      'controls are re-enabled when the key is accepted')

# Legibility floor: no text below the 11sp floor flagged by the deslop audit.
under_floor = []
for path in (APP / 'res/layout').glob('*.xml'):
    for match in re.finditer(r'android:textSize="(\d+)sp"', path.read_text()):
        if int(match.group(1)) < 11:
            under_floor.append(f'{path.name}:{match.group(1)}sp')
check(not under_floor, f'no text below 11sp ({under_floor})')

# Main controls are comfortable to tap; XML declaration does not assert runtime size.
text = BASE.read_text()
for item in ['android:layout_height="56dp"', 'android:layout_height="52dp"',
             'android:layout_height="48dp"']:
    check(item in text, f'touch target declared {item}')

print('\nSTATIC UI CONTRACT CHECKS PASSED (not an APK runtime test).')
