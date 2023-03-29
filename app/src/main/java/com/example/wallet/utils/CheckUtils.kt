package com.example.wallet.utils

class CheckUtils {
    fun checkEmail(email: String): Boolean {
        return when {
            email.contains("[a-zA-Z0-9@._-]".toRegex()) && email.length in 6..30 -> true
            else -> false
        }
    }
}