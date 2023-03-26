package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentWelcomeScreenBinding
import com.google.android.material.transition.MaterialSharedAxis

class Welcome : Fragment(R.layout.fragment__welcome_screen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding: FragmentWelcomeScreenBinding = FragmentWelcomeScreenBinding.bind(view)
        binding.apply {
            buttonGo.setOnClickListener {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.homeScreen, true).build()
                findNavController().navigate(R.id.homeScreen, null, navOptions)
            }
        }
    }
}