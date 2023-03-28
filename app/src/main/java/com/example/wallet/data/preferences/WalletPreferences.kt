package com.example.wallet.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.wallet.data.models.SettingsIds.*

class WalletPreferences(context: Context) {
    private val preferencesManager =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    private val editPreferences = preferencesManager.edit()

    var isFirstLogin: Boolean
        get() = preferencesManager.getBoolean(USER_NEW.id, true)
        set(value) = editPreferences.putBoolean(USER_NEW.id, value).apply()
    var userIsLogin: Boolean
        get() = preferencesManager.getBoolean(USER_IS_LOGIN.id, false)
        set(value) = editPreferences.putBoolean(USER_IS_LOGIN.id, value).apply()
    var fingerPrintLogin: Boolean
        get() = preferencesManager.getBoolean(USE_FINGERPRINT_TO_LOGIN.id, true)
        set(value) = editPreferences.putBoolean(USE_FINGERPRINT_TO_LOGIN.id, value)
            .apply()
    var userName: String
        get() = preferencesManager.getString(USER_NAME.id, "").toString()
        set(value) = editPreferences.putString(USER_NAME.id, value).apply()
    var userPin: Int
        get() = preferencesManager.getInt(USER_PIN.id, -1)
        set(value) = editPreferences.putInt(USER_PIN.id, value).apply()
    var userEmail: String
        get() = preferencesManager.getString(USER_EMAIL.id, "").toString()
        set(value) = editPreferences.putString(USER_EMAIL.id, value).apply()

    fun registrationUser(name: String, email: String, pin: Int) {
        with(editPreferences) {
            putBoolean(USER_NEW.id, false)
            putBoolean(USER_IS_LOGIN.id, true)
            putString(USER_NAME.id, name)
            putString(USER_EMAIL.id, email)
            putInt(USER_PIN.id, pin)
            apply()
        }
    }
}
