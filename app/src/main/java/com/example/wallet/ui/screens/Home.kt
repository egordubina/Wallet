package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentHomeScreenBinding
import com.example.wallet.ui.adapters.HomeTransactionAdapter
import com.example.wallet.ui.models.Transaction
import com.example.wallet.ui.uistate.HomeScreenUiState
import com.example.wallet.ui.viewmodels.HomeScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.time.LocalTime

class Home : Fragment(R.layout.fragment__home_screen) {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels { HomeScreenViewModel.Factory }
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
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
        if (userViewModel.isFirstLogin)
            findNavController().navigate(R.id.action_homeScreen_to_welcome)
        else
            if (!userViewModel.userIsLogin)
                findNavController().navigate(R.id.action_homeScreen_to_login)

        homeScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is HomeScreenUiState.Error -> showErrorUi()
                is HomeScreenUiState.Content -> showContentUi(
                    uiState.userName,
                    uiState.transactionsList
                )

                HomeScreenUiState.Loading -> showLoadingUi()
            }
        }
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

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showLoading() {
        binding.linearProgressIndicatorHome.isVisible = true
    }

    private fun showContentUi(
        userName: String,
        transactionList: List<Transaction>
    ) {
        hideLoading()
        binding.apply {
            textViewWelcome.text = getWelcomeMessage(userName)
            fabAddTransaction.setOnClickListener { actionToAddTransaction() }
            layoutNoTransaction.isVisible = transactionList.isEmpty()
            textViewLatestTransaction.isVisible = transactionList.isNotEmpty()
            recyclerViewHomeAllTransaction.apply {
                isVisible = transactionList.isNotEmpty()
                if (isVisible)
                    adapter = HomeTransactionAdapter(transactionList.asReversed())
            }
        }
    }

    private fun hideLoading() {
        binding.linearProgressIndicatorHome.isVisible = false
    }

    private fun showErrorUi() {
        hideLoading()
        Toast.makeText(
            requireContext(),
            getString(R.string.error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun actionToAddTransaction() {
        findNavController().navigate(R.id.action_homeScreen_to_addTransaction)
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