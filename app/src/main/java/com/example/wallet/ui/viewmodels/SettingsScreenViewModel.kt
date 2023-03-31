package com.example.wallet.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.ui.uistate.SettingsScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(
        SettingsScreenUiState(
            userName = "",
            fingerprintLogin = true,
            pinCodeToLogin = "",
            userEmail = "",
        )
    )

    val uiState: StateFlow<SettingsScreenUiState> = _uiState
    private var job: Job? = null

    init {
        job?.cancel()
        _uiState.update {
            it.copy(userName = "", fingerprintLogin = true, pinCodeToLogin = "", userEmail = "")
        }
        job = viewModelScope.launch {
            try {
                val user = userRepository.userInfo
                user.collect { userInfo ->
                    _uiState.update {
                        it.copy(
                            userName = userInfo.userName,
                            fingerprintLogin = false,
                            userEmail = userInfo.userEmail,
                            pinCodeToLogin = ""
                        )
                    }
                }
            } catch (e: Exception) {
                Log.d("Exception", e.toString())
                _uiState.update {
                    it.copy(
                        userName = "",
                        fingerprintLogin = false,
                        pinCodeToLogin = "",
                        userEmail = ""
                    )
                }
            }
        }
    }

    //    }
//        UpdateSettingsUseCase(walletPreferences).updateSetting(settingsId, value)
//    ) {
//        value: String
//        settingsId: SettingsIds,
//    fun changeSettings(
    fun updateSettings(name: String) {
        viewModelScope.launch {
            userRepository.updateUserName(name)
        }
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