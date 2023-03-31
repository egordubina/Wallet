package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.ui.uistate.LoginScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginScreenUiState> =
        MutableStateFlow(LoginScreenUiState.Loading)
    val uiState: StateFlow<LoginScreenUiState> = _uiState
    private var job: Job? = null

    init {
        job?.cancel()
        job = viewModelScope.launch {
            _uiState.value = LoginScreenUiState.Loading
            try {
                userRepository.userInfo.collect { user ->
                    _uiState.value = LoginScreenUiState.Content(
                        userName = user.userName,
                        userPinCode = user.userPin
                    )
                }
            } catch (e: Exception) {
                _uiState.value = LoginScreenUiState.Error
            }
        }
    }

    fun loginUser(userPin: String, userPinCode: String) {
        _uiState.value = LoginScreenUiState.Loading
        job?.cancel()
        job = viewModelScope.launch {
            when {
                userPin.length !in 4..8 -> _uiState.value = LoginScreenUiState.Error
                userPin.isEmpty() -> _uiState.value = LoginScreenUiState.Error
                userPin != userPinCode -> LoginScreenUiState.InCorrectPinCode
                else -> _uiState.value = LoginScreenUiState.Success
            }
        }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = extras[APPLICATION_KEY]
                return LoginViewModel(
                    userRepository = (application as WalletApplication).userRepository
                ) as T
            }
        }
    }
}
