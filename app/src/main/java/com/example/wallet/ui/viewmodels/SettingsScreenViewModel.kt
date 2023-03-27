package com.example.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.models.Settings
import com.example.wallet.data.models.Settings.*
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.domain.usecases.UpdateSettingsUseCase
import com.example.wallet.ui.uistate.SettingsScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val walletPreferences: WalletPreferences) : ViewModel() {
    fun changeSettings(
        settingsId: Settings,
        value: Any
    ) {
        UpdateSettingsUseCase(walletPreferences).updateSetting(settingsId, value.toString())
    }

    private val _uiState = MutableLiveData<SettingsScreenUiState>(SettingsScreenUiState.Loading)
    val uiState: LiveData<SettingsScreenUiState> = _uiState

    private var job: Job? = null

    init {
        job?.cancel()
        _uiState.value = SettingsScreenUiState.Loading
        job = viewModelScope.launch {
            try {
                _uiState.postValue(
                    SettingsScreenUiState.Content(
                        userName = walletPreferences.userName,
                        fingerprintLogin = walletPreferences.fingerPrintLogin,
                        userEmail = walletPreferences.userEmail,
                        pinCodeToLogin = walletPreferences.userPin
                    )
                )
            } catch (e: Exception) {
            }
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsScreenViewModel(
                    (application as WalletApplication).walletPreferences
                ) as T
            }
        }
    }
}