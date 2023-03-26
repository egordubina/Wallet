package com.example.wallet.data.preferences

import android.app.Activity
import android.content.Context
import java.lang.RuntimeException

class WalletPreferences(private val activity: Activity) {
    private val prefs = activity.getPreferences(Context.MODE_PRIVATE)
    companion object {
        const val INT: Int = -1
        const val STRING: String = ""
        const val BOOLEAN: Boolean = true

        const val USER_NEW: String = "USER_NEW"
        const val USE_FINGERPRINT_TO_LOGIN = "USE_FINGERPRINT_TO_LOGIN"
        const val USER_NAME: String = "USER_NAME"
    }

    fun <T> getValue(name: String, answer: T): T {
        return when (answer) {
            is String -> prefs.getString(name, STRING) as T
            is Int -> prefs.getInt(name, INT) as T
            is Boolean -> prefs.getBoolean(name, BOOLEAN) as T
            else -> throw RuntimeException("getValue only for String, Int, Boolean")
        }
    }

    fun <T> setValue(name: String, value: T) {
        with(prefs.edit()) {
            when (value) {
                is String -> putString(name, value)
                is Int -> putInt(name, value)
                is Boolean -> putBoolean(name, value)
                else -> throw RuntimeException("setValue only for String, Int, Boolean")
            }
            apply()
        }
    }
}
