package ru.neuromantics.wallet.ui.uistate

import ru.neuromantics.wallet.ui.models.Transaction

sealed class HomeScreenUiState {
    object Loading : HomeScreenUiState()
    data class Error(
        val errorMessage: String,
    ) : HomeScreenUiState()

    data class Content(
        val userName: String,
        val transactionsList: List<Transaction>,
        val currentMonthExpanses: Int,
        val currentMonthIncomes: Int,
    ) : HomeScreenUiState()
}
