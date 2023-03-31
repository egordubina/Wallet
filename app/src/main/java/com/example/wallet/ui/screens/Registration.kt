package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentRegistrationScreenBinding
import com.example.wallet.ui.uistate.RegistrationScreenUiState
import com.example.wallet.ui.viewmodels.RegistrationScreenViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class Registration : Fragment(R.layout.fragment__registration_screen) {
    private var _binding: FragmentRegistrationScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val registrationScreeViewModel: RegistrationScreenViewModel by viewModels { RegistrationScreenViewModel.Factory }
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                registrationScreeViewModel.uiState.collect {
                    when (it) {
                        RegistrationScreenUiState.Content -> showContentUi()
                        RegistrationScreenUiState.Loading -> showLoadingUi()
                        RegistrationScreenUiState.RegistrationFailedIncorrectPin -> showIncorrectPinUi()
                        RegistrationScreenUiState.RegistrationFailedIncorrectEmail -> showIncorrectEmailUi()
                        RegistrationScreenUiState.RegistrationFailedIncorrectName -> showIncorrectNameUi()
                        RegistrationScreenUiState.RegistrationFailed -> showFailedUi()
                        RegistrationScreenUiState.RegistrationSuccessful -> loginUser()
                    }
                }
            }
        }
    }

    private fun loginUser() {
        userViewModel.userIsLogin = true
        findNavController().navigate(R.id.action_registration_to_homeScreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showIncorrectNameUi() {
        hideLoading()
        binding.textInputLayoutRegistrationUserName.error =
            binding.textInputLayoutRegistrationUserName.helperText
    }

    private fun showIncorrectEmailUi() {
        hideLoading()
        binding.textInputLayoutRegistrationUserEmail.error =
            binding.textInputLayoutRegistrationUserEmail.helperText
    }

    private fun showIncorrectPinUi() {
        hideLoading()
        binding.textInputLayoutRegistrationUserPinCode.error =
            binding.textInputLayoutRegistrationUserPinCode.helperText
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarRegistration.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    private fun showContentUi() {
        hideLoading()
        binding.apply {
            buttonActionRegistration.setOnClickListener {
                registrationScreeViewModel.registrationUser(
                    name = editTextRegistrationUserName.text.toString(),
                    email = editTextRegistrationUserEmail.text.toString(),
                    pin = editTextRegistrationUserPinCode.text.toString()
                )
            }
        }
    }

    private fun showSuccessfulUi() {
        hideLoading()
        findNavController().navigate(R.id.action_registration_to_homeScreen)
    }

    private fun showFailedUi() {
        hideLoading()
        Snackbar.make(
            requireView(),
            R.string.error_message,
            Snackbar.LENGTH_SHORT
        )
            .setAnchorView(binding.buttonActionRegistration)
            .show()
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
            textInputLayoutRegistrationUserEmail.error = null
            textInputLayoutRegistrationUserName.error = null
            textInputLayoutRegistrationUserPinCode.error = null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}