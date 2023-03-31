package com.example.wallet.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val id: Int = 0,
    val userName: String,
    val userEmail: String,
    val userPin: String,
    val isFirstLogin: Boolean
)