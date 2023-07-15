package ru.neuromantics.wallet.ui.uistate

sealed class RegistrationScreenUiState {
    object Loading : RegistrationScreenUiState()
    object Content : RegistrationScreenUiState()
    object RegistrationFailed : RegistrationScreenUiState()
    object RegistrationSuccessful : RegistrationScreenUiState()
}