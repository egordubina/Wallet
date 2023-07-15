package ru.neuromantics.wallet.domain.usecases

import ru.neuromantics.wallet.data.models.SettingsIds
import ru.neuromantics.wallet.data.models.SettingsIds.USER_EMAIL
import ru.neuromantics.wallet.data.models.SettingsIds.USER_NAME
import ru.neuromantics.wallet.data.models.SettingsIds.USER_NEW
import ru.neuromantics.wallet.data.models.SettingsIds.USER_PIN
import ru.neuromantics.wallet.data.models.SettingsIds.USE_FINGERPRINT_TO_LOGIN
import ru.neuromantics.wallet.data.preferences.WalletPreferences

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
