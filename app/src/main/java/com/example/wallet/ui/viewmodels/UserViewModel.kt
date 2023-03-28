package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.models.SettingsIds
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.domain.usecases.UpdateSettingsUseCase

class UserViewModel(private val walletPreferences: WalletPreferences) : ViewModel() {

    var userIsLogin: Boolean
        get() = walletPreferences.userIsLogin
        set(value) = UpdateSettingsUseCase(walletPreferences).updateSetting(SettingsIds.USER_IS_LOGIN, value.toString())
    val isFirstLogin: Boolean
        get() = walletPreferences.isFirstLogin
    val pinCode: Int
        get() = walletPreferences.userPin

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