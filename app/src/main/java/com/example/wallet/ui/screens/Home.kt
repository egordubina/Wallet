package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.databinding.FragmentHomeScreenBinding
import com.example.wallet.ui.uistate.HomeScreenUiState
import com.example.wallet.ui.viewmodels.HomeScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.transition.MaterialSharedAxis
import java.time.LocalTime

class Home : Fragment(R.layout.fragment__home_screen) {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels { HomeScreenViewModel.Factory }
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var walletPreferences: WalletPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)
        walletPreferences = WalletPreferences(requireActivity())
        checkUserFirstLogin()
        checkUserData()
        homeScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is HomeScreenUiState.Error -> showError()
                is HomeScreenUiState.Content -> {
                    binding.apply {
                        textViewWelcome.text = getWelcomeMessage(uiState.userName)
                        fabAddTransaction.setOnClickListener { actionToAddTransaction() }
                    }
                }

                HomeScreenUiState.Loading -> TODO()
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

    private fun showError() {
        Toast.makeText(
            requireContext(),
            getString(R.string.error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun checkUserData() {
        if (userViewModel.user.value == null) {

        }
    }

    private fun actionToAddTransaction() {
        Toast.makeText(requireContext(), "Будет сделано позже", Toast.LENGTH_SHORT).show()
    }

    private fun checkUserFirstLogin() {
        if (userViewModel.isFirstLogin)
            findNavController().navigate(R.id.action_homeScreen_to_welcome)
    }

    private fun getWelcomeMessage(userName: String): String {
        return when (LocalTime.now().hour) {
            in 6 until 12 -> getString(R.string.welcome_good_morning, userName)
            in 12 until 18 -> getString(R.string.welcome_good_day, userName)
            in 18 until 22 -> getString(R.string.welcome_good_evening, userName)
            else -> getString(R.string.welcome_good_night, userName)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }
}