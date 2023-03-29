package com.example.wallet.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.ui.models.Transaction

class HomeTransactionAdapter(private val transactionList: List<Transaction>) :
    RecyclerView.Adapter<HomeTransactionAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description = view.findViewById<TextView>(R.id.list_item_transaction__description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item__transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.description.text = transactionList[position].description
    }

    override fun getItemCount(): Int = transactionList.size
}