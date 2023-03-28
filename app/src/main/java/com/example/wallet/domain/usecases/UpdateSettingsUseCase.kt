package com.example.wallet.domain.usecases

import com.example.wallet.data.models.SettingsIds
import com.example.wallet.data.models.SettingsIds.*
import com.example.wallet.data.preferences.WalletPreferences

class UpdateSettingsUseCase(private val walletPreferences: WalletPreferences) {
    fun updateSetting(setting: SettingsIds, value: String) {
        when (setting) {
            USER_NEW -> walletPreferences.isFirstLogin = value.toBooleanStrict()
            USER_NAME -> walletPreferences.userName = value
            USE_FINGERPRINT_TO_LOGIN -> walletPreferences.fingerPrintLogin = value.toBooleanStrict()
            USER_PIN -> walletPreferences.userPin = value.toInt()
            USER_EMAIL -> walletPreferences.userEmail = value
            USER_IS_LOGIN -> walletPreferences.userIsLogin = value.toBooleanStrict()
        }
    }
}
