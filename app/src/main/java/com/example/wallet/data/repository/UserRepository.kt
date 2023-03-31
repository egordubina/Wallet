package com.example.wallet.data.repository

import com.example.wallet.data.database.UserDao
import com.example.wallet.data.models.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {
    val userInfo: Flow<User> = userDao.getUserInfo()
//    var userPin: Flow<String> = userDao.getUserPinCode()

    suspend fun getIsFirstLogin(): Boolean {
        return userDao.checkIsFirstLogin()
    }

    suspend fun setIsFirstLogin(status: Boolean = true) {
        userDao.setUserIsFirstLogin(status)
    }

    suspend fun updateUserName(name: String) {
        userDao.updateUserInfo(name)
    }

    suspend fun registrationUser(
        userName: String,
        userEmail: String,
        userPin: String
    ) {
        userDao.registrationUser(
            User(
                userName = userName,
                userEmail = userEmail,
                userPin = userPin,
                isFirstLogin = false
            )
        )
    }
}
