package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel

class UserViewModel(/*private val userRepository: UserRepository*/) : ViewModel() {
    var userIsLogin: Boolean = false

//    companion object {
//        @Suppress("UNCHECKED_CAST")
//        val Factory = object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//                val application = checkNotNull(extras[APPLICATION_KEY])
//                return UserViewModel(
//                    (application as WalletApplication).userRepository
//                ) as T
//            }
//        }
//    }
}