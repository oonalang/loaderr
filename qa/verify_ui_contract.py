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

# Main controls are comfortable to tap; XML declaration does not assert runtime size.
text = BASE.read_text()
for item in ['android:layout_height="56dp"', 'android:layout_height="52dp"',
             'android:layout_height="48dp"']:
    check(item in text, f'touch target declared {item}')

print('\nSTATIC UI CONTRACT CHECKS PASSED (not an APK runtime test).')
