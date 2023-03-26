package com.example.wallet.ui.viewmodels

import android.app.Activity
import android.text.Editable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.domain.usecases.LoadHomeScreenUseCase
import com.example.wallet.ui.uistate.HomeScreenUiState

class HomeScreenViewModel(walletPreferences: WalletPreferences) : ViewModel() {
    private val _uiState: MutableLiveData<HomeScreenUiState> =
        MutableLiveData(HomeScreenUiState.Loading)
    val uiState: LiveData<HomeScreenUiState> = _uiState

    init {
        val answer = LoadHomeScreenUseCase(walletPreferences).loadUserInfo()
        _uiState.value = HomeScreenUiState.Content(userName = answer)
    }

    companion object {
        fun provideFactory(activity: Activity) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return HomeScreenViewModel(
                    WalletPreferences(activity)
                ) as T
            }
        }
    }
}
