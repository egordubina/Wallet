package com.example.wallet.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager

class WalletPreferences(context: Context) {
    @Suppress("UNUSED")
    companion object {
        const val USER_NEW: String = "USER_NEW"
        const val USE_FINGERPRINT_TO_LOGIN: String = "USE_FINGERPRINT_TO_LOGIN"
        const val USER_NAME: String = "USER_NAME"
        const val USER_PHONE_NUMBER: String = "USER_PHONE_NUMBER"
        const val USER_PIN: String = "USER_PIN"
    }

    private val preferencesManager =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    val isFirstLogin: Boolean
        get() = preferencesManager.getBoolean(USER_NEW, true)

    fun registrationUser(name: String, email: String, pin: Int) {
        with(preferencesManager.edit()) {
            putBoolean(USER_NEW, false)
            putString(USER_NAME, name)
            putString(USER_PHONE_NUMBER, email)
            putInt(USER_PIN, pin)
            apply()
        }
    }
}
