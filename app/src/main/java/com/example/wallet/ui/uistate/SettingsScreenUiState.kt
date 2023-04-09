package com.example.wallet.ui.uistate

sealed class SettingsScreenUiState {
    object Loading : SettingsScreenUiState()
    object Error : SettingsScreenUiState()
    data class Content(
        val userName: String,
        val fingerprintLogin: Boolean,
        val userEmail: String
    ) : SettingsScreenUiState()
}
