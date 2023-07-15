package ru.neuromantics.wallet.data.repository

import ru.neuromantics.wallet.data.models.Transaction
import kotlinx.coroutines.flow.Flow
import ru.neuromantics.wallet.data.database.TransactionDao
import ru.neuromantics.wallet.data.database.WalletDatabase

interface TransactionRepository {
    val lastTransaction: Flow<List<Transaction>>
}

class TransactionRepositoryImpl(
    walletDatabase: WalletDatabase
) : TransactionRepository {
    override val lastTransaction: Flow<List<Transaction>> = walletDatabase.transactionDao.getLatestTransactions()
}