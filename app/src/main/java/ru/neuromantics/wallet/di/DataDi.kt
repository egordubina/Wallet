package ru.neuromantics.wallet.di

import org.koin.dsl.module
import ru.neuromantics.wallet.data.database.WalletDatabase
import ru.neuromantics.wallet.data.repository.TransactionRepository
import ru.neuromantics.wallet.data.repository.TransactionRepositoryImpl
import ru.neuromantics.wallet.data.repository.UserRepository
import ru.neuromantics.wallet.data.repository.UserRepositoryImpl

val dataModule = module {
    single<WalletDatabase> { WalletDatabase.getDatabase(context = get()) }
    single<TransactionRepository> { TransactionRepositoryImpl(walletDatabase = get()) }
    single<UserRepository> { UserRepositoryImpl() }
}