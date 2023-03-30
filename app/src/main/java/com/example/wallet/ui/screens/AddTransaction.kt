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
import com.example.wallet.data.models.TransactionCategory
import com.example.wallet.data.models.TransactionType
import com.example.wallet.databinding.FragmentAddTransactionBinding
import com.example.wallet.ui.viewmodels.AddTransactionViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialSharedAxis
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
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
        TransactionCategory.values().forEach { types.add(it.categoryName) }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item__transaction_type, types)
        binding.apply {
            (textInputEditTextTransactionType as? AutoCompleteTextView)?.setAdapter(adapter)
            var transactionDate: String? = null
            val todayDate = LocalDate.now()
            val formatDate = DateTimeFormatter.ofPattern("d MMMM yyyy").format(todayDate)
            textViewTransactionDate.text = getString(R.string.transaction_date, formatDate)
            buttonActionSelectTransactionDate.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(R.string.select_date)
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()
                datePicker.show(requireActivity().supportFragmentManager, "DATE PICKER")
                datePicker.addOnPositiveButtonClickListener {
                    val selectionDate =
                        LocalDateTime.ofEpochSecond(it / 1000, 0, ZoneOffset.UTC).toLocalDate()
                    val date = DateTimeFormatter.ofPattern("d MMMM yyyy").format(selectionDate)
                    transactionDate = selectionDate.toString()
                    textViewTransactionDate.text = getString(R.string.transaction_date, date)
                }
            }
            toolbarAddTransaction.setNavigationOnClickListener { findNavController().navigateUp() }
            buttonActionSaveTransaction.setOnClickListener {
                addTransactionViewModel.addTransaction(
                    Transaction(
                        description = editTextAddTransactionDescription.text.toString(),
                        price = editTextAddTransactionPrice.text.toString().toInt(),
                        date = transactionDate ?: todayDate.toString(),
                        category = getTransactionCategory(textInputEditTextTransactionType.text.toString()),
                        type = if (radioButtonIncome.isChecked) TransactionType.INCOME else TransactionType.EXPENSES
                    )
                )
                findNavController().navigateUp()
            }
            radioButtonIncome.setOnClickListener {
                radioButtonIncome.isChecked = true
                radioButtonExpenses.isChecked = false
            }
            radioButtonExpenses.setOnClickListener {
                radioButtonIncome.isChecked = false
                radioButtonExpenses.isChecked = true
            }
        }
    }

    private fun getTransactionCategory(string: String): TransactionCategory {
        TransactionCategory.values().forEach {
            if (it.categoryName == string)
                return it
        }
        return TransactionCategory.OTHER
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}