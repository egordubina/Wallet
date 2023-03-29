package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wallet.R
import com.example.wallet.data.models.Transaction
import com.example.wallet.data.models.TransactionType
import com.example.wallet.databinding.FragmentAddTransactionBinding
import com.example.wallet.ui.viewmodels.AddTransactionViewModel
import com.google.android.material.transition.MaterialSharedAxis

class AddTransaction : Fragment(R.layout.fragment__add_transaction) {
    private var _binding: FragmentAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val addTransactionViewModel: AddTransactionViewModel by viewModels { AddTransactionViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonActionSaveTransaction.setOnClickListener {
                addTransactionViewModel.addTransaction(
                    Transaction(
                        title = "Title",
                        description = "Description",
                        price = 100,
                        date = "1111",
                        type = TransactionType.OTHER
                    )
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}