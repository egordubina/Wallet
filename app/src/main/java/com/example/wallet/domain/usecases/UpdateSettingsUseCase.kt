package com.example.wallet.domain.usecases

//import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.data.database.UserDao
import com.example.wallet.data.models.SettingsIds

class UpdateSettingsUseCase(private val userDao: UserDao) {
    fun updateSetting(setting: SettingsIds, value: String) {
//        when (setting) {
//            USER_NEW -> walletPreferences.isFirstLogin = value.toBooleanStrict()
//            USER_NAME -> { walletPreferences.userName = flow { emit(value) } }
//            USE_FINGERPRINT_TO_LOGIN -> walletPreferences.fingerPrintLogin = value.toBooleanStrict()
//            USER_PIN -> walletPreferences.userPin = value
//            USER_EMAIL -> walletPreferences.userEmail = value
//        }
    }
}
