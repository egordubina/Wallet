package com.example.wallet.domain.models

import com.example.wallet.data.models.TransactionType

data class Transaction(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val date: String,
    val type: TransactionType
)