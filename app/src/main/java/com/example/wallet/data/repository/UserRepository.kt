package com.example.wallet.data.repository

import com.example.wallet.data.database.UserDao
import com.example.wallet.data.models.User
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val userDao: UserDao
) {
    val userInfo: Flow<User> = userDao.getUserInfo()

    suspend fun registrationUser(
        userName: String,
        userEmail: String,
        userPin: String
    ) {
        userDao.registrationUser(
            User(
                userName = userName,
                userEmail = userEmail,
                userPin = userPin
            )
        )
    }

    suspend fun updateUser(user: User) {

    }
}
