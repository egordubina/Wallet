package com.example.wallet.ui.uistate

import com.example.wallet.ui.models.Transaction

sealed class HomeScreenUiState {
    object Loading : HomeScreenUiState()

    data class Content(
        val userName: String,
        val transactionsList: List<Transaction>,
        val currentMonthExpanses: Int,
        val currentMonthIncomes: Int,
    ) : HomeScreenUiState()
}