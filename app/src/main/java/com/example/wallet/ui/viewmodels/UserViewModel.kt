package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.preferences.WalletPreferences

class UserViewModel(
    private val walletPreferences: WalletPreferences
) : ViewModel() {

    fun isFirstLogin(): Boolean = walletPreferences.isFirstLogin

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return UserViewModel(
                    (application as WalletApplication).walletPreferences
                ) as T
            }
        }
    }
}