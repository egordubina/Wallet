package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.ui.uistate.ChangePinUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChangePinViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<ChangePinUiState> = MutableStateFlow(ChangePinUiState.Loading)
    val uiState: StateFlow<ChangePinUiState> = _uiState.asStateFlow()
    private var job: Job? = null
    private var currentUserPin: String? = null
    init {
        job?.cancel()
        _uiState.value = ChangePinUiState.Loading
        job = viewModelScope.launch {
            try {
                userRepository.userInfo.collect {
                    _uiState.value = ChangePinUiState.Content
                    currentUserPin = it.userPin
                }
            } catch (e: Exception) {
                _uiState.value = ChangePinUiState.Error
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ChangePinViewModel(
                    (application as WalletApplication).userRepository
                ) as T
            }
        }
    }

}
