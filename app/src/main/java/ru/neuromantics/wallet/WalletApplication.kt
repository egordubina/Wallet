package ru.neuromantics.wallet

import android.app.Application
import ru.neuromantics.wallet.data.preferences.WalletPreferences
import ru.neuromantics.wallet.data.repository.TransactionRepository
import com.google.android.material.color.DynamicColors
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.neuromantics.wallet.di.appModule
import ru.neuromantics.wallet.di.dataModule

class WalletApplication : Application() {
    val walletPreferences by lazy { WalletPreferences(this) }
    val appDatabase by lazy { ru.neuromantics.wallet.data.database.WalletDatabase.getDatabase(this) }
//    val transactionRepository by lazy { TransactionRepository(appDatabase.transactionDao) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        startKoin {
            androidContext(this@WalletApplication)
            modules(dataModule, appModule)
        }
    }
}