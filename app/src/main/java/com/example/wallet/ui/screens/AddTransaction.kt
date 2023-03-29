package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.data.models.Transaction
import com.example.wallet.data.models.TransactionType
import com.example.wallet.databinding.FragmentAddTransactionBinding
import com.example.wallet.ui.viewmodels.AddTransactionViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialSharedAxis
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
        val types: MutableList<String> = mutableListOf()
        TransactionType.values().forEach { types.add(it.title) }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item__transaction_type, types)
        binding.apply {
            (textInputEditTextTransactionType as? AutoCompleteTextView)?.setAdapter(adapter)
            toolbarAddTransaction.setNavigationOnClickListener { findNavController().navigateUp() }
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
            radioButtonIncome.setOnClickListener {
                radioButtonIncome.isChecked = true
                radioButtonExpenses.isChecked = false
            }
            radioButtonExpenses.setOnClickListener {
                radioButtonIncome.isChecked = false
                radioButtonExpenses.isChecked = true
            }
            val todayDate = LocalDate.now()
            val formatDate = DateTimeFormatter.ofPattern("d MMMM yyyy").format(todayDate)
            textViewTransactionDate.text = getString(R.string.transaction_date, formatDate)
            buttonActionSelectTransactionDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(R.string.select_date)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(requireActivity().supportFragmentManager, "TAG")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}