package com.example.wallet.data.preferences

import android.content.Context
import androidx.preference.PreferenceManager
import com.example.wallet.data.models.SettingsIds.USER_NEW

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
        get() = preferencesManager.getBoolean(USER_NEW.id, true)
        set(value) = editPreferences.putBoolean(USER_NEW.id, value).apply()
}
