package astral.loader

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Typeface
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.drawable.Animatable
import android.net.Uri
import android.util.Log
import android.util.TypedValue
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private companion object {
        const val SERVER_LIST_URL = "https://shaii.x10.mx/loader/catalog"
        const val PANEL_URL = "https://shaii.x10.mx/connect"
        const val STORE_URL = "https://ethnir-storefront.vercel.app/#home"
        const val LIBRARY_FILE_NAME = "libexternal.so"
    }

    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private lateinit var licenseKeyInput: TextInputEditText
    private lateinit var licenseKeyInputLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var librarySpinner: Spinner
    private lateinit var delaySpinner: Spinner
    private lateinit var methodSpinner: Spinner
    private lateinit var loaderPreferences: LoaderPreferences
    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: View
    private lateinit var radioAuto: CheckBox
    private lateinit var radioManual: CheckBox
    private lateinit var rootStatusView: TextView
    private lateinit var appBanner: ImageView
    private lateinit var mainScroll: ScrollView
    private lateinit var libraryDrawer: View
    private lateinit var drawerScrim: View
    private lateinit var librarySearch: EditText
    private lateinit var libraryList: LinearLayout
    private lateinit var libraryCount: TextView
    private lateinit var rememberKeyCheck: CheckBox
    private lateinit var keyFingerprint: TextView
    private lateinit var delayAdapter: SimpleSpinnerAdapter
    private lateinit var methodAdapter: SimpleSpinnerAdapter
    
    private val commandRunner = CommandRunner()
    private val catalogClient = LibraryCatalogClient(SERVER_LIST_URL)
    private val licenseValidator by lazy { PanelLicenseValidator(contentResolver) }
    private val assetExtractor by lazy { AssetExtractor(assets, cacheDir) }
    private var isReadyToInject: Boolean = false
    private var remoteLibrarySize: Int = 0
    private var selectedLibrary = SelectedLibrary()
    private var delaySeconds: Int = 0
    private var injectionMethod: String = "1"
    private var showTestLibraries = false
    private val allLibraries = mutableListOf<JSONObject>()
    private val filteredLibraries = mutableListOf<JSONObject>()
    private var isDrawerOpen = false
    private lateinit var backdrop: LoaderBackdropView
    private lateinit var libraryCardSurface: PhaseSurfaceDrawable
    private lateinit var primarySurface: PhaseSurfaceDrawable
    private lateinit var secondarySurface: PhaseSurfaceDrawable
    private lateinit var successSurface: PhaseSurfaceDrawable
    private lateinit var drawerSurface: PhaseSurfaceDrawable
    private lateinit var drawerSearchSurface: PhaseSurfaceDrawable
    private lateinit var delaySurface: PhaseSurfaceDrawable
    private lateinit var methodSurface: PhaseSurfaceDrawable
    private var phaseProgress = 0f
    private var phaseTarget = 0f
    private var phaseAnimator: ValueAnimator? = null
    private var entranceAnimator: ValueAnimator? = null
    private var pendingReveal: Runnable? = null
    private var authenticatedLibrary: String? = null
    private var licenseVerified = false
    private var redeemedGame: String? = null
    private var redeemedKey: String? = null
    private var isPreparingLibrary = false
    private var lastDisplayedSignedIn: Boolean? = null
    private var lastHandStyle: Boolean? = null
    private var pendingRememberedKey: String? = null
    private var autoLoginStarted = false
    private var restoredFromState = false
    private var redemptionInFlight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        restoredFromState = savedInstanceState != null
        super.onCreate(null)
        setContentView(R.layout.activity_main)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isDrawerOpen) {
                    setLibraryDrawerOpen(false)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                    // Re-arm if a host or another callback kept us alive; the
                    // next back press must still close an open drawer.
                    isEnabled = true
                }
            }
        })

        mainScroll = findViewById(R.id.main_scroll)
        libraryDrawer = findViewById(R.id.library_drawer)
        drawerScrim = findViewById(R.id.drawer_scrim)
        librarySearch = findViewById(R.id.library_search)
        libraryList = findViewById(R.id.library_list)
        libraryCount = findViewById(R.id.library_count)
        
        val appCacheDir = cacheDir.absolutePath
        val outputFile = File(appCacheDir, LIBRARY_FILE_NAME)
        if (outputFile.exists()) {
            outputFile.delete()
        }
        
        val versionCode = getAppVersionCode()
        
        progressBar = findViewById(R.id.progressBar)
        
        librarySpinner = findViewById(R.id.my_spinner)
        delaySpinner = findViewById(R.id.delay_spinner)
        methodSpinner = findViewById(R.id.method_spinner)
        licenseKeyInput = findViewById(R.id.input_key)
        licenseKeyInputLayout = findViewById(R.id.input_layout)
        loginButton = findViewById(R.id.login_button)
        radioGroup = findViewById(R.id.radioGroup)
        radioAuto = findViewById(R.id.radio_auto)
        radioManual = findViewById(R.id.radio_manual)
        rootStatusView = findViewById(R.id.root_status)
        appBanner = findViewById(R.id.app_banner)
        rememberKeyCheck = findViewById(R.id.remember_key)
        keyFingerprint = findViewById(R.id.key_fingerprint)
        
        Glide.with(this)
            .load(R.drawable.shai_mark)
            .centerCrop()
            .into(appBanner)
        
        loaderPreferences = LoaderPreferences(this)
        setupRememberedKey()
        setupVisuals()
        setupLibraryDrawer()

        val savedMode = loaderPreferences.getMode()
        if (savedMode == "auto") {
            radioAuto.isChecked = true
        } else {
            radioManual.isChecked = true
        }

        setupDelaySpinner()
        setupMethodSpinner()
        
        val savedDelay = loaderPreferences.getDelaySeconds()
        val savedMethod = loaderPreferences.getInjectionMethod()
        
        delaySpinner.setSelection(savedDelay)
        methodSpinner.setSelection(when (savedMethod) {
            "1" -> 0
            "2" -> 1
            "3" -> 2
            else -> 0
        })
        
        delaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                delaySeconds = position
                loaderPreferences.saveDelaySeconds(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        
        methodSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                injectionMethod = when (position) {
                    0 -> "1"
                    1 -> "2"
                    2 -> "3"
                    else -> "1"
                }
                loaderPreferences.saveInjectionMethod(injectionMethod)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        radioAuto.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                // Checkbox look, radio behaviour: exactly one mode is ticked.
                radioManual.isChecked = false
                loaderPreferences.saveMode("auto")
            }
        }
        radioManual.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                radioAuto.isChecked = false
                loaderPreferences.saveMode("manual")
            }
        }

        findViewById<View>(R.id.library_card).setOnClickListener { setLibraryDrawerOpen(true) }
        findViewById<View>(R.id.browse_button).setOnClickListener { setLibraryDrawerOpen(true) }
        findViewById<View>(R.id.about_button).setOnClickListener { AboutDialog(this, licenseVerified).show() }
        findViewById<View>(R.id.change_key_link).setOnClickListener {
            if (isPreparingLibrary) {
                showToast("Finish the current preparation first")
            } else {
                setLibraryDrawerOpen(false)
                clearAuthenticatedPhase()
                licenseKeyInput.text?.clear()
                licenseKeyInputLayout.error = null
                forgetRememberedKey()
                revealKeyEntry()
            }
        }

        licenseKeyInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE && !licenseVerified) {
                loginButton.performClick()
                true
            } else {
                false
            }
        }
        installPressFeedback(
            findViewById(R.id.login_button),
            findViewById(R.id.browse_button),
            findViewById(R.id.library_card)
        )

        loginButton.setOnClickListener {
            if (isReadyToInject) {
                injectLibrary(LIBRARY_FILE_NAME)
                showToast("Injecting, please wait...")
                loginButton.isEnabled = false
                setInjectionControlsEnabled(false)
            } else if (licenseVerified) {
                if (isPreparingLibrary) {
                    showToast("Preparing your library…")
                } else if (selectedLibrary.name.isBlank() || selectedLibrary.type != redeemedGame) {
                    showToast("Choose a library for your redeemed key")
                    findViewById<TextView>(R.id.success_detail).text = "Choose a library from the list, then tap Prepare library."
                    loginButton.text = "CHOOSE LIBRARY"
                    loginButton.isEnabled = true
                    setLibraryDrawerOpen(true)
                } else {
                    findViewById<TextView>(R.id.success_detail).text = "Verifying ${selectedLibrary.name}…"
                    verifySelectedLibraryAndDownload()
                }
            } else {
                beginLicenseRedemption()
            }
        }
        
        appBanner.setOnLongClickListener {
            // A brief acknowledgement, not an alpha jump that reads as a glitch.
            if (ValueAnimator.areAnimatorsEnabled()) {
                appBanner.animate().cancel()
                appBanner.animate().alpha(.7f)
                    .setDuration(90L)
                    .setInterpolator(ShaiMotion.easeOut)
                    .withEndAction {
                        appBanner.animate().alpha(1f)
                            .setDuration(260L)
                            .setInterpolator(ShaiMotion.easeOut)
                            .start()
                    }
                    .start()
            }
            
            filterLibraries(!showTestLibraries)
            
            val message = if (showTestLibraries) {
                "Test libraries shown"
            } else {
                "Test libraries hidden"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            
            true
        }
        
        playEntranceMotion()
        loadLibraryCatalog()
        updateRootStatus()
    }
    

    private fun playEntranceMotion() {
        val content = findViewById<View>(R.id.main_content)
        val rule = findViewById<OrnamentRuleView>(R.id.hero_rule)
        val keyNote = findViewById<View>(R.id.key_note)
        val keyArrow = findViewById<ImageView>(R.id.key_note_arrow)
        entranceAnimator?.cancel()
        if (!ValueAnimator.areAnimatorsEnabled()) {
            content.alpha = 1f
            content.translationY = 0f
            rule.reveal = 1f
            keyNote.alpha = 1f
            keyArrow.alpha = 1f
            return
        }
        // One orchestrated sheet settle, not a fade-up on every section.
        content.alpha = 0f
        content.translationY = 14f * resources.displayMetrics.density
        rule.reveal = 0f
        content.animate().alpha(1f).translationY(0f)
            .setDuration(ShaiMotion.ENTRANCE_MS)
            .setInterpolator(ShaiMotion.easeOut)
            .start()
        entranceAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            startDelay = 80L
            duration = ShaiMotion.RULE_REVEAL_MS
            interpolator = ShaiMotion.easeOut
            addUpdateListener { rule.reveal = it.animatedValue as Float }
            start()
        }
        // Margin note and arrow ink themselves once the rule is drawn.
        keyNote.alpha = 0f
        keyArrow.alpha = 0f
        keyNote.animate().alpha(1f)
            .setStartDelay(300L)
            .setDuration(220L)
            .setInterpolator(ShaiMotion.easeOut)
            .start()
        keyArrow.animate().alpha(1f)
            .setStartDelay(420L)
            .setDuration(200L)
            .setInterpolator(ShaiMotion.easeOut)
            .withEndAction {
                (keyArrow.drawable as? Animatable)?.start()
            }
            .start()
    }

    private fun installPressFeedback(vararg views: View) {
        views.forEach { view ->
            view.setOnTouchListener { target, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        target.animate().cancel()
                        if (ValueAnimator.areAnimatorsEnabled()) {
                            target.animate()
                                .scaleX(0.975f).scaleY(0.975f)
                                .setDuration(ShaiMotion.PRESS_IN_MS)
                                .setInterpolator(ShaiMotion.easeOut)
                                .start()
                        }
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        target.animate().cancel()
                        target.animate()
                            .scaleX(1f).scaleY(1f)
                            .setDuration(ShaiMotion.PRESS_OUT_MS)
                            .setInterpolator(ShaiMotion.easeOut)
                            .start()
                    }
                }
                false
            }
        }
    }

    private fun setupRememberedKey() {
        val remembered = loaderPreferences.getRememberedKey()
        rememberKeyCheck.isChecked = !remembered.isNullOrBlank()
        if (!remembered.isNullOrBlank()) {
            licenseKeyInput.setText(remembered)
            pendingRememberedKey = remembered
        }
        rememberKeyCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val key = licenseKeyInput.text?.toString()?.trim().orEmpty()
                if (key.isNotBlank()) {
                    loaderPreferences.saveRememberedKey(key)
                    pendingRememberedKey = key
                }
            } else {
                loaderPreferences.setRememberKeyEnabled(false)
                pendingRememberedKey = null
            }
            updateRememberCaption()
        }
        licenseKeyInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (rememberKeyCheck.isChecked) {
                    val key = s?.toString()?.trim().orEmpty()
                    if (key.isNotBlank()) loaderPreferences.saveRememberedKey(key)
                }
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
        updateRememberCaption()
    }

    private fun updateRememberCaption() {
        findViewById<TextView>(R.id.remember_caption).text = if (rememberKeyCheck.isChecked) {
            "Saved on this device. SHAI tries it when the app opens."
        } else {
            "Redeem automatically the next time SHAI opens."
        }
    }

    /** Runs once per launch, after catalog/version checks. It never downloads a library. */
    private fun maybeAutoLogin() {
        if (autoLoginStarted || restoredFromState || licenseVerified) return
        val key = pendingRememberedKey?.trim().orEmpty()
        if (key.isBlank() || !loaderPreferences.isRememberKeyEnabled()) return
        autoLoginStarted = true
        licenseKeyInput.setText(key)
        findViewById<TextView>(R.id.hero_caption).text = "Remembered key. Checking it with the panel…"
        loginButton.postDelayed({
            val current = licenseKeyInput.text?.toString()?.trim()
            if (!isFinishing && !isDestroyed && !licenseVerified &&
                rememberKeyCheck.isChecked && current == key
            ) {
                beginLicenseRedemption()
            }
        }, 260L)
    }

    private fun beginLicenseRedemption() {
        if (licenseVerified || redemptionInFlight) return
        redemptionInFlight = true
        loginButton.isEnabled = false
        loginButton.text = "VERIFYING KEY…"
        findViewById<TextView>(R.id.hero_caption).text = "Verifying your key…"
        licenseKeyInput.isEnabled = false
        licenseKeyInputLayout.error = null
        librarySpinner.isEnabled = false
        setInjectionControlsEnabled(false)
        val licenseKey = licenseKeyInput.text?.toString()?.trim()
        if (licenseKey.isNullOrEmpty()) {
            resetLoginState("Enter the key from your panel.")
            return
        }
        // Once there is something to check, get the keyboard out of the way.
        hideKeyboard()
        val detectedVpn = SplashActivity.getDetectedVPN()
        if (detectedVpn != null) {
            SplashActivity.showDialog(
                this,
                "VPN DETECTED",
                "Detected VPN Interface:\n$detectedVpn\n\nPlease disable your VPN and try again."
            )
        } else {
            validateLicense(licenseKey)
        }
    }

    private fun forgetRememberedKey() {
        loaderPreferences.setRememberKeyEnabled(false)
        loaderPreferences.clearRememberedKey()
        pendingRememberedKey = null
        if (rememberKeyCheck.isChecked) {
            rememberKeyCheck.isChecked = false
        } else {
            updateRememberCaption()
        }
    }

    private fun keyLabel(key: String): String {
        val clean = key.trim()
        return if (clean.length >= 12) {
            "KEY  ${clean.take(4)} ···· ${clean.takeLast(4)}"
        } else {
            "KEY  ···· ${clean.takeLast(2)}"
        }
    }

    // Keep a single layout and change its material treatment only when the server
    // accepts the entered license. A saved text value does not restore this state.
    private fun setupVisuals() {
        backdrop = findViewById(R.id.phase_backdrop)
        libraryCardSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.CARD)
        primarySurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.PRIMARY)
        secondarySurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.SECONDARY)
        successSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.SUCCESS)
        drawerSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.DRAWER)
        drawerSearchSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.SEARCH)
        delaySurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.SELECT)
        methodSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.SELECT)
        delaySpinner.background = delaySurface
        methodSpinner.background = methodSurface
        findViewById<View>(R.id.library_card).background = libraryCardSurface
        loginButton.background = primarySurface
        findViewById<View>(R.id.browse_button).background = secondarySurface
        findViewById<View>(R.id.success_section).background = successSurface
        libraryDrawer.background = drawerSurface
        librarySearch.background = drawerSearchSurface
        loginButton.text = "REDEEM KEY"
        loginButton.isEnabled = true
        updateTheme(0f)
    }

    private fun clearAuthenticatedPhase() {
        pendingReveal?.let { mainScroll.removeCallbacks(it) }
        pendingReveal = null
        setSignedInControlsVisible(false)
        setInjectionControlsEnabled(true)
        licenseVerified = false
        redemptionInFlight = false
        authenticatedLibrary = null
        redeemedGame = null
        redeemedKey = null
        isPreparingLibrary = false
        selectedLibrary = SelectedLibrary()
        Glide.with(this).clear(appBanner)
        appBanner.setImageResource(R.drawable.shai_mark)
        findViewById<TextView>(R.id.library_name).text = "No library selected"
        findViewById<TextView>(R.id.library_game).text = "Redeem your key to continue"
        findViewById<TextView>(R.id.library_version).visibility = View.GONE
        findViewById<TextView>(R.id.library_package).visibility = View.GONE
        keyFingerprint.text = ""
        keyFingerprint.visibility = View.GONE
        allLibraries.clear()
        filteredLibraries.clear()
        if (::librarySpinner.isInitialized) updateSpinnerWithFilteredData()
        refreshLibraryDrawer()
        isReadyToInject = false
        findViewById<View>(R.id.license_section).visibility = View.VISIBLE
        findViewById<View>(R.id.success_section).visibility = View.GONE
        findViewById<TextView>(R.id.hero_caption).text =
            "Paste a key from your panel. Nothing opens until it is checked."
        appBanner.isEnabled = true
        licenseKeyInput.isEnabled = true
        librarySpinner.isEnabled = true
        loginButton.text = "REDEEM KEY"
        loginButton.isEnabled = true
        animateToPhase(0f)
    }

    /** Per-frame view cache: updateTheme runs on every phase-animation frame. */
    private val viewCache = HashMap<Int, View>()

    private fun cv(id: Int): View = viewCache.getOrPut(id) { findViewById(id) }

    private fun tv(id: Int): TextView = cv(id) as TextView

    /** Reused matrix for the banner desaturation; the filter copies it at construction. */
    private val phaseColorMatrix = ColorMatrix()

    private fun animateToPhase(target: Float) {
        phaseAnimator?.cancel()
        phaseTarget = target
        if (!ValueAnimator.areAnimatorsEnabled()) {
            updateTheme(target)
            return
        }
        phaseAnimator = ValueAnimator.ofFloat(phaseProgress, target).apply {
            // Deliberate entry, quicker exit: the sheet returns faster than the court assembles.
            duration = if (target < phaseProgress) ShaiMotion.PHASE_OUT_MS else ShaiMotion.PHASE_IN_MS
            interpolator = ShaiMotion.easeInOut
            addUpdateListener { updateTheme(it.animatedValue as Float) }
            start()
        }
    }

    private fun updateTheme(progress: Float) {
        phaseProgress = progress.coerceIn(0f, 1f)
        backdrop.phase = phaseProgress
        libraryCardSurface.phase = phaseProgress
        primarySurface.phase = phaseProgress
        secondarySurface.phase = phaseProgress
        successSurface.phase = phaseProgress
        drawerSurface.phase = phaseProgress
        drawerSearchSurface.phase = phaseProgress
        delaySurface.phase = phaseProgress
        methodSurface.phase = phaseProgress
        (cv(R.id.hero_rule) as OrnamentRuleView).phase = phaseProgress
        (cv(R.id.drawer_rule) as OrnamentRuleView).phase = phaseProgress
        // The washi tape holds the unsigned sketch on the sheet; it lifts as the court takes over.
        val tapeAlpha = 1f - phaseProgress
        cv(R.id.tape_start).alpha = tapeAlpha
        cv(R.id.tape_end).alpha = tapeAlpha

        val ink = ShaiPalette.blend(ShaiPalette.ink, Color.rgb(246, 239, 255), phaseProgress)
        val secondary = ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.muted, phaseProgress)
        val accent = ShaiPalette.blend(ShaiPalette.draft, ShaiPalette.gold, phaseProgress)
        val line = ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.goldDeep, phaseProgress)
        val heroHeadline = tv(R.id.hero_headline)
        val displaySignedIn = phaseProgress >= .55f
        val density = resources.displayMetrics.density
        val titleIds = intArrayOf(
            R.id.brand_title, R.id.library_name, R.id.hero_headline,
            R.id.success_title, R.id.drawer_title, R.id.remember_title,
            R.id.radio_auto, R.id.radio_manual
        )
        val secondaryIds = intArrayOf(
            R.id.brand_subtitle, R.id.chapter_label, R.id.library_game,
            R.id.library_version, R.id.library_package, R.id.hero_caption,
            R.id.license_heading, R.id.footer_text, R.id.library_count,
            R.id.success_detail, R.id.remember_caption, R.id.control_hint,
            R.id.operation_note, R.id.sheet_note
        )
        titleIds.forEach { tv(it).setTextColor(ink) }
        secondaryIds.forEach { tv(it).setTextColor(secondary) }
        intArrayOf(
            R.id.library_arrow, R.id.about_button, R.id.drawer_about,
            R.id.change_key_link, R.id.sheet_plate, R.id.operation_heading,
            R.id.method_label, R.id.delay_label, R.id.mode_label, R.id.key_fingerprint
        ).forEach { tv(it).setTextColor(accent) }
        tv(R.id.library_status).setTextColor(accent)
        tv(R.id.success_emblem).setTextColor(
            ShaiPalette.blend(ShaiPalette.draft, ShaiPalette.gold, phaseProgress)
        )
        tv(R.id.browse_button).setTextColor(
            ShaiPalette.blend(ShaiPalette.ink, ShaiPalette.orchid, phaseProgress)
        )
        loginButton.setTextColor(
            ShaiPalette.blend(ShaiPalette.ink, Color.rgb(32, 20, 6), phaseProgress)
        )
        licenseKeyInput.setTextColor(ink)
        licenseKeyInput.setHintTextColor(secondary)
        licenseKeyInputLayout.boxBackgroundColor =
            ShaiPalette.blend(ShaiPalette.paperCard, Color.rgb(28, 24, 38), phaseProgress)
        licenseKeyInputLayout.boxStrokeColor = accent
        licenseKeyInputLayout.defaultHintTextColor = ColorStateList.valueOf(secondary)
        findViewById<EditText>(R.id.library_search).apply {
            setTextColor(ink)
            setHintTextColor(secondary)
        }
        findViewById<View>(R.id.sheet_rule).backgroundTintList = ColorStateList.valueOf(line)
        findViewById<View>(R.id.library_divider).backgroundTintList = ColorStateList.valueOf(line)
        findViewById<View>(R.id.success_rule).backgroundTintList = ColorStateList.valueOf(line)
        progressBar.progressTintList = ColorStateList.valueOf(accent)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(
            ShaiPalette.blend(ShaiPalette.paperCard, ShaiPalette.plum, phaseProgress)
        )
        val successSection = cv(R.id.success_section)
        if (successSection.visibility == View.VISIBLE) {
            val reveal = ((phaseProgress - .30f) / .70f).coerceIn(0f, 1f)
            successSection.alpha = reveal
            successSection.translationY = (1f - reveal) * 16f * density
            val scale = .975f + .025f * reveal
            successSection.scaleX = scale
            successSection.scaleY = scale
        }
        phaseColorMatrix.setSaturation(phaseProgress)
        appBanner.colorFilter = ColorMatrixColorFilter(phaseColorMatrix)
        val handMode = !displaySignedIn
        if (lastHandStyle != handMode) {
            val display = Typeface.create("sans-serif", Typeface.BOLD)
            val brand = tv(R.id.brand_title)
            val success = tv(R.id.success_title)
            brand.typeface = display
            heroHeadline.typeface = display
            success.typeface = display
            brand.letterSpacing = 0f
            heroHeadline.letterSpacing = 0f
            heroHeadline.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)
            success.letterSpacing = 0f
            success.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            if (::delayAdapter.isInitialized) delayAdapter.applyPhase(phaseProgress)
            if (::methodAdapter.isInitialized) methodAdapter.applyPhase(phaseProgress)
            lastHandStyle = handMode
        }
        if (lastDisplayedSignedIn != displaySignedIn) {
            tv(R.id.sheet_plate).text =
                if (displaySignedIn) "SHAI / 02" else "SHAI / 01"
            tv(R.id.sheet_note).text =
                if (displaySignedIn) "READY" else "Not yet verified"
            tv(R.id.operation_heading).text =
                if (displaySignedIn) "Injection" else "Operations"
            tv(R.id.operation_note).text =
                if (displaySignedIn) "Set before prepare" else "Applies after access"
            tv(R.id.brand_subtitle).text =
                "The keykeeper’s notebook"
            tv(R.id.chapter_label).text =
                if (displaySignedIn) "Your collection" else "Library access"
            heroHeadline.text =
                if (displaySignedIn) "Access granted." else "Your key comes first."
            findViewById<TextView>(R.id.hero_caption).text = if (displaySignedIn) {
                "Choose one library. Nothing downloads until you confirm."
            } else {
                "Paste a key from your panel. Nothing opens until it is checked."
            }
            val heroArt = findViewById<ImageView>(R.id.hero_art)
            heroArt.setImageResource(if (displaySignedIn) R.drawable.hero_ethereal else R.drawable.hero_sketch)
            heroArt.contentDescription =
                "Illustrated keykeeper holding a key"
            if (ValueAnimator.areAnimatorsEnabled()) {
                heroArt.animate().cancel()
                if (displaySignedIn) {
                    // Stamp the crest, rather than only fading one image into another.
                    heroArt.alpha = 0.6f
                    heroArt.scaleX = 0.90f
                    heroArt.scaleY = 0.90f
                    heroArt.rotation = -3f
                    heroArt.animate()
                        .alpha(1f).scaleX(1f).scaleY(1f).rotation(0f)
                        .setDuration(ShaiMotion.CREST_MS)
                        .setInterpolator(ShaiMotion.easeOut)
                        .start()
                } else {
                    heroArt.alpha = 1f
                    heroArt.scaleX = 1f
                    heroArt.scaleY = 1f
                    // Paper tilt: the unsigned sketch is taped onto the sheet, not stamped.
                    heroArt.rotation = -1.5f
                }
            } else {
                heroArt.alpha = 1f
                heroArt.scaleX = 1f
                heroArt.scaleY = 1f
                heroArt.rotation = if (displaySignedIn) 0f else -1.5f
            }
            findViewById<TextView>(R.id.library_status).text = when {
                selectedLibrary.name.isBlank() && !licenseVerified -> "KEY REQUIRED"
                selectedLibrary.name.isBlank() && licenseVerified -> "CHOOSE LIBRARY"
                isReadyToInject -> "READY"
                isPreparingLibrary -> "PREPARING"
                licenseVerified -> "SELECTED"
                else -> "AVAILABLE"
            }
            lastDisplayedSignedIn = displaySignedIn
        }
        findViewById<ImageButton>(R.id.library_menu_button).setColorFilter(accent)
        findViewById<ImageButton>(R.id.drawer_close).setColorFilter(accent)
        val systemBar = ShaiPalette.blend(ShaiPalette.paper, ShaiPalette.void, phaseProgress)
        window.statusBarColor = systemBar
        window.navigationBarColor = systemBar
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = if (!displaySignedIn) {
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else { 0 }
        // Rows are created asynchronously when the catalog arrives.
        for (i in 0 until libraryList.childCount) {
            styleDrawerRow(libraryList.getChildAt(i))
        }
    }

    private fun styleDrawerRow(row: View) {
        val surface = row.background as? PhaseSurfaceDrawable
            ?: PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.ROW).also { row.background = it }
        surface.phase = phaseProgress
        val main = ShaiPalette.blend(ShaiPalette.ink, Color.rgb(246, 239, 255), phaseProgress)
        val sub = ShaiPalette.blend(ShaiPalette.pencil, ShaiPalette.muted, phaseProgress)
        intArrayOf(R.id.drawer_name, R.id.drawer_meta, R.id.drawer_package).forEachIndexed { index, id ->
            row.findViewById<TextView>(id)?.setTextColor(if (index == 0) main else sub)
        }
    }

    private fun setupDelaySpinner() {
        val delayOptions = arrayOf("NO DELAY", "1S DELAY", "2S DELAY", "3S DELAY", "4S DELAY", "5S DELAY")
        delayAdapter = SimpleSpinnerAdapter(this, delayOptions)
        delaySpinner.adapter = delayAdapter
        delayAdapter.applyPhase(phaseProgress)
    }

    private fun setupMethodSpinner() {
        val methodOptions = arrayOf("METHOD 1", "METHOD 2", "METHOD 3")
        methodAdapter = SimpleSpinnerAdapter(this, methodOptions)
        methodSpinner.adapter = methodAdapter
        methodAdapter.applyPhase(phaseProgress)
    }
    
    private fun saveLicenseKeyForLibrary(libraryName: String, licenseKey: String) {
        loaderPreferences.licenses.saveForLibrary(libraryName, licenseKey)
    }

    private fun getLicenseKeyForLibrary(libraryName: String): String? {
        return loaderPreferences.licenses.getForLibrary(libraryName)
    }

    private fun loadLibraryCatalog() {
        lifecycleScope.launch {
            val root = catalogClient.load().getOrElse { _ ->
                Toast.makeText(this@MainActivity, "Failed to load servers data", Toast.LENGTH_SHORT).show()
                return@launch
            }

            val meta = root.optJSONObject("META")
            if (meta != null) {
                val requiredCode = meta.optInt("APP_VERSION_CODE", -1)
                val reason = meta.optString("VERSION_REASON", "Update required.")
                val updateUrl = meta.optString("UPDATE_URL")

                val currentCode = getAppVersionCode()
                if (requiredCode != -1 && currentCode < requiredCode) {
                    showVersionMismatchDialog(reason, updateUrl)
                    return@launch
                }
            }

            // Older loader versions use the public catalog. This client reads
            // update metadata only and waits for /redeem before showing entries.
            refreshLibraryDrawer()
            loginButton.text = "REDEEM KEY"
            loginButton.isEnabled = true
            maybeAutoLogin()
        }
    }
    
    private fun openStore() {
        val storeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(STORE_URL))
        try {
            startActivity(storeIntent)
        } catch (error: Exception) {
            Log.w("SHAI_LOADER", "Unable to open the store link", error)
            showToast("Unable to open store")
        }
    }

    private fun setupLibraryDrawer() {
        libraryDrawer.post {
            libraryDrawer.translationX = -libraryDrawer.width.toFloat()
        }

        findViewById<View>(R.id.library_menu_button).setOnClickListener {
            setLibraryDrawerOpen(!isDrawerOpen)
        }
        findViewById<View>(R.id.drawer_close).setOnClickListener {
            setLibraryDrawerOpen(false)
        }
        drawerScrim.setOnClickListener {
            setLibraryDrawerOpen(false)
        }

        librarySearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard()
                true
            } else {
                false
            }
        }

        librarySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                refreshLibraryDrawer(text?.toString().orEmpty())
            }
            override fun afterTextChanged(editable: Editable?) = Unit
        })

        findViewById<View>(R.id.drawer_about).setOnClickListener {
            setLibraryDrawerOpen(false)
            openStore()
        }

        refreshLibraryDrawer()
    }

    private fun setLibraryDrawerOpen(open: Boolean) {
        if (isDrawerOpen == open) return
        isDrawerOpen = open
        // Any explicit open or close supersedes the post-auth auto-reveal.
        pendingReveal?.let { mainScroll.removeCallbacks(it) }
        pendingReveal = null
        libraryDrawer.animate().cancel()
        drawerScrim.animate().cancel()
        if (!ValueAnimator.areAnimatorsEnabled()) {
            libraryDrawer.visibility = if (open) View.VISIBLE else View.GONE
            drawerScrim.visibility = if (open) View.VISIBLE else View.GONE
            drawerScrim.alpha = if (open) 1f else 0f
            libraryDrawer.translationX = if (open) 0f else -libraryDrawer.width.toFloat()
            setMainContentBlur(open)
            return
        }

        if (open) {
            drawerScrim.visibility = View.VISIBLE
            drawerScrim.alpha = 0f
            drawerScrim.animate()
                .alpha(1f)
                .setDuration(ShaiMotion.SCRIM_OPEN_MS)
                .setInterpolator(ShaiMotion.easeOut)
                .start()

            if (libraryDrawer.width > 0) {
                libraryDrawer.translationX = -libraryDrawer.width.toFloat()
            }
            libraryDrawer.visibility = View.VISIBLE
            libraryDrawer.animate()
                .translationX(0f)
                .setDuration(ShaiMotion.DRAWER_OPEN_MS)
                .setInterpolator(ShaiMotion.easeDrawer)
                .start()
            setMainContentBlur(true)
            animateDrawerRows()
        } else if (libraryDrawer.width > 0) {
            libraryDrawer.animate()
                .translationX(-libraryDrawer.width.toFloat())
                .setDuration(ShaiMotion.DRAWER_CLOSE_MS)
                .setInterpolator(ShaiMotion.easeDrawer)
                .withEndAction { libraryDrawer.visibility = View.GONE }
                .start()

            drawerScrim.animate()
                .alpha(0f)
                .setDuration(ShaiMotion.SCRIM_CLOSE_MS)
                .setInterpolator(ShaiMotion.easeOut)
                .withEndAction { drawerScrim.visibility = View.GONE }
                .start()
            setMainContentBlur(false)
        } else {
            libraryDrawer.visibility = View.GONE
            drawerScrim.animate()
                .alpha(0f)
                .setDuration(ShaiMotion.SCRIM_CLOSE_MS)
                .setInterpolator(ShaiMotion.easeOut)
                .withEndAction { drawerScrim.visibility = View.GONE }
                .start()
            setMainContentBlur(false)
        }
    }

    /** Rows follow the panel in with a short, capped stagger. */
    private fun animateDrawerRows() {
        if (!::libraryList.isInitialized) return
        val density = resources.displayMetrics.density
        // Cap the cascade: only the first screenful participates, so a long
        // catalogue cannot turn an open into dozens of concurrent animations.
        for (index in 0 until minOf(libraryList.childCount, 12)) {
            val row = libraryList.getChildAt(index)
            row.animate().cancel()
            if (!ValueAnimator.areAnimatorsEnabled()) {
                row.alpha = if (row.isEnabled) 1f else .6f
                row.translationX = 0f
                continue
            }
            val restingAlpha = if (row.isEnabled) 1f else .6f
            row.alpha = 0f
            row.translationX = 14f * density
            row.animate()
                .alpha(restingAlpha)
                .translationX(0f)
                .setStartDelay(90L + minOf(index, 7) * ShaiMotion.ROW_STAGGER_MS)
                .setDuration(ShaiMotion.ROW_MS)
                .setInterpolator(ShaiMotion.easeOut)
                .start()
        }
    }

    @Suppress("NewApi")
    private fun setMainContentBlur(enabled: Boolean) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.S) return
        mainScroll.setRenderEffect(
            if (enabled) {
                RenderEffect.createBlurEffect(6f, 6f, Shader.TileMode.CLAMP)
            } else {
                null
            }
        )
    }

    private fun refreshLibraryDrawer(query: String = librarySearch.text?.toString().orEmpty()) {
        if (!::libraryList.isInitialized) return

        val normalizedQuery = query.trim()
        val visibleLibraries = allLibraries.filter { library ->
            normalizedQuery.isBlank() ||
                library.optString("NAME").contains(normalizedQuery, ignoreCase = true) ||
                library.optString("GAME").contains(normalizedQuery, ignoreCase = true) ||
                library.optString("TYPE").contains(normalizedQuery, ignoreCase = true)
        }

        libraryList.removeAllViews()
        libraryCount.text = when {
            !licenseVerified -> "REDEEM A KEY FIRST"
            allLibraries.isEmpty() -> "NO LIBRARIES PUBLISHED"
            else -> "${visibleLibraries.size} LIBRARIES"
        }

        if (visibleLibraries.isEmpty()) {
            val emptyState = TextView(this).apply {
                text = when {
                    !licenseVerified -> "Redeem a key to view your libraries."
                    allLibraries.isEmpty() -> "No libraries are available for this key."
                    else -> "No results. Try another search."
                }
                setTextColor(ContextCompat.getColor(this@MainActivity, R.color.loader_text_muted))
                textSize = 14f
                setPadding(16, 24, 16, 24)
            }
            libraryList.addView(emptyState)
            return
        }

        visibleLibraries.forEach { library ->
            val row = layoutInflater.inflate(R.layout.item_library_drawer, libraryList, false)
            val name = library.optString("NAME").trim().ifBlank { "Unnamed library" }
            val packageName = library.optString("GAME").trim()
            val version = LibraryMetadata.versionLabel(library)
            val gameLabel = LibraryMetadata.gameLabel(library)
            val meta = listOf(gameLabel, version).filter { it.isNotBlank() }.joinToString(" • ")
            val status = LibraryMetadata.status(library)
            val available = status == "ACTIVE"

            styleDrawerRow(row)
            row.findViewById<TextView>(R.id.drawer_name).text = name
            row.findViewById<TextView>(R.id.drawer_meta).text = meta
            row.findViewById<TextView>(R.id.drawer_package).apply {
                text = packageName
                visibility = if (packageName.isBlank()) View.GONE else View.VISIBLE
            }
            row.findViewById<TextView>(R.id.drawer_status).apply {
                text = status
                setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        if (available) R.color.loader_status_active else R.color.loader_status_maintenance
                    )
                )
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    if (available) R.drawable.status_pill else R.drawable.maintenance_pill
                )
            }
            // A consistent drawable that adapts to the sketch and ethereal phases.
            val selected = available && name == selectedLibrary.name
            val rowSurface = PhaseSurfaceDrawable(PhaseSurfaceDrawable.Kind.ROW)
            rowSurface.phase = phaseProgress
            rowSurface.selected = selected
            row.background = rowSurface
            row.isSelected = selected
            row.isEnabled = available
            row.contentDescription = listOf(name, gameLabel, version, status).filter { it.isNotBlank() }.joinToString(", ")
            row.alpha = if (available) 1f else 0.6f

            Glide.with(this)
                .load(library.optString("LOGO"))
                .placeholder(R.drawable.shai_mark)
                .error(R.drawable.shai_mark)
                .centerCrop()
                .into(row.findViewById(R.id.drawer_logo))

            row.setOnClickListener {
                if (available) {
                    selectLibraryFromDrawer(library)
                } else {
                    showToast("$name is under maintenance")
                }
            }
            libraryList.addView(row)
        }
    }

    private fun selectLibraryFromDrawer(library: JSONObject) {
        if (isPreparingLibrary) {
            showToast("Finish preparing the current library first")
            return
        }
        if (!LibraryMetadata.isActive(library)) {
            showToast("${library.optString("NAME", "Library")} is under maintenance")
            return
        }
        handleSelectedJsonData(library)
        if (!licenseVerified) licenseKeyInput.setText(getLicenseKeyForLibrary(selectedLibrary.name).orEmpty())
        saveSelectedLibrary(selectedLibrary.name)
        setLibraryDrawerOpen(false)
        if (ValueAnimator.areAnimatorsEnabled()) {
            findViewById<View>(R.id.library_card).apply {
                animate().cancel()
                scaleX = 0.985f
                scaleY = 0.985f
                animate().scaleX(1f).scaleY(1f)
                    .setDuration(ShaiMotion.CARD_CONFIRM_MS)
                    .setInterpolator(ShaiMotion.easeOut)
                    .start()
            }
        }
    }

    private fun filterLibraries(includeTestLibraries: Boolean) {
        showTestLibraries = includeTestLibraries
        filteredLibraries.clear()
        if (includeTestLibraries) {
            filteredLibraries.addAll(allLibraries.filter { library ->
                library.optString("NAME").contains("TEST", ignoreCase = true) &&
                    LibraryMetadata.isActive(library)
            })
        } else {
            filteredLibraries.addAll(allLibraries.filter { library ->
                !library.optString("NAME").contains("TEST", ignoreCase = true) &&
                    LibraryMetadata.isActive(library)
            })
        }
        updateSpinnerWithFilteredData()
    }
    
    private fun updateSpinnerWithFilteredData() {
        // The legacy spinner is not visible. Never let its automatic first-item
        // callback count as a user selection after redeeming a license.
        librarySpinner.onItemSelectedListener = null
        val labels = filteredLibraries.map { it.optString("NAME") }
        librarySpinner.adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, labels
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
    }

    fun handleSelectedJsonData(jsonObject: JSONObject) {
        val nextLibrary = SelectedLibrary.fromJson(jsonObject)
        if (licenseVerified && nextLibrary.type != redeemedGame) {
            showToast("This library is not assigned to your redeemed game")
            return
        }
        val switchedWithinGame = licenseVerified && selectedLibrary.name != nextLibrary.name
        selectedLibrary = nextLibrary
        val maintenance = !LibraryMetadata.isActive(jsonObject)
        findViewById<TextView>(R.id.library_name).text = selectedLibrary.name
        findViewById<TextView>(R.id.library_game).text = LibraryMetadata.gameLabel(jsonObject)
        findViewById<TextView>(R.id.library_version).apply {
            text = LibraryMetadata.versionLabel(jsonObject)
            visibility = if (text.isNullOrBlank()) View.GONE else View.VISIBLE
        }
        findViewById<TextView>(R.id.library_package).apply {
            text = selectedLibrary.gamePackage
            visibility = if (selectedLibrary.gamePackage.isBlank()) View.GONE else View.VISIBLE
        }
        findViewById<TextView>(R.id.library_status).apply {
            text = when {
                maintenance -> "OFFLINE"
                isReadyToInject -> "READY"
                licenseVerified -> "SELECTED"
                else -> "AVAILABLE"
            }
            setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    if (maintenance) R.color.loader_status_maintenance else R.color.loader_status_active
                )
            )
            background = null
        }
        if (maintenance) {
            isPreparingLibrary = false
            isReadyToInject = false
            loginButton.text = "UNAVAILABLE"
            loginButton.isEnabled = false
        } else {
            loginButton.isEnabled = true
            if (licenseVerified) {
                loginButton.text = if (isReadyToInject) "INJECT" else "PREPARE LIBRARY"
            } else if (!isReadyToInject) {
                loginButton.text = "REDEEM KEY"
            }
        }
        val logoUrl = jsonObject.optString("LOGO")
        if (logoUrl.isNotBlank()) {
            Glide.with(this)
                .load(logoUrl)
                .placeholder(R.drawable.shai_mark)
                .error(R.drawable.shai_mark)
                .into(appBanner)
        }
        
        refreshLibraryDrawer()
        updateTheme(phaseProgress)
        if (switchedWithinGame) {
            // Never reuse a previously downloaded .so for another catalog entry.
            isPreparingLibrary = false
            isReadyToInject = false
            remoteLibrarySize = 0
            File(cacheDir, LIBRARY_FILE_NAME).delete()
            findViewById<TextView>(R.id.success_detail).text = "Selected ${selectedLibrary.name}. Tap Prepare library when you are ready."
            loginButton.text = "PREPARE LIBRARY"
            loginButton.isEnabled = true
        } else if (licenseVerified) {
            findViewById<TextView>(R.id.success_detail).text = "Selected ${selectedLibrary.name}. Tap Prepare library when you are ready."
            if (!isReadyToInject) {
                loginButton.text = "PREPARE LIBRARY"
                loginButton.isEnabled = true
            }
        }
    }

    private fun verifySelectedLibraryAndDownload() {
        val key = redeemedKey ?: return
        val game = redeemedGame ?: return
        val targetName = selectedLibrary.name
        isPreparingLibrary = true
        loginButton.text = "VERIFYING…"
        loginButton.isEnabled = false
        librarySpinner.isEnabled = false
        lifecycleScope.launch(Dispatchers.IO) {
            val result = licenseValidator.validate(key, PANEL_URL, game)
            withContext(Dispatchers.Main) {
                if (targetName != selectedLibrary.name || !licenseVerified) return@withContext
                if (!licenseValidator.isSuccess(result)) {
                    resetLoginState(result)
                } else {
                    authenticatedLibrary = targetName
                    findViewById<TextView>(R.id.success_detail).text = "Preparing $targetName…"
                    proceedWithDownload()
                }
            }
        }
    }
    
    private fun showVersionMismatchDialog(message: String, updateUrl: String?) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this, R.style.DarkAlertDialog)
                .setTitle("Update required")
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Exit") { _, _ ->
                    finishAffinity()
                }

            if (!updateUrl.isNullOrEmpty()) {
                builder.setPositiveButton("Update") { _, _ ->
                        try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(updateUrl)
                            )
                        )
                        finishAffinity()
                    } catch (e: Exception) {
                        finishAffinity()
                    }
                }
            }

            builder.show()
        }
    }
 
    private fun getAppVersionCode(): Int {
        return try {
            packageManager.getPackageInfo(packageName, 0).longVersionCode.toInt()
        } catch (e: Exception) {
            0
        }
    }

    private fun validateLicense(licenseKey: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            // The server selects the game based on keys_code.game, never a
            // user-selected label. Only that game's entries are returned.
            val result = catalogClient.redeem(licenseKey, licenseValidator.deviceSerial())
            val redemption = result.getOrElse { error ->
                withContext(Dispatchers.Main) {
                    resetLoginState(error.message ?: "Unable to redeem this key")
                }
                return@launch
            }
            val game = redemption.optString("game")
            val catalog = redemption.optJSONObject("catalog")
            val items = catalog?.optJSONArray("DATA")
            val validationResult = licenseValidator.validate(licenseKey, PANEL_URL, game)
            withContext(Dispatchers.Main) {
                if (!licenseValidator.isSuccess(validationResult)) {
                    redemptionInFlight = false
                    resetLoginState(validationResult)
                    return@withContext
                }
                if (items == null) {
                    resetLoginState("Invalid library catalog response")
                    return@withContext
                }
                allLibraries.clear()
                for (i in 0 until items.length()) {
                    val item = items.optJSONObject(i) ?: continue
                    if (item.optString("TYPE") == game) allLibraries.add(item)
                }
                redemptionInFlight = false
                redeemedGame = game
                redeemedKey = licenseKey
                licenseVerified = true
                // Populate only matching library entries. The spinner and drawer
                // now reflect the authenticated game's group.
                filterLibraries(false)
                findViewById<View>(R.id.license_section).visibility = View.GONE
                findViewById<View>(R.id.success_section).apply {
                    visibility = View.VISIBLE
                    // The plaque is revealed by the phase timeline in updateTheme,
                    // so it rises with the court instead of racing it with a fade.
                    if (!ValueAnimator.areAnimatorsEnabled()) alpha = 1f
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    loginButton.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                }
                findViewById<TextView>(R.id.hero_caption).text =
                    "Choose one library. Nothing downloads until you confirm."
                findViewById<TextView>(R.id.success_title).text = "Key verified."
                if (rememberKeyCheck.isChecked) {
                    loaderPreferences.saveRememberedKey(licenseKey)
                    pendingRememberedKey = null
                }
                keyFingerprint.apply {
                    text = keyLabel(licenseKey)
                    visibility = View.VISIBLE
                }
                animateToPhase(1f)
                // The signed-in affordances and live controls arrive with the plaque.
                setSignedInControlsVisible(true)
                setInjectionControlsEnabled(true)
                if (filteredLibraries.isEmpty()) {
                    findViewById<TextView>(R.id.success_detail).text =
                        "No active libraries are currently published for ${redemption.optString("game_label", game)}."
                    loginButton.text = "NO LIBRARIES AVAILABLE"
                    loginButton.isEnabled = false
                    refreshLibraryDrawer()
                    showToast("No available libraries for this key")
                    schedulePostAuthReveal(openDrawer = false)
                } else {
                    selectedLibrary = SelectedLibrary()
                    findViewById<TextView>(R.id.library_name).text = "Choose your library"
                    findViewById<TextView>(R.id.library_game).text = redemption.optString("game_label", game)
                    findViewById<TextView>(R.id.library_version).visibility = View.GONE
                    findViewById<TextView>(R.id.library_package).visibility = View.GONE
                    findViewById<TextView>(R.id.library_status).text = "CHOOSE LIBRARY"
                    findViewById<TextView>(R.id.success_detail).text = "Choose one library to continue."
                    loginButton.text = "CHOOSE LIBRARY"
                    loginButton.isEnabled = true
                    appBanner.isEnabled = true
                    refreshLibraryDrawer()
                    // Selection is explicit. The drawer opens only after the
                    // signed-in plaque has had time to land.
                    schedulePostAuthReveal(openDrawer = true)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    
    private fun saveSelectedLibrary(libraryName: String) {
        loaderPreferences.licenses.saveSelectedLibrary(libraryName)
    }

    private fun resetLoginState(message: String, focusKey: Boolean = true) {
        val savedKeyRejected = autoLoginStarted && pendingRememberedKey != null
        pendingRememberedKey = null
        isPreparingLibrary = false
        clearAuthenticatedPhase()
        licenseKeyInputLayout.error = message
        findViewById<TextView>(R.id.hero_caption).text = if (savedKeyRejected) {
            "The saved key was not accepted. Check it, or turn off Remember."
        } else {
            "Check the key and try again."
        }
        loginButton.text = "REDEEM KEY"
        loginButton.isEnabled = true
        licenseKeyInput.isEnabled = true
        librarySpinner.isEnabled = true
        setInjectionControlsEnabled(true)
        isReadyToInject = false
        if (focusKey) {
            revealKeyEntry()
        }
    }

    /** Return an error to the field that caused it, without stealing focus for
     *  download or preparation failures that the key cannot fix. */
    private fun revealKeyEntry() {
        hideKeyboard()
        val target = findViewById<View>(R.id.license_section)
        mainScroll.post {
            if (licenseVerified) return@post
            licenseKeyInput.requestFocus()
            mainScroll.smoothScrollTo(0, target.top)
        }
    }

    private fun hideKeyboard() {
        val manager = getSystemService(InputMethodManager::class.java) ?: return
        manager.hideSoftInputFromWindow(licenseKeyInput.windowToken, 0)
    }

    /** Hold the drawer until the success plaque and crest are legible. The
     *  reveal is cancelled when the user leaves the signed-in phase. */
    private fun schedulePostAuthReveal(openDrawer: Boolean) {
        pendingReveal?.let { mainScroll.removeCallbacks(it) }
        val delay = if (ValueAnimator.areAnimatorsEnabled()) {
            ShaiMotion.DRAWER_REVEAL_DELAY_MS
        } else {
            ShaiMotion.REDUCED_DRAWER_DELAY_MS
        }
        val action = Runnable {
            pendingReveal = null
            if (!licenseVerified || isFinishing) return@Runnable
            if (openDrawer) {
                setLibraryDrawerOpen(true)
            } else {
                mainScroll.smoothScrollTo(0, findViewById<View>(R.id.success_section).top)
            }
        }
        pendingReveal = action
        mainScroll.postDelayed(action, delay)
    }

    /** The library selector, mode/delay controls and browse action belong to the
     *  signed-in court; the unauthenticated sheet shows only the key form. */
    private fun setSignedInControlsVisible(visible: Boolean) {
        val views = listOf(
            findViewById<View>(R.id.library_card),
            findViewById<View>(R.id.operation_section),
            findViewById<View>(R.id.browse_button)
        )
        if (visible) {
            val density = resources.displayMetrics.density
            views.forEachIndexed { index, view ->
                view.animate().cancel()
                view.visibility = View.VISIBLE
                if (!ValueAnimator.areAnimatorsEnabled()) {
                    view.alpha = 1f
                    view.translationY = 0f
                    return@forEachIndexed
                }
                view.alpha = 0f
                view.translationY = 12f * density
                view.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setStartDelay(120L + index * ShaiMotion.ROW_STAGGER_MS)
                    .setDuration(ShaiMotion.ROW_MS)
                    .setInterpolator(ShaiMotion.easeOut)
                    .start()
            }
        } else {
            views.forEach { view ->
                view.animate().cancel()
                view.alpha = 1f
                view.translationY = 0f
                view.visibility = View.GONE
            }
        }
    }

    /** One place owns enablement and the visual state of the injection controls,
     *  so a disabled spinner and mode row read as disabled instead of inert. */
    private fun setInjectionControlsEnabled(enabled: Boolean) {
        listOf(delaySpinner, methodSpinner, radioGroup).forEach { view ->
            view.isEnabled = enabled
            view.alpha = if (enabled) 1f else .45f
        }
    }

    private fun proceedWithDownload() {
        val currentLibrary = selectedLibrary.name
        isPreparingLibrary = true
        librarySpinner.isEnabled = false
        if (selectedLibrary.type != redeemedGame) {
            resetLoginState("This library is not assigned to your key", focusKey = false)
            return
        }
        if (selectedLibrary.downloadUrl.isBlank()) {
            resetLoginState("Library download URL unavailable", focusKey = false)
            return
        }

        val detectedVpn = SplashActivity.getDetectedVPN()
        if (detectedVpn == null) {
            downloadLibraryIfNeeded(this@MainActivity, selectedLibrary.downloadUrl, LIBRARY_FILE_NAME) { success ->
                if (currentLibrary != selectedLibrary.name || !licenseVerified) return@downloadLibraryIfNeeded
                if (success) {
                    showToast("Download successful")
                    assetExtractor.extractAll()
                    if (!isLibraryReady()) {
                        resetLoginState("FAILED TO LOAD RUNTIME LIBRARY", focusKey = false)
                        isReadyToInject = false
                        isPreparingLibrary = false
                    } else {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(2500)
                            if (currentLibrary != selectedLibrary.name || !licenseVerified) return@launch
                            loginButton.text = "INJECT"
                            findViewById<TextView>(R.id.success_detail).text = "$currentLibrary is ready. Tap Inject when you are ready."
                            showToast("Ready to inject")
                            loginButton.isEnabled = true
                            setInjectionControlsEnabled(true)
                            isReadyToInject = true
                            isPreparingLibrary = false
                            librarySpinner.isEnabled = true
                        }
                    }
                } else {
                    resetLoginState("FAILED TO DOWNLOAD, \nRETRY AGAIN.", focusKey = false)
                    isReadyToInject = false
                }
            }
        }
    }

    private fun isLibraryReady(): Boolean {
        val libraryFile = File(cacheDir, LIBRARY_FILE_NAME)
        return libraryFile.isFile && libraryFile.length() > 0L
    }
    
    private fun injectLibrary(libraryFileName: String) {
        val appCacheDir = cacheDir.absolutePath
        val outputFile = File(appCacheDir, libraryFileName)
        if (!outputFile.exists() || outputFile.length().toInt() != remoteLibrarySize) {
            return
        }
        
        val cacheDirectory = File(cacheDir.absolutePath)
        cacheDirectory.setReadable(true, false)
        cacheDirectory.setWritable(true, false)
        cacheDirectory.setExecutable(true, false)
        cacheDirectory.listFiles()?.forEach { file ->
            file.setExecutable(true, false)
        }
        if (!isLibraryReady()) {
            resetLoginState("FAILED TO LOAD RUNTIME LIBRARY", focusKey = false)
            return
        }
        val injectionCommand = InjectionCommandBuilder.build(
            cacheDirectory = cacheDirectory,
            library = selectedLibrary,
            libraryFileName = libraryFileName,
            injectionMethod = injectionMethod,
            hasRootAccess = commandRunner.hasRootAccess()
        )
        if (loaderPreferences.isAutoMode()) {
            launchGame(selectedLibrary.gamePackage)
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000)
                delay(delaySeconds * 1000L)
                commandRunner.run(injectionCommand)
                scheduleInjectionStatus()
            }
        } else {
            commandRunner.run(injectionCommand)
            scheduleInjectionStatus()
        }
    }
    
    private fun scheduleInjectionStatus() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(3000)
            showToast("Injection completed")
            loginButton.text = "RE-INJECT"
            delay(5000)
            loginButton.text = "RE-INJECT"
            loginButton.isEnabled = true
            setInjectionControlsEnabled(true)
        }
    }
    
    private fun launchGame(packageName: String): Boolean {
        return try {
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            if (intent != null) {
                startActivity(intent)
                true
            } else {
                Toast.makeText(this, "Game not installed!", Toast.LENGTH_SHORT).show()
                false
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to start game!", Toast.LENGTH_SHORT).show()
            false
        }
    }
    
    private fun downloadLibraryIfNeeded(
        context: Context,
        url: String,
        libraryFileName: String,
        callback: (Boolean) -> Unit
    ) {
        val libraryDownloader = LibraryDownloader(context)
        val outputFile = File(context.cacheDir, libraryFileName)
        lifecycleScope.launch(Dispatchers.IO) {
            var success = false
            try {
                val connection = URL(url).openConnection()
                try {
                    connection.connect()
                    remoteLibrarySize = connection.contentLength
                    if (remoteLibrarySize >= 9999) {
                        val needToDownload = if (outputFile.exists()) {
                            val localFileSize = outputFile.length().toInt()
                            localFileSize != remoteLibrarySize
                        } else {
                            true
                        }

                        if (!needToDownload) {
                            success = true
                        } else {
                            withContext(Dispatchers.Main) {
                                showToast("Downloading updates...")
                                progressBar.visibility = View.VISIBLE
                                progressBar.progress = 0
                            }
                            success = libraryDownloader.download(url, libraryFileName) { progress ->
                                runOnUiThread {
                                    progressBar.progress = progress
                                }
                            }
                        }
                    }
                } finally {
                    (connection as? HttpURLConnection)?.disconnect()
                }
            } catch (_: Exception) {
            }

            withContext(Dispatchers.Main) {
                callback(success)
                if (::progressBar.isInitialized) {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }
    
    private fun updateRootStatus() {
        lifecycleScope.launch(Dispatchers.IO) {
            val isRooted = commandRunner.hasRootAccess()
            withContext(Dispatchers.Main) {
                if (isRooted) {
                    rootStatusView.text = "ROOTED ENVIRONMENT"
                    rootStatusView.setTextColor(Color.GREEN)
                    rootStatusView.visibility = View.VISIBLE
                } else {
                    rootStatusView.visibility = View.GONE
                }
            }
        }
    }
    
    private fun cleanUp() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
        LibraryDownloader.cancelNotification(this)
    }
    
    override fun onStop() {
        super.onStop()
        pendingReveal?.let { mainScroll.removeCallbacks(it) }
        pendingReveal = null
        if (isFinishing) {
            cleanUp()
        }
    }

    override fun onDestroy() {
        phaseAnimator?.cancel()
        entranceAnimator?.cancel()
        pendingReveal?.let { mainScroll.removeCallbacks(it) }
        pendingReveal = null
        super.onDestroy()
        cleanUp()
    }
}
