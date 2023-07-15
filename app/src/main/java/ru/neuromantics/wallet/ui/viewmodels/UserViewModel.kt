package ru.neuromantics.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import ru.neuromantics.wallet.data.preferences.WalletPreferences

class UserViewModel(private val walletPreferences: WalletPreferences) : ViewModel() {
    val isFirstLogin: Boolean
        get() = walletPreferences.isFirstLogin
    val pinCode: String
        get() = walletPreferences.userPin
    var userIsLogin: Boolean = false

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return UserViewModel(
                    (application as ru.neuromantics.wallet.WalletApplication).walletPreferences
                ) as T
            }
        }
    }
}