package ru.neuromantics.wallet.data.models

import androidx.annotation.StringRes
import ru.neuromantics.wallet.R

enum class TransactionCategory(@StringRes val categoryName: Int, val type: Int) {
    PRODUCT_SHOP(R.string.transaction_category_products, 1),
    CAR(R.string.transaction_category_car, 1),
    HOME(R.string.transaction_category_home, 1),
    HEALTH(R.string.transaction_category_health, 1),
    SERVICES(R.string.transaction_category_services, 1),
    SPORT_GOODS(R.string.transaction_category_sport, 1),
    SUBSCRIBERS(R.string.transaction_category_subscribers, 1),
    CAFES(R.string.transaction_category_cafe, 1),
    FAST_FOOD(R.string.transaction_category_fast_food, 1),
    REST(R.string.transaction_category_rest, 1),
    CLOTHES(R.string.transaction_category_clothes, 1),
    TRANSFER(R.string.transaction_category_transfer, 0),
    SALARY(R.string.transaction_category_salary, 2),
    OTHER(R.string.transaction_category_other, 0),
}