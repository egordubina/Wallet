package com.example.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallet.ui.uistate.HomeScreenUiState

class HomeScreenViewModel : ViewModel() {
    private val _uiState: MutableLiveData<HomeScreenUiState> =
        MutableLiveData(HomeScreenUiState.Loading)
    val uiState: LiveData<HomeScreenUiState> = _uiState

    init {
        _uiState.value = HomeScreenUiState.Content(
            welcomeMessage = "Message"
        )
    }
}
