package com.example.wallet

import android.app.Application
import com.example.wallet.data.database.WalletDatabase
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.data.repository.TransactionRepository
import com.example.wallet.data.repository.UserRepository
import com.google.android.material.color.DynamicColors

class WalletApplication : Application() {
    val walletPreferences by lazy { WalletPreferences(this) }
    val appDatabase by lazy { WalletDatabase.getDatabase(this) }
    val transactionRepository by lazy { TransactionRepository(appDatabase.transactionDao) }
    val userRepository by lazy { UserRepository(appDatabase.userDao) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}