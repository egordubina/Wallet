package com.example.wallet.data.preferences

import android.app.Activity
import android.content.Context

class WalletPreferences(private val activity: Activity) {
    companion object {
        const val INT: Int = 0
        const val STRING: String = ""
        const val BOOLEAN: Boolean = false
    }

    fun <T> getValue(name: String, answer: T?): T? {
        val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        @Suppress("UNCHECKED_CAST")
        return when (answer) {
            is String -> prefs.getString(name, null) as T
            is Int -> {
                val ans = prefs.getInt(name, -1)
                if (ans == -1) null else ans as T
            }

//            is Boolean -> prefs.getBoolean(name, null) as T
            else -> null
        }
    }

    fun <T> setValue(name: String, value: T) {
        val prefs = activity.getPreferences(Context.MODE_PRIVATE)
        with(prefs.edit()) {
            when (value) {
                is String -> putString(name, value)
                is Int -> putInt(name, value)
//                is Boolean -> putInt(name, value)
            }
            apply()
        }
    }
}