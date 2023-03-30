package com.example.wallet.data.models

import androidx.annotation.StringRes
import com.example.wallet.R

enum class TransactionType(@StringRes val typeName: Int) {
    INCOME(R.string.income),
    EXPENSES(R.string.expenses)
}
