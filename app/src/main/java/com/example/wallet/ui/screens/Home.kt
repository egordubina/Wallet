package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentHomeScreenBinding
import com.example.wallet.ui.uistate.HomeScreenUiState
import com.example.wallet.ui.viewmodels.HomeScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import java.time.LocalTime

class Home : Fragment(R.layout.fragment__home_screen) {
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeScreenBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)
        checkUserLogin()
        homeScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is HomeScreenUiState.Error -> TODO()
                is HomeScreenUiState.Content -> {
                    binding.apply {
                        textViewWelcome.text = getWelcomeMessage(userName = uiState.welcomeMessage)
                    }
                }

                HomeScreenUiState.Loading -> TODO()
            }
        }
    }

    private fun checkUserLogin() {
        if (userViewModel.userName.value == null || userViewModel.userName.value!!.isEmpty())
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
    }
}