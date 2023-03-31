package com.example.wallet.ui.uistate

sealed class RegistrationScreenUiState {
    object Loading : RegistrationScreenUiState()
    object Content : RegistrationScreenUiState()
    object RegistrationFailed : RegistrationScreenUiState()
    object RegistrationFailedIncorrectPin : RegistrationScreenUiState()
    object RegistrationFailedIncorrectName : RegistrationScreenUiState()
    object RegistrationFailedIncorrectEmail : RegistrationScreenUiState()
    object RegistrationSuccessful : RegistrationScreenUiState()
}