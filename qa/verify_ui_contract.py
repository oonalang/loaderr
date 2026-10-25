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

# The two phases are distinct, deliberate systems, not one recolored theme.
visuals = (APP / 'kotlin/astral/LoaderVisuals.kt').read_text()
check('drawConceptSheet' in visuals and 'drawVioletCourt' in visuals, 'each phase has its own backdrop')
check('gold = Color.rgb(233, 199, 126)' in visuals, 'maximal court gilding token exists')
check('drawCornerScroll' in visuals and 'guilloche' in visuals, 'maximal ornament layers exist')
check('class OrnamentRuleView' in visuals and 'OrnamentRuleView' in KOTLIN,
      'ornament rule is phase-aware')
check('hero_ethereal' in KOTLIN and 'hero_sketch' in KOTLIN, 'crest swaps with auth state')
crest = (APP / 'res/drawable/hero_ethereal.xml').read_text()
check('#E9C77E' in crest and '#925CF4' in crest, 'crest uses the maximal gold/violet palette')

# Main controls are comfortable to tap; XML declaration does not assert runtime size.
text = BASE.read_text()
for item in ['android:layout_height="56dp"', 'android:layout_height="52dp"',
             'android:layout_height="48dp"']:
    check(item in text, f'touch target declared {item}')

print('\nSTATIC UI CONTRACT CHECKS PASSED (not an APK runtime test).')
