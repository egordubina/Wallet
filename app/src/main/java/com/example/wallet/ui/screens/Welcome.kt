package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.databinding.FragmentWelcomeScreenBinding
import com.google.android.material.transition.MaterialSharedAxis

class Welcome : Fragment(R.layout.fragment__welcome_screen) {
    private lateinit var walletPreferences: WalletPreferences
    private var _binding: FragmentWelcomeScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding: FragmentWelcomeScreenBinding = FragmentWelcomeScreenBinding.bind(view)
        walletPreferences = WalletPreferences(requireActivity())
        binding.apply {
            buttonGo.setOnClickListener {
                findNavController().navigate(R.id.action_welcome_to_register)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
