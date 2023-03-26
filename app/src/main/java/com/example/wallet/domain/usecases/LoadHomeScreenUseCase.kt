package com.example.wallet.domain.usecases

import com.example.wallet.data.preferences.WalletPreferences

class LoadHomeScreenUseCase(private val walletPreferences: WalletPreferences) {
    fun loadUserInfo(): String {
        return walletPreferences.getValue(WalletPreferences.USER_NAME, WalletPreferences.STRING)
    }
}