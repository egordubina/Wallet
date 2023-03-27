package com.example.wallet.domain.usecases

import com.example.wallet.data.models.Settings
import com.example.wallet.data.models.Settings.*
import com.example.wallet.data.preferences.WalletPreferences

class UpdateSettingsUseCase(private val walletPreferences: WalletPreferences) {
    fun updateSetting(setting: Settings, value: String) {
        when (setting) {
            USER_NEW -> walletPreferences.isFirstLogin = value.toBooleanStrict()
            USER_NAME -> walletPreferences.userName = value
            USE_FINGERPRINT_TO_LOGIN -> walletPreferences.fingerPrintLogin = value.toBooleanStrict()
            USER_PIN -> {}
            USER_EMAIL -> {}
        }
    }
}
