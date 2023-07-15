package ru.neuromantics.wallet.domain.usecases

import ru.neuromantics.wallet.data.models.asDomain
import ru.neuromantics.wallet.data.preferences.WalletPreferences
import ru.neuromantics.wallet.data.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.neuromantics.wallet.domain.models.Transaction as TransactionDomain

class LoadHomeScreenUseCase(
    private val walletPreferences: WalletPreferences,
    transactionRepository: TransactionRepository
) {
    val allTransaction: Flow<List<TransactionDomain>> =
        transactionRepository.lastTransaction.map { it.asDomain() }
    val userName: String
        get() = walletPreferences.userName

    // Бюджет
    val currentMonthExpanses: Int
        get() = walletPreferences.currentMonthExpanses
    val currentMonthIncomes: Int
        get() = walletPreferences.currentMonthIncomes
}