package com.example.wallet.ui.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentRegistrationScreenBinding
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Registration : Fragment(R.layout.fragment__registration_screen) {
    private lateinit var binding: FragmentRegistrationScreenBinding
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationScreenBinding.bind(view)
        binding.apply {
            // Клик по кнопке "Регистрация"
            buttonActionRegistration.setOnClickListener {

            }
            // Клик по navigateUp
            toolbarRegistration.setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }
}