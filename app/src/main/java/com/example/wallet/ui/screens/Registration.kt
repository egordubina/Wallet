package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentRegistrationScreenBinding
import com.example.wallet.ui.uistate.RegistrationScreenUiState
import com.example.wallet.ui.viewmodels.RegistrationScreenViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Registration : Fragment(R.layout.fragment__registration_screen) {
    private lateinit var binding: FragmentRegistrationScreenBinding

    //    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    private val registrationScreeViewModel: RegistrationScreenViewModel by viewModels { RegistrationScreenViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationScreenBinding.bind(view)
        registrationScreeViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                RegistrationScreenUiState.Content -> showContentUi()
                RegistrationScreenUiState.Loading -> showLoadingUi()
                RegistrationScreenUiState.RegistrationFailed -> showFailedUi()
                RegistrationScreenUiState.RegistrationSuccessful -> showSuccessfulUi()
            }
        }
        // Клик по navigateUp
        binding.toolbarRegistration.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun showSuccessfulUi() {
        hideLoading()
        findNavController().navigate(R.id.action_registration_to_homeScreen)
    }

    private fun showFailedUi() {
        hideLoading()
    }

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showContentUi() {
        binding.apply {
            binding.linearProgressIndicatorRegistration?.isVisible = false
            // Клик по кнопке "Регистрация"
            buttonActionRegistration.setOnClickListener {
                if (checkUserDataForRegistration()) {
                    registrationScreeViewModel.registrationUser(
                        name = editTextRegistrationUserName.text.toString(),
                        email = editTextRegistrationUserEmail.text.toString(),
                        pin = editTextRegistrationUserPinCode.text.toString().toInt()
                    )
                }
            }
        }
    }

    private fun checkUserDataForRegistration(): Boolean {
        binding.apply {
            textInputLayoutRegistrationUserName.error = null
            textInputLayoutRegistrationUserEmail.error = null
            textInputLayoutRegistrationUserPinCode.error = null
            if (editTextRegistrationUserName.text.toString().isEmpty()) {
                textInputLayoutRegistrationUserName.error = getString(R.string.reg_help_text_name)
                return false
            }
            if (editTextRegistrationUserEmail.text.toString().isEmpty()) {
                textInputLayoutRegistrationUserEmail.error = getString(R.string.reg_help_text_email)
                return false
            }
            if (editTextRegistrationUserPinCode.text.toString().isEmpty()) {
                textInputLayoutRegistrationUserPinCode.error =
                    getString(R.string.reg_help_text_pin_code)
                return false
            }
            if (editTextRegistrationUserPinCode.text.toString().length !in 4..8) {
                textInputLayoutRegistrationUserPinCode.error =
                    getString(R.string.reg_help_text_pin_code)
                return false
            }
            try {
                editTextRegistrationUserPinCode.text.toString().toInt()
            } catch (e: Exception) {
                return false
            }
            return true
        }
    }

    private fun showLoading() {
        binding.apply {
            linearProgressIndicatorRegistration?.isVisible = true
            editTextRegistrationUserName.isEnabled = false
            editTextRegistrationUserEmail.isEnabled = false
            editTextRegistrationUserPinCode.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            linearProgressIndicatorRegistration?.isVisible = false
            editTextRegistrationUserName.isEnabled = true
            editTextRegistrationUserEmail.isEnabled = true
            editTextRegistrationUserPinCode.isEnabled = true
        }
    }
}