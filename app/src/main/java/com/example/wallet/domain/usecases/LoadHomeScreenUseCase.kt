package com.example.wallet.domain.usecases

import com.example.wallet.data.models.User
import com.example.wallet.data.models.asDomain
import com.example.wallet.data.repository.TransactionRepository
import com.example.wallet.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.wallet.domain.models.Transaction as TransactionDomain

class LoadHomeScreenUseCase(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository
) {
    val lastTransaction: Flow<List<TransactionDomain>> =
        transactionRepository.lastTransaction.map { it.asDomain() }
    val userInfo: Flow<User> = userRepository.userInfo
}