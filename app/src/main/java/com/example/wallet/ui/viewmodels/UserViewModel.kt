package com.example.wallet.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wallet.ui.models.User

class UserViewModel : ViewModel() {
    fun isFirstLogin(): Boolean {
        return true
    }

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _name = MutableLiveData<String>()
    private val _photo = MutableLiveData<String>()
    val name: LiveData<String> = _name
    val photo: LiveData<String> = _photo
}