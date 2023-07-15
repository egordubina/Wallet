package ru.neuromantics.wallet.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.neuromantics.wallet.ui.viewmodels.HomeScreenViewModel

val appModule = module {
    viewModel<HomeScreenViewModel> { HomeScreenViewModel(userRepository = get(), transactionRepository = get()) }
}