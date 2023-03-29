package com.example.wallet.data.repository

import com.example.wallet.data.database.TransactionDao
import com.example.wallet.data.models.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    transactionDao: TransactionDao
) {
    val lastTransaction: Flow<List<Transaction>> = transactionDao.getAllTransactions()
    // todo функция для синхронизации транзакций
}