package com.example.wallet.domain.usecases

import com.example.wallet.data.models.SettingsIds
import com.example.wallet.data.models.SettingsIds.USER_EMAIL
import com.example.wallet.data.models.SettingsIds.USER_NAME
import com.example.wallet.data.models.SettingsIds.USER_NEW
import com.example.wallet.data.models.SettingsIds.USER_PIN
import com.example.wallet.data.models.SettingsIds.USE_FINGERPRINT_TO_LOGIN
import com.example.wallet.data.preferences.WalletPreferences

class UpdateSettingsUseCase(private val walletPreferences: WalletPreferences) {
    fun updateSetting(setting: SettingsIds, value: String) {
        when (setting) {
            USER_NEW -> walletPreferences.isFirstLogin = value.toBooleanStrict()
            USER_NAME -> walletPreferences.userName = value
            USE_FINGERPRINT_TO_LOGIN -> walletPreferences.fingerPrintLogin = value.toBooleanStrict()
            USER_PIN -> walletPreferences.userPin = value
            USER_EMAIL -> walletPreferences.userEmail = value
        }
    }
}
