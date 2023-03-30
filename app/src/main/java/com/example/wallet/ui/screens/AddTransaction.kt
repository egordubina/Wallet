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
    private val binding get() = checkNotNull(_binding)
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

            var adapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item__transaction_type,
                initTypesCategory(false)
            )
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
                textInputLayoutTransactionPrice.error = null
                when {
                    editTextAddTransactionPrice.text.toString().isEmpty() -> {
                        textInputLayoutTransactionPrice.error = getString(R.string.input_cost)
                        return@setOnClickListener
                    }

                    else -> {
                        try {
                            editTextAddTransactionPrice.text.toString().toInt()
                        } catch (e: Exception) {
                            textInputLayoutTransactionPrice.error =
                                getString(R.string.incorrect_cost)
                            return@setOnClickListener
                        }
                    }
                }
                val description = editTextAddTransactionDescription.text.toString().ifEmpty {
                    if (radioButtonIncome.isChecked) getString(R.string.incomes) else getString(R.string.expanses)
                }
                val date = transactionDate ?: todayDate.toString()
                val type =
                    if (radioButtonIncome.isChecked) TransactionType.INCOME else TransactionType.EXPENSES
                addTransactionViewModel.addTransaction(
                    Transaction(
                        description = description,
                        price = editTextAddTransactionPrice.text.toString().toInt(),
                        date = date,
                        category = getTransactionCategory(textInputEditTextTransactionType.text.toString()),
                        type = type
                    )
                )
                findNavController().navigateUp()
            }
            radioButtonIncome.setOnClickListener {
                radioButtonIncome.isChecked = true
                radioButtonExpenses.isChecked = false
                adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_item__transaction_type,
                    initTypesCategory(true)
                )
                (textInputEditTextTransactionType as? AutoCompleteTextView)?.setAdapter(adapter)
            }
            radioButtonExpenses.setOnClickListener {
                radioButtonIncome.isChecked = false
                radioButtonExpenses.isChecked = true
                adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.list_item__transaction_type,
                    initTypesCategory(false)
                )
                (textInputEditTextTransactionType as? AutoCompleteTextView)?.setAdapter(adapter)
            }
        }
    }

    private fun initTypesCategory(incomes: Boolean): List<String> {
        val types: MutableList<String> = mutableListOf()
        if (incomes) {
            TransactionCategory.values().forEach {
                if (it.type == 0 || it.type == 2)
                    types.add(getString(it.categoryName))
            }
        } else {
            TransactionCategory.values().forEach {
                if (it.type == 0 || it.type == 1)
                    types.add(getString(it.categoryName))
            }
        }
        return types
    }

    private fun getTransactionCategory(string: String): TransactionCategory {
        TransactionCategory.values().forEach {
            if (getString(it.categoryName) == string)
                return it
        }
        return TransactionCategory.OTHER
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}