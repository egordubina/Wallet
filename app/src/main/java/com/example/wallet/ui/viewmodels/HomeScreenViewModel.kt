package com.example.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.wallet.WalletApplication
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.data.repository.TransactionRepository
import com.example.wallet.domain.models.asUi
import com.example.wallet.domain.usecases.LoadHomeScreenUseCase
import com.example.wallet.ui.uistate.HomeScreenUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    walletPreferences: WalletPreferences,
    transactionRepository: TransactionRepository
) : ViewModel() {
    private val _uiState: MutableLiveData<HomeScreenUiState> =
        MutableLiveData(HomeScreenUiState.Loading)
    val uiState: LiveData<HomeScreenUiState> = _uiState

    private var job: Job? = null

    init {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                val userName =
                    LoadHomeScreenUseCase(walletPreferences, transactionRepository).userName
                val transactionList =
                    LoadHomeScreenUseCase(walletPreferences, transactionRepository).allTransaction
                transactionList.collect {
                    _uiState.postValue(HomeScreenUiState.Content(userName = userName, it.asUi()))
                }
            } catch (e: Exception) {
                _uiState.postValue(HomeScreenUiState.Error(e.message.toString()))
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return HomeScreenViewModel(
                    (application as WalletApplication).walletPreferences,
                    application.transactionRepository
                ) as T
            }
        }
    }
}
