package com.example.wallet.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.wallet.domain.models.Transaction as TransactionDomain

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val description: String,
    val price: Int,
    val date: String,
    val category: TransactionCategory,
    val type: TransactionType,
)

fun List<Transaction>.asDomain(): List<TransactionDomain> {
    return map {
        TransactionDomain(
            id = it.id,
            description = it.description,
            price = it.price,
            date = it.date,
            category = it.category,
            type = it.type
        )
    }
}