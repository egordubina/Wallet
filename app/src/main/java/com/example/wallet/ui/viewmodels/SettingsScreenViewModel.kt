package com.example.wallet.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.models.User
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.ui.uistate.SettingsScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val userRepository: UserRepository,
    private val walletPreferences: WalletPreferences
) : ViewModel() {

    private val _uiState: MutableStateFlow<SettingsScreenUiState> =
        MutableStateFlow(SettingsScreenUiState.Loading)
    val uiState: StateFlow<SettingsScreenUiState> = _uiState.asStateFlow()
    private var job: Job? = null

    init {
        job?.cancel()
        _uiState.value = SettingsScreenUiState.Loading
        job = viewModelScope.launch {
            try {
                val user = userRepository.userInfo
                user.collect { userInfo ->
                    _uiState.value = SettingsScreenUiState.Content(
                        userName = userInfo.userName,
                        fingerprintLogin = walletPreferences.useFingerprintToLogin,
                        userEmail = userInfo.userEmail,
                        userPin = userInfo.userPin
                    )
                }
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
                _uiState.value = SettingsScreenUiState.Error
            }
        }
    }

    // TODO: перепроверить функцию сохранения
    fun saveSettings(
        userName: String,
        userEmail: String,
        useFingerprintToLogin: Boolean,
        userPin: String
    ) {
        val user = User(
            userName = userName,
            userEmail = userEmail,
            userPin = userPin
        )
        viewModelScope.launch {
            userRepository.updateUser(user)
        }
        walletPreferences.useFingerprintToLogin = useFingerprintToLogin
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsScreenViewModel(
                    (application as WalletApplication).userRepository,
                    application.walletPreferences
                ) as T
            }
        }
    }
}