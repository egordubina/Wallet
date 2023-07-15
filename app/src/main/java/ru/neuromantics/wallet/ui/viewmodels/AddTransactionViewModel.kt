package ru.neuromantics.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ru.neuromantics.wallet.data.models.Transaction
import ru.neuromantics.wallet.data.models.TransactionType
import ru.neuromantics.wallet.data.preferences.WalletPreferences
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val database: ru.neuromantics.wallet.data.database.WalletDatabase,
    private val walletPreferences: WalletPreferences
) : ViewModel() {

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            database.transactionDao.insertTransaction(transaction)
            if (transaction.type == TransactionType.EXPENSES)
                walletPreferences.currentMonthExpanses =
                    walletPreferences.currentMonthExpanses + transaction.price
            else
                walletPreferences.currentMonthIncomes =
                    walletPreferences.currentMonthIncomes + transaction.price
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return AddTransactionViewModel(
                    database = (application as ru.neuromantics.wallet.WalletApplication).appDatabase,
                    walletPreferences = application.walletPreferences
                ) as T
            }
        }
    }
}