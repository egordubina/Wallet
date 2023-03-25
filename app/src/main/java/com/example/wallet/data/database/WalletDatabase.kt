package com.example.wallet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallet.data.models.Transaction

@Database(entities = [Transaction::class], exportSchema = false, version = 1)
abstract class WalletDatabase : RoomDatabase() {

}