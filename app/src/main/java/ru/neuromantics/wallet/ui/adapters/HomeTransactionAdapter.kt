package ru.neuromantics.wallet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.neuromantics.wallet.R
import ru.neuromantics.wallet.databinding.ListItemTransactionExpanseBinding
import ru.neuromantics.wallet.databinding.ListItemTransactionIncomeBinding
import ru.neuromantics.wallet.ui.models.Transaction

class HomeTransactionAdapter(private val transactionList: List<Transaction>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class TransactionIncomeViewHolder(private val binding: ListItemTransactionIncomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction.Income) {
            binding.apply {
                itemTransactionDescription.text = item.description
                itemTransactionCategory.text =
                    itemView.resources.getString(item.category.categoryName)
                itemTransactionCost.text =
                    itemView.resources.getString(R.string.cost_income, item.price)
                itemTransactionDate.text = item.date
            }
        }
    }

    class TransactionExpanseViewHolder(private val binding: ListItemTransactionExpanseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Transaction.Expanse) {
            binding.apply {
                itemTransactionDescription.text = item.description
                itemTransactionCategory.text =
                    itemView.resources.getString(item.category.categoryName)
                itemTransactionCost.text =
                    itemView.resources.getString(R.string.cost_expanse, item.price)
                itemTransactionDate.text = item.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_INCOME ->
                TransactionIncomeViewHolder(
                    ListItemTransactionIncomeBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )

            TYPE_EXPANSE ->
                TransactionExpanseViewHolder(
                    ListItemTransactionExpanseBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )

            else -> throw RuntimeException("Invalid type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TransactionExpanseViewHolder -> holder.bind(transactionList[position] as Transaction.Expanse)
            is TransactionIncomeViewHolder -> holder.bind(transactionList[position] as Transaction.Income)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (transactionList[position]) {
            is Transaction.Expanse -> TYPE_EXPANSE
            is Transaction.Income -> TYPE_INCOME
        }
    }

    override fun getItemCount(): Int = transactionList.size

    companion object {
        private const val TYPE_INCOME = 0
        private const val TYPE_EXPANSE = 1
    }
}