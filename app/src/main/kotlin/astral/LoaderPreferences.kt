package astral.loader

import android.content.Context

class LoaderPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    val licenses = LicenseStore(preferences)

    fun getTheme(): String = preferences.getString("appearance", "pastel")?.takeIf { it in setOf("pastel", "midnight", "notebook") } ?: "pastel"
    fun saveTheme(theme: String) {
        require(theme in setOf("pastel", "midnight", "notebook"))
        preferences.edit().putString("appearance", theme).apply()
    }

    fun getMode(): String = preferences.getString(KEY_MODE, MODE_AUTO) ?: MODE_AUTO

    /** Auto is the source of truth at injection time; the visible mode boxes
     *  and this preference are kept in sync when the user ticks one. */
    fun isAutoMode(): Boolean = getMode() == MODE_AUTO

    fun saveMode(mode: String) {
        preferences.edit().putString(KEY_MODE, mode).apply()
    }

    fun getDelaySeconds(): Int = preferences.getInt(KEY_DELAY, 0)

    fun saveDelaySeconds(seconds: Int) {
        preferences.edit().putInt(KEY_DELAY, seconds).apply()
    }

    fun getInjectionMethod(): String = preferences.getString(KEY_METHOD, DEFAULT_METHOD) ?: DEFAULT_METHOD

    fun saveInjectionMethod(method: String) {
        preferences.edit().putString(KEY_METHOD, method).apply()
    }

    /*
     * Explicit opt-in only. The key is kept in app-private preferences, the same
     * store the per-library license map already uses; it is never sent anywhere
     * except the panel that issued it. Turning the toggle off deletes it.
     */
    fun isRememberKeyEnabled(): Boolean = preferences.getBoolean(KEY_REMEMBER_ENABLED, false)

    fun setRememberKeyEnabled(enabled: Boolean) {
        preferences.edit().putBoolean(KEY_REMEMBER_ENABLED, enabled).apply()
        if (!enabled) clearRememberedKey()
    }

    fun getRememberedKey(): String? {
        if (!isRememberKeyEnabled()) return null
        return preferences.getString(KEY_REMEMBERED_KEY, null)
    }

    fun saveRememberedKey(key: String) {
        preferences.edit()
            .putString(KEY_REMEMBERED_KEY, key)
            .putBoolean(KEY_REMEMBER_ENABLED, true)
            .apply()
    }

    fun clearRememberedKey() {
        preferences.edit().remove(KEY_REMEMBERED_KEY).apply()
    }

    private companion object {
        const val PREFERENCES_NAME = "loaderPrefs"
        const val KEY_MODE = "injection_mode"
        const val KEY_DELAY = "injection_delay"
        const val KEY_METHOD = "injection_method"
        const val KEY_REMEMBER_ENABLED = "remember_key_enabled"
        const val KEY_REMEMBERED_KEY = "remembered_license_key"
        const val MODE_AUTO = "auto"
        const val DEFAULT_METHOD = "1"
    }
}
