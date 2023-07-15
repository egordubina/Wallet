package ru.neuromantics.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import ru.neuromantics.wallet.data.repository.TransactionRepository
import ru.neuromantics.wallet.data.repository.UserRepository
import ru.neuromantics.wallet.ui.uistate.HomeScreenUiState

class HomeScreenViewModel(
//    private val walletPreferences: WalletPreferences,
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    val uiState = combine(
        userRepository.user,
        transactionRepository.lastTransaction
    ) { user, transaction ->
        HomeScreenUiState.Content(user, emptyList(), 0, 0)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeScreenUiState.Loading
    )
}
