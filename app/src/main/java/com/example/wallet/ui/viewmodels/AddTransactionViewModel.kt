package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.database.WalletDatabase
import com.example.wallet.data.models.Transaction
import kotlinx.coroutines.launch

class AddTransactionViewModel(
    private val database: WalletDatabase
) : ViewModel() {

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            database.transactionDao.insertTransaction(transaction)
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return AddTransactionViewModel(
                    database = (application as WalletApplication).appDatabase
                ) as T
            }
        }
    }
}