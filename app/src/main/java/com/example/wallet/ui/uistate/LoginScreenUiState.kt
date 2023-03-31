package com.example.wallet.ui.uistate


sealed class LoginScreenUiState {
    object Loading : LoginScreenUiState()
    object Success : LoginScreenUiState()
    object InCorrectPinCode : LoginScreenUiState()
    object Error : LoginScreenUiState()

    data class Content(
        val userName: String,
        val userPinCode: String
    ) : LoginScreenUiState()
}