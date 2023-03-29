package com.example.wallet.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet.data.models.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("select * from `transaction`")
    suspend fun getAllTransactions(): List<Transaction>
}

@Database(entities = [Transaction::class], exportSchema = false, version = 1)
abstract class WalletDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao

    companion object {
        private var INSTANCE: WalletDatabase? = null
        fun getDatabase(context: Context): WalletDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    WalletDatabase::class.java,
                    "database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
