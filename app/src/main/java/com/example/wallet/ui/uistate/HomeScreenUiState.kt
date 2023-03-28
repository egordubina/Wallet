package com.example.wallet.ui.uistate

sealed class HomeScreenUiState {
    object Loading : HomeScreenUiState()
    data class Error(
        val errorMessage: String,
    ) : HomeScreenUiState()

    data class Content(
        val userName: String,
    ) : HomeScreenUiState()
}
