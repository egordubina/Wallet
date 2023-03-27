package com.example.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.ui.models.User

class UserViewModel(
    private val walletPreferences: WalletPreferences
) : ViewModel() {

    val isFirstLogin: Boolean = walletPreferences.isFirstLogin

    fun registrationUser(name: String, phoneNumber: String, pin: Int) {
        walletPreferences.userRegistration(
            name = name,
            phoneNumber = phoneNumber,
            pin = pin
        )
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _name = MutableLiveData<String>()
    private val _photo = MutableLiveData<String>()
    val name: LiveData<String> = _name
    val photo: LiveData<String> = _photo

    companion object {
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