package com.example.wallet.ui.uistate

data class SettingsScreenUiState(
    val userName: String,
    val fingerprintLogin: Boolean,
    val pinCodeToLogin: String,
    val userEmail: String
)