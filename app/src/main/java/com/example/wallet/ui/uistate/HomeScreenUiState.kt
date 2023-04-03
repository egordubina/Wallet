package com.example.wallet.ui.uistate

import com.example.wallet.ui.models.Transaction

sealed class HomeScreenUiState {
    interface HomeScreen {
        val userName: String
        val currentMonthExpanses: Int
        val currentMonthIncomes: Int
    }

    object Loading : HomeScreenUiState()
    object Error : HomeScreenUiState()
    data class NoTransaction(
        override val userName: String,
        override val currentMonthExpanses: Int,
        override val currentMonthIncomes: Int,
    ) : HomeScreenUiState(), HomeScreen

    data class Content(
        override val userName: String,
        override val currentMonthExpanses: Int,
        override val currentMonthIncomes: Int,
        val transactionsList: List<Transaction>,
    ) : HomeScreenUiState(), HomeScreen
}