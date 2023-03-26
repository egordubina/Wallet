package com.example.wallet

import android.app.Application
import com.example.wallet.data.preferences.WalletPreferences
import com.google.android.material.color.DynamicColors

class WalletApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}