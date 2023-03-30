package com.example.wallet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.data.models.TransactionType
import com.example.wallet.databinding.ListItemTransactionBinding
import com.example.wallet.ui.models.Transaction

class HomeTransactionAdapter(private val transactionList: List<Transaction>) :
    RecyclerView.Adapter<HomeTransactionAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            with(transactionList[position]) {
                itemTransactionCategory.text = category.categoryName
                itemTransactionDescription.text = description
                itemTransactionCost.text =
                    if (this.type == TransactionType.INCOME) "+ $price" else "- $price"
                itemTransactionDate.text = date
            }
        }
    }

    override fun getItemCount(): Int = transactionList.size
}