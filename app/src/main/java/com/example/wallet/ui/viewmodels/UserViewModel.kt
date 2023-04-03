package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.preferences.WalletPreferences

/**
 * ViewModel для проверки логина пользователя
 * */
class UserViewModel(private val walletPreferences: WalletPreferences) : ViewModel() {
    /**
     * @return *true*, если пользователь залогинен, иначе *false*
     * */
    var userIsLogin: Boolean = false

    /**
     * @return *true*, если пользователь зашёл в приложение в первый раз, иначе *false*
     * */
    var userIsFirstLogin
        get() = walletPreferences.isFirstLogin
        set(value) {
            walletPreferences.isFirstLogin = value
        }

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