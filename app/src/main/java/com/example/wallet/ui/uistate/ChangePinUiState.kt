package com.example.wallet.ui.uistate

sealed class ChangePinUiState {
    object Loading : ChangePinUiState()
    object Error : ChangePinUiState()
    object Content : ChangePinUiState()
    object IncorrectPin : ChangePinUiState()
    object Success : ChangePinUiState()
}
