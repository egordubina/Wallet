package com.example.wallet.data.models

enum class TransactionCategory(val categoryName: String) {
    // todo поменять categoryName на stringRes
    PRODUCT_SHOP("Продукты"),
    SPORT_GOODS("Спортивные товары"),
    SUBSCRIBERS("Подписки"),
    CAFES("Кафе"),
    FAST_FOOD("Фаст фуд"),
    REST("Отдых"),
    CLOTHES("Одежда"),
    OTHER("Прочее")
}
