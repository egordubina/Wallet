package com.example.wallet.domain.models

import com.example.wallet.data.models.TransactionCategory
import com.example.wallet.data.models.TransactionType
import com.example.wallet.ui.models.Transaction as TransactionUi

data class Transaction(
    val id: Int,
    val description: String,
    val price: Int,
    val date: String,
    val category: TransactionCategory,
    val type: TransactionType,
)

fun List<Transaction>.asUi(): List<TransactionUi> {
    return map {
        TransactionUi(
            id = it.id,
            description = it.description,
            price = it.price,
            date = it.date,
            category = it.category,
            type = it.type
        )
    }
}