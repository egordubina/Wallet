package com.example.wallet.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentLoginScreenBinding
import com.example.wallet.ui.uistate.LoginScreenUiState
import com.example.wallet.ui.viewmodels.LoginViewModel
import com.example.wallet.ui.viewmodels.UserViewModel
import com.example.wallet.utils.UiUtils
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class Login : Fragment(R.layout.fragment__login_screen) {
    private var _binding: FragmentLoginScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val userViewModel: UserViewModel by activityViewModels { UserViewModel.Factory }
    private val loginViewModel: LoginViewModel by viewModels { LoginViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.uiState.collect {
                    when (it) {
                        is LoginScreenUiState.Content -> showUi(it.userName)
                        LoginScreenUiState.Loading -> showLoading()
                        LoginScreenUiState.Success -> toHomeScreen()
                        LoginScreenUiState.IncorrectPinCode -> showIncorrectPinCodeUi()
                        LoginScreenUiState.Error -> showErrorUi()
                    }
                }
            }
        }
    }

    private fun toHomeScreen() {
        userViewModel.userIsLogin = true
        findNavController().navigate(R.id.action_login_to_homeScreen)
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
            buttonActionLogin.setOnClickListener {
                textInputLayoutLoginPinCode.error = null
                loginViewModel.loginUser(editTextLoginPinCode.text.toString())
            }
            buttonActionUseFingerprintToLogin.setOnClickListener {
                showBiometricDialog()
            }
        }
    }

    private fun showIncorrectPinCodeUi() {
        hideLoading()
        binding.textInputLayoutLoginPinCode.error = getString(R.string.incorrect_pin_code)
    }

    private fun showErrorUi() {
        hideLoading()
        binding.apply {
            textInputLayoutLoginPinCode.error = textInputLayoutLoginPinCode.helperText
        }
    }

    private fun hideLoading() {
        binding.apply {
            editTextLoginPinCode.error = null
            linearProgressIndicatorLogin.isVisible = false
        }
    }

    private fun showLoading() {
        binding.linearProgressIndicatorLogin.isVisible = true
    }

    private fun showUi(userName: String) {
        hideLoading()
        binding.apply {
            textViewWelcomeLogin.text = UiUtils(requireContext()).getWelcomeMessage(userName)
        }
        showBiometricDialog()
    }

    private fun showBiometricDialog() {
        val biometricManager = BiometricManager.from(requireContext())
        @SuppressLint("SwitchIntDef")
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                val executor = ContextCompat.getMainExecutor(requireContext())
                val biometricPrompt =
                    BiometricPrompt(
                        this,
                        executor,
                        object : BiometricPrompt.AuthenticationCallback() {
                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                super.onAuthenticationSucceeded(result)
                                loginViewModel.loginUserWithBiometric()
                            }
                        })
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle(getString(R.string.app_name))
                    .setAllowedAuthenticators(BIOMETRIC_STRONG)
                    .setNegativeButtonText("Использовать пин-код")
                    .build()
                biometricPrompt.authenticate(promptInfo)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}