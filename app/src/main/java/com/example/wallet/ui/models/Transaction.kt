package com.example.wallet.ui.models

import com.example.wallet.data.models.TransactionCategory
import com.example.wallet.data.models.TransactionType

sealed class Transaction {
    data class Income(
        val id: Int,
        val description: String,
        val price: Int,
        val date: String,
        val category: TransactionCategory,
        val type: TransactionType,
    ) : Transaction()

    data class Expanse(
        val id: Int,
        val description: String,
        val price: Int,
        val date: String,
        val category: TransactionCategory,
        val type: TransactionType,
    ) : Transaction()
}