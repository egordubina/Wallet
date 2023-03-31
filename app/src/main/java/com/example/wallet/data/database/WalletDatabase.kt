package com.example.wallet.data.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wallet.data.models.Transaction
import com.example.wallet.data.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("select * from `transaction`")
    fun getLatestTransactions(): Flow<List<Transaction>>
}

@Dao
interface UserDao {
    @Query("select * from user")
    fun getUserInfo(): Flow<User>

    @Query("update user set userName = :name")
    suspend fun updateUserInfo(name: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registrationUser(user: User)

    @Query("select userPin from user")
    fun getUserPinCode(): Flow<String>

    @Query("select isFirstLogin from user")
    suspend fun checkIsFirstLogin(): Boolean

    @Query("update user set isFirstLogin = :status ")
    suspend fun setUserIsFirstLogin(status: Boolean)
}

@Database(entities = [Transaction::class, User::class], exportSchema = false, version = 1)
abstract class WalletDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val userDao: UserDao

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
