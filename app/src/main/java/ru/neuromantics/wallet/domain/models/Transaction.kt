package ru.neuromantics.wallet.domain.models

import ru.neuromantics.wallet.data.models.TransactionCategory
import ru.neuromantics.wallet.data.models.TransactionType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import ru.neuromantics.wallet.ui.models.Transaction as TransactionUi

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
        val entityDate = it.date
        val date = LocalDate.of(
            entityDate.substring(0, 4).toInt(),
            entityDate.substring(5, 7).toInt(),
            entityDate.substring(8, 10).toInt()
        )
        val formatDate = DateTimeFormatter.ofPattern("d MMMM yyyy").format(date)
        if (it.type == TransactionType.EXPENSES)
            TransactionUi.Expanse(
                id = it.id,
                description = it.description,
                price = it.price,
                date = formatDate,
                category = it.category,
                type = it.type
            )
        else
            TransactionUi.Income(
                id = it.id,
                description = it.description,
                price = it.price,
                date = formatDate,
                category = it.category,
                type = it.type
            )
    }
}