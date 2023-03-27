package com.example.wallet.domain.usecases

import com.example.wallet.data.preferences.WalletPreferences

class LoadHomeScreenUseCase(private val walletPreferences: WalletPreferences) {
    suspend fun loadHomeScreen(): String {
        return walletPreferences.userName
    }
}