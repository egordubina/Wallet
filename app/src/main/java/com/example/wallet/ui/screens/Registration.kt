package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentRegistrationScreenBinding
import com.example.wallet.ui.uistate.RegistrationScreenUiState
import com.example.wallet.ui.viewmodels.RegistrationScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.example.wallet.utils.CheckUtils
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis

class Registration : Fragment(R.layout.fragment__registration_screen) {
    private var _binding: FragmentRegistrationScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val registrationScreeViewModel: RegistrationScreenViewModel by viewModels { RegistrationScreenViewModel.Factory }
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
        _binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrationScreeViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                RegistrationScreenUiState.Content -> showContentUi()
                RegistrationScreenUiState.Loading -> showLoadingUi()
                RegistrationScreenUiState.RegistrationFailed -> showFailedUi()
                RegistrationScreenUiState.RegistrationSuccessful -> showSuccessfulUi()
            }
        }
        binding.toolbarRegistration.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun showContentUi() {
        hideLoading()
        binding.apply {
            buttonActionRegistration.setOnClickListener {
                if (checkUserDataForRegistration()) {
                    registrationScreeViewModel.registrationUser(
                        name = editTextRegistrationUserName.text.toString(),
                        email = editTextRegistrationUserEmail.text.toString(),
                        pin = editTextRegistrationUserPinCode.text.toString()
                    )
                    userViewModel.userIsLogin = true
                }
            }
        }
    }

    private fun showSuccessfulUi() {
        hideLoading()
        findNavController().navigate(R.id.action_registration_to_homeScreen)
    }

    private fun showFailedUi() {
        Snackbar.make(
            requireView(),
            R.string.error_message,
            Snackbar.LENGTH_SHORT
        )
            .setAnchorView(binding.buttonActionRegistration)
            .show()
        hideLoading()
    }

    private fun showLoadingUi() {
        showLoading()
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

    private fun checkUserDataForRegistration(): Boolean {
        binding.apply {
            textInputLayoutRegistrationUserName.error = null
            textInputLayoutRegistrationUserEmail.error = null
            textInputLayoutRegistrationUserPinCode.error = null
            val userName = editTextRegistrationUserName.text.toString()
            val userEmail = editTextRegistrationUserEmail.text.toString()
            val userPinCode = editTextRegistrationUserPinCode.text.toString()
            when {
                userName.isEmpty() -> {
                    textInputLayoutRegistrationUserName.error =
                        getString(R.string.reg_help_text_name)
                    return false
                }

//                userEmail.isEmpty() -> {
//                    textInputLayoutRegistrationUserEmail.error =
//                        getString(R.string.reg_help_text_email)
//                    return false
//                }
                !CheckUtils().checkEmail(userEmail) -> {
                    textInputLayoutRegistrationUserEmail.error =
                        getString(R.string.reg_help_text_email)
                    return false
                }

                userPinCode.isEmpty() -> {
                    textInputLayoutRegistrationUserPinCode.error =
                        getString(R.string.reg_help_text_pin_code)
                    return false
                }

                userPinCode.length !in 4..8 -> {
                    textInputLayoutRegistrationUserPinCode.error =
                        getString(R.string.reg_help_text_pin_code)
                    return false
                }

                else -> {
                    return try {
                        userPinCode.toInt()
                        true
                    } catch (e: Exception) {
                        textInputLayoutRegistrationUserPinCode.error =
                            getString(R.string.only_number_pin_code)
                        false
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