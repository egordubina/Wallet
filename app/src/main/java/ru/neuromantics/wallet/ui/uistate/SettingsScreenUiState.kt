package ru.neuromantics.wallet.ui.uistate

sealed class SettingsScreenUiState {
    data class Content(
        val userName: String,
        val fingerprintLogin: Boolean,
        val pinCodeToLogin: String,
        val userEmail: String
    ) : SettingsScreenUiState()

    object Loading : SettingsScreenUiState()
    object Error : SettingsScreenUiState()
}