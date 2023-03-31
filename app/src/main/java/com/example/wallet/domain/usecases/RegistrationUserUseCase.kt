package com.example.wallet.domain.usecases

import com.example.wallet.data.preferences.WalletPreferences

class RegistrationUserUseCase(private val walletPreferences: WalletPreferences) {
    fun registrationUser(name: String, email: String, pin: String) {
        walletPreferences.registrationUser(name, email, pin)
    }
}