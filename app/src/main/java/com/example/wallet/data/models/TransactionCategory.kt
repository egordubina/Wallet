package com.example.wallet.data.models

import androidx.annotation.StringRes
import com.example.wallet.R

enum class TransactionCategory(@StringRes val categoryName: Int, val type: TransactionType) {
    PRODUCT_SHOP(R.string.transaction_category_products, TransactionType.EXPENSES),
    CAR(R.string.transaction_category_car, TransactionType.EXPENSES),
    HOME(R.string.transaction_category_home, TransactionType.EXPENSES),
    HEALTH(R.string.transaction_category_health, TransactionType.EXPENSES),
    SERVICES(R.string.transaction_category_services, TransactionType.EXPENSES),
    SPORT_GOODS(R.string.transaction_category_sport, TransactionType.EXPENSES),
    SUBSCRIBERS(R.string.transaction_category_subscribers, TransactionType.EXPENSES),
    CAFES(R.string.transaction_category_cafe, TransactionType.EXPENSES),
    FAST_FOOD(R.string.transaction_category_fast_food, TransactionType.EXPENSES),
    REST(R.string.transaction_category_rest, TransactionType.EXPENSES),
    CLOTHES(R.string.transaction_category_clothes, TransactionType.EXPENSES),
    TRANSFER(R.string.transaction_category_transfer, TransactionType.NEUTRAL),
    SALARY(R.string.transaction_category_salary, TransactionType.INCOME),
    OTHER(R.string.transaction_category_other, TransactionType.NEUTRAL),
}

enum class TransactionType(/*@StringRes val typeName: Int*/) {
    INCOME(/*R.string.incomes*/),
    EXPENSES(/*R.string.expanses*/),
    NEUTRAL
}