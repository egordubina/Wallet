package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.domain.usecases.RegistrationUserUseCase
import com.example.wallet.ui.uistate.RegistrationScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegistrationScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegistrationScreenUiState> =
        MutableStateFlow(RegistrationScreenUiState.Content)
    val uiState: StateFlow<RegistrationScreenUiState> = _uiState
    private var job: Job? = null

    fun registrationUser(name: String, email: String, pin: String) {
        job?.cancel()
        _uiState.value = RegistrationScreenUiState.Loading
        job = viewModelScope.launch {
            when {
                name.isEmpty() -> {
                    _uiState.value = RegistrationScreenUiState.RegistrationFailedIncorrectName
                    return@launch
                }

                email.isEmpty() -> {
                    _uiState.value = RegistrationScreenUiState.RegistrationFailedIncorrectEmail
                    return@launch
                }

                pin.isEmpty() -> {
                    _uiState.value = RegistrationScreenUiState.RegistrationFailedIncorrectPin
                    return@launch
                }

                pin.length !in 4..8 -> {
                    _uiState.value = RegistrationScreenUiState.RegistrationFailedIncorrectPin
                    return@launch
                }

                else -> {
                    try {
                        pin.toInt()
                    } catch (e: Exception) {
                        _uiState.value = RegistrationScreenUiState.RegistrationFailedIncorrectPin
                        return@launch
                    }
                    try {
                        RegistrationUserUseCase(userRepository).registrationUser(name, email, pin)
                        _uiState.value = RegistrationScreenUiState.RegistrationSuccessful
                    } catch (e: Exception) {
                        _uiState.value = RegistrationScreenUiState.RegistrationFailed
                        return@launch
                    }
                }
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
                    (application as WalletApplication).userRepository
                ) as T
            }
        }
    }
}