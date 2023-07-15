package ru.neuromantics.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ru.neuromantics.wallet.data.preferences.WalletPreferences
import ru.neuromantics.wallet.domain.usecases.RegistrationUserUseCase
import ru.neuromantics.wallet.ui.uistate.RegistrationScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RegistrationScreenViewModel(
    private val walletPreferences: WalletPreferences
) : ViewModel() {
    private val _uiState = MutableLiveData<RegistrationScreenUiState>(RegistrationScreenUiState.Content)
    val uiState: LiveData<RegistrationScreenUiState> = _uiState
    private var job: Job? = null

    fun registrationUser(name: String, email: String, pin: String) {
        job?.cancel()
        _uiState.value = RegistrationScreenUiState.Loading
        job = viewModelScope.launch {
            try {
                RegistrationUserUseCase(walletPreferences).registrationUser(name, email, pin)
                _uiState.postValue(RegistrationScreenUiState.RegistrationSuccessful)
            } catch (e: Exception) {
                _uiState.postValue(RegistrationScreenUiState.RegistrationFailed)
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return RegistrationScreenViewModel(
                    (application as ru.neuromantics.wallet.WalletApplication).walletPreferences
                ) as T
            }
        }
    }
}