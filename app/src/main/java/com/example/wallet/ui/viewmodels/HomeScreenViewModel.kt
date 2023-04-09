package com.example.wallet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.models.asDomain
import com.example.wallet.data.repository.TransactionRepository
import com.example.wallet.data.repository.UserRepository
import com.example.wallet.domain.models.asUi
import com.example.wallet.ui.uistate.HomeScreenUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    transactionRepository: TransactionRepository,
    userRepository: UserRepository
) : ViewModel() {

    val uiState = combine(
        transactionRepository.lastTransaction,
        userRepository.userInfo,
    ) { lastTransaction, userInfo ->
        try {
            HomeScreenUiState.Content(
                transactionsList = lastTransaction.asDomain().asUi(),
                userName = userInfo.userName,
                currentMonthExpanses = 0,
                currentMonthIncomes = 0
            )
        } catch (e: Exception) {
            HomeScreenUiState.Error
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = HomeScreenUiState.Loading
    )

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return HomeScreenViewModel(
                    (application as WalletApplication).transactionRepository,
                    application.userRepository
                ) as T
            }
        }
    }
}
