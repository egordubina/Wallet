package com.example.wallet.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.wallet.data.models.UserSettingsIds

// todo: change

const val SETTINGS_USER_NEW: String = "NEW_USER"

/**
 * Класс для общения с sharedPreferences
 * @param context Контекст для создания [preferencesManager]
 * */
class WalletPreferences(context: Context) {

    /**
     * Менеджер, через который проходят все операции с sharedPreferences
     */
    private val preferencesManager =
        PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
    private val editPreferences = preferencesManager.edit()

    /**
     * Использует [preferencesManager] для общения с sharedPreferences.
     * @return *true*, если пользователь первый раз заходит в приложение, иначе *false*
     * */
    var isFirstLogin: Boolean
        get() = preferencesManager.getBoolean(SETTINGS_USER_NEW, true)
        set(value) = editPreferences.putBoolean(SETTINGS_USER_NEW, value).apply()

    var useFingerprintToLogin: Boolean
        get() = preferencesManager.getBoolean(UserSettingsIds.USER_FINGERPRINT.id, true)
        set(value) = editPreferences.putBoolean(UserSettingsIds.USER_FINGERPRINT.id, value).apply()
}
