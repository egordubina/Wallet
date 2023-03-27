package com.example.wallet.ui.uistate

sealed class SettingsScreenUiState {
    data class Content(
        val userName: String,
        val fingerprintLogin: Boolean,
        val pinCodeToLogin: Int,
        val userEmail: String
    ) : SettingsScreenUiState()

    object Loading : SettingsScreenUiState()
}