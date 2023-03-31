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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import java.time.LocalTime

class Home : Fragment(R.layout.fragment__home_screen) {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels { HomeScreenViewModel.Factory }
    private val userViewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = checkNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeScreenViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is HomeScreenUiState.Content -> showContentUi(
                            userName = uiState.userName,
                            transactionList = uiState.transactionsList,
                            currentMonthIncomes = uiState.currentMonthIncomes,
                            currentMonthExpanses = uiState.currentMonthExpanses
                        )

                        HomeScreenUiState.Loading -> {} // todo переписать на состояние
                        HomeScreenUiState.UserIsFirstLogin ->
                            findNavController().navigate(R.id.action_homeScreen_to_welcome)
                    }
                }
            }
        }
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
        if (!userViewModel.userIsLogin)
            findNavController().navigate(R.id.action_homeScreen_to_login)
        hideLoading()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
//            requestNotificationPermission()
        binding.apply {
            textViewWelcome.text = getWelcomeMessage(userName)
            fabAddTransaction.setOnClickListener { findNavController().navigate(R.id.action_homeScreen_to_addTransaction) }
            textViewAllExpanses.text =
                getString(R.string.expanses_home_screen, currentMonthExpanses)
            textViewAllIncomes.text = getString(R.string.incomes_home_screen, currentMonthIncomes)
            layoutNoTransaction.isVisible = transactionList.isEmpty()
            textViewLatestTransaction.isVisible = transactionList.isNotEmpty()
            recyclerViewHomeAllTransaction.apply {
                isVisible = transactionList.isNotEmpty()
                if (isVisible)
                    adapter = HomeTransactionAdapter(transactionList.asReversed())
            }
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
            textViewAllIncomes.isVisible = false
            textViewAllExpanses.isVisible = false
            textViewLatestTransaction.isVisible = false
            fabAddTransaction.isVisible = false
            buttonToChart.isVisible = false
            buttonToSetPlan.isVisible = false
            buttonToSetNotification.isVisible = false
            textViewWelcome.isVisible = false
            recyclerViewHomeAllTransaction.isVisible = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            linearProgressIndicatorHome.isVisible = false
            textViewAllIncomes.isVisible = true
            textViewAllExpanses.isVisible = true
            textViewLatestTransaction.isVisible = true
            fabAddTransaction.isVisible = true
            buttonToChart.isVisible = true
            buttonToSetNotification.isVisible = true
            buttonToSetPlan.isVisible = true
            textViewWelcome.isVisible = true
            recyclerViewHomeAllTransaction.isVisible = true
        }
    }

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showErrorUi() {
        hideLoading()
        Snackbar.make(
            requireView(),
            getString(R.string.error_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun getWelcomeMessage(userName: String): String {
        return when (LocalTime.now().hour) {
            in 6 until 12 -> getString(R.string.welcome_good_morning, userName)
            in 12 until 18 -> getString(R.string.welcome_good_day, userName)
            in 18 until 22 -> getString(R.string.welcome_good_evening, userName)
            else -> getString(R.string.welcome_good_night, userName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}