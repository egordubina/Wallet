package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentLoginScreenBinding
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Login : Fragment(R.layout.fragment__login_screen) {
    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
//            buttonActionUseFingerprintToLogin.setOnClickListener {
//                Toast.makeText(requireContext(), "Будет сделано позже", Toast.LENGTH_SHORT).show()
//            }
            buttonActionLogin.setOnClickListener {
                textInputLayoutLoginPinCode.error = null
                when {
                    editTextLoginPinCode.text.toString().isEmpty() ->
                        textInputLayoutLoginPinCode.error = textInputLayoutLoginPinCode.helperText

                    editTextLoginPinCode.text.toString().length !in 4..8 ->
                        textInputLayoutLoginPinCode.error = textInputLayoutLoginPinCode.helperText

                    editTextLoginPinCode.text.toString() != userViewModel.pinCode ->
                        textInputLayoutLoginPinCode.error = getString(R.string.incorrect_pin_code)

                    else -> {
                        try {
                            editTextLoginPinCode.text.toString().toInt()
                            userViewModel.userIsLogin = true
                            findNavController().navigate(R.id.action_login_to_homeScreen)
                        } catch (e: Exception) {
                            textInputLayoutLoginPinCode.error =
                                getString(R.string.only_number_pin_code)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}