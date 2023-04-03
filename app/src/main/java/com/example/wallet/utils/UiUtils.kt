package com.example.wallet.utils

import android.content.Context
import com.example.wallet.R
import java.time.LocalTime

/**
 * Утилиты для работы с UI
 * @property context Контекст для получения ресурсов
 * */
class UiUtils(private val context: Context) {
    /**
     * Функция для приветствия пользователей
     * @param context Контекст для получаения строковых ресурсов
     * @param userName Имя пользователя
     * @return Строка с приветствием пользователя
     * */
    fun getWelcomeMessage(userName: String): String {
        return when (LocalTime.now().hour) {
            in 6 until 12 -> context.getString(R.string.welcome_good_morning, userName)
            in 12 until 18 -> context.getString(R.string.welcome_good_day, userName)
            in 18 until 22 -> context.getString(R.string.welcome_good_evening, userName)
            else -> context.getString(R.string.welcome_good_night, userName)
        }
    }
}