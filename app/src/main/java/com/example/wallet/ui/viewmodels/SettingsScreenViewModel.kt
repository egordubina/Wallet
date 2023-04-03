package com.example.wallet.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.models.SettingsIds
import com.example.wallet.data.models.SettingsIds.*
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.ui.uistate.SettingsScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<SettingsScreenUiState> =
        MutableStateFlow(SettingsScreenUiState.Loading)

    val uiState: StateFlow<SettingsScreenUiState> = _uiState
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
                        fingerprintLogin = false,
                        userEmail = userInfo.userEmail,
                        pinCodeToLogin = ""
                    )
                }
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
                _uiState.value = SettingsScreenUiState.Error
            }
        }
    }

    fun updateSettings(settings: Map<SettingsIds, String>) {
//        val user = User(
//            userName = settings[USER_NAME],
//            userEmail = settings[USER_EMAIL],
//            userPin = settings[USER_PIN],
//            isFirstLogin = false
//        )
//        userRepository.updateUserInfo()
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SettingsScreenViewModel(
                    (application as WalletApplication).userRepository
                ) as T
            }
        }
    }
}