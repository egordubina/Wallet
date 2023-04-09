package com.example.wallet.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentSettingsScreenBinding
import com.example.wallet.ui.uistate.SettingsScreenUiState
import com.example.wallet.ui.viewmodels.SettingsScreenViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch

class Settings : Fragment(R.layout.fragment__settings_screen) {
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModels { SettingsScreenViewModel.Factory }
    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingsScreenViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is SettingsScreenUiState.Content -> {
                            showContentUi(
                                name = uiState.userName,
                                email = uiState.userEmail,
                                useFingerprintToLogin = uiState.fingerprintLogin,
                                userPin = uiState.userPin
                            )
                        }

                        SettingsScreenUiState.Error -> showFailedUi()
                        SettingsScreenUiState.Loading -> showLoadingUi()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbarSettings.setNavigationOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun showContentUi(
        name: String,
        email: String,
        useFingerprintToLogin: Boolean,
        userPin: String
    ) {
        hideLoading()
        // todo перенести логику во viewmodel
        binding.apply {
            editTextUserName.setText(name)
            editTextUserEmail.setText(email)
            switchUseFingerPrintToLogin.isChecked = useFingerprintToLogin
            toolbarSettings.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item__save_settings -> { // todo: save settings
                        settingsScreenViewModel.saveSettings(
                            userName = editTextUserName.text.toString(),
                            userEmail = editTextUserEmail.text.toString(),
                            useFingerprintToLogin = switchUseFingerPrintToLogin.isChecked,
                            userPin = userPin
                        )
                        true
                    }

                    else -> false
                }
            }
            buttonActionToChangePinCode.setOnClickListener {
                findNavController().navigate(R.id.action_settings_to_changePin)
            }
        }
    }

    private fun showFailedUi() {
        Snackbar.make(
            requireView(),
            R.string.error_message,
            Snackbar.LENGTH_SHORT
        ).show()
        hideLoading()
    }

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showLoading() {
        binding.linearProgressIndicatorSettings.isVisible = true
    }

    private fun hideLoading() {
        binding.linearProgressIndicatorSettings.isVisible = false
        Log.d("UI State", "hide loading")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}