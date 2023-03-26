package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentRegistrationScreenBinding
import com.google.android.material.transition.MaterialSharedAxis

class Registration : Fragment(R.layout.fragment__registration_screen) {
    private lateinit var binding: FragmentRegistrationScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationScreenBinding.bind(view)
        binding.apply {
            buttonActionRegistration.setOnClickListener {
                findNavController().navigate(R.id.action_registration_to_homeScreen)
            }
        }
    }
}