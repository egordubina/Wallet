package com.example.wallet.domain.usecases

import com.example.wallet.data.models.asDomain
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.data.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.wallet.domain.models.Transaction as TransactionDomain

class LoadHomeScreenUseCase(
    private val walletPreferences: WalletPreferences,
    transactionRepository: TransactionRepository
) {
    val allTransaction: Flow<List<TransactionDomain>> =
        transactionRepository.lastTransaction.map { it.asDomain() }
    val userName: String
        get() = walletPreferences.userName
}