package ru.neuromantics.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ru.neuromantics.wallet.data.models.SettingsIds
import ru.neuromantics.wallet.data.preferences.WalletPreferences
import ru.neuromantics.wallet.domain.usecases.UpdateSettingsUseCase
import ru.neuromantics.wallet.ui.uistate.SettingsScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val walletPreferences: WalletPreferences) : ViewModel() {
    fun changeSettings(
        settingsId: SettingsIds,
        value: String
    ) {
        UpdateSettingsUseCase(walletPreferences).updateSetting(settingsId, value)
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
                _uiState.postValue(SettingsScreenUiState.Error)
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsScreenViewModel(
                    (application as ru.neuromantics.wallet.WalletApplication).walletPreferences
                ) as T
            }
        }
    }
}