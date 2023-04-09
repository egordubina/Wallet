package com.example.wallet.ui.uistate

import com.example.wallet.ui.models.Transaction

sealed class HomeScreenUiState {
    object Loading : HomeScreenUiState()
    object Error : HomeScreenUiState()
    data class Content(
        val userName: String,
        val currentMonthExpanses: Int,
        val currentMonthIncomes: Int,
        val transactionsList: List<Transaction>,
    ) : HomeScreenUiState()
}