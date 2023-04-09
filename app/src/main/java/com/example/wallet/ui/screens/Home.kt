package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentHomeScreenBinding
import com.example.wallet.ui.adapters.HomeTransactionAdapter
import com.example.wallet.ui.models.Transaction
import com.example.wallet.ui.uistate.HomeScreenUiState
import com.example.wallet.ui.viewmodels.HomeScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.example.wallet.utils.UiUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class Home : Fragment(R.layout.fragment__home_screen) {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels { HomeScreenViewModel.Factory }
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        if (userViewModel.userIsLogin)
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    homeScreenViewModel.uiState.collect { uiState ->
                        when (uiState) {
                            HomeScreenUiState.Loading -> showLoadingUi()
                            HomeScreenUiState.Error -> showErrorUi()
                            is HomeScreenUiState.Content -> showContentUi(
                                userName = uiState.userName,
                                transactionList = uiState.transactionsList,
                                currentMonthIncomes = uiState.currentMonthIncomes,
                                currentMonthExpanses = uiState.currentMonthExpanses
                            )
                        }
                    }
                }
            }
        else
            if (userViewModel.userIsFirstLogin)
                findNavController().navigate(R.id.action_homeScreen_to_welcome)
            else
                findNavController().navigate(R.id.action_homeScreen_to_login)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fabAddTransaction.setOnClickListener {
                findNavController().navigate(R.id.action_homeScreen_to_addTransaction)
            }
            toolbarHome.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item__settings -> {
                        findNavController().navigate(R.id.action_homeScreen_to_settings)
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun showContentUi(
        userName: String,
        transactionList: List<Transaction>,
        currentMonthExpanses: Int,
        currentMonthIncomes: Int
    ) {
        hideLoading()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            requestNotificationPermission()
        binding.apply {
            textViewWelcome.text = UiUtils(requireContext()).getWelcomeMessage(userName)
            textViewAllExpanses.text =
                getString(R.string.expanses_home_screen, currentMonthExpanses)
            textViewAllIncomes.text = getString(R.string.incomes_home_screen, currentMonthIncomes)
            layoutNoTransaction.isVisible = transactionList.isEmpty()
            textViewLatestTransaction.isVisible = transactionList.isNotEmpty()
            recyclerViewHomeAllTransaction.apply {
                isVisible = transactionList.isNotEmpty()
                if (transactionList.isNotEmpty())
                    adapter = HomeTransactionAdapter(transactionList.asReversed())
            }
            // Change toolbar title when scroll to latest transactions
            nestedScrollViewHome.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
                if (scrollY > textViewLatestTransaction.bottom)
                    toolbarHome.setTitle(R.string.latest_transaction)
                else
                    toolbarHome.setTitle(R.string.app_name)
            })
            buttonToSetPlan.setOnClickListener { findNavController().navigate(R.id.action_homeScreen_to_budgetPlan) }
        }
    }

    private fun showLoading() {
        binding.apply {
            linearProgressIndicatorHome.isVisible = true
            textViewWelcome.isVisible = false
            groupIfTransactionNotEmpty.isVisible = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            linearProgressIndicatorHome.isVisible = false
            textViewWelcome.isVisible = true
            groupIfTransactionNotEmpty.isVisible = true
        }
    }

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showErrorUi() {
        hideLoading()
        Snackbar.make(
            requireView(),
            "Ошибка",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}