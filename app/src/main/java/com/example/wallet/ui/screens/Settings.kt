package com.example.wallet.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.data.models.Settings
import com.example.wallet.data.preferences.WalletPreferences
import com.example.wallet.databinding.FragmentSettingsScreenBinding
import com.example.wallet.ui.uistate.SettingsScreenUiState
import com.example.wallet.ui.viewmodels.SettingsScreenViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Settings : Fragment(R.layout.fragment__settings_screen) {
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModels { SettingsScreenViewModel.Factory }
    private lateinit var binding: FragmentSettingsScreenBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private val currentUserSettings: MutableMap<Settings, Any> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null)
                binding.imageUserPhoto.setImageURI(uri)
        }

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsScreenBinding.bind(view)
        settingsScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is SettingsScreenUiState.Content -> {
                    showContentUi(
                        name = uiState.userName,
                        email = uiState.userEmail,
                        useFingerprintToLogin = uiState.fingerprintLogin
                    )
                    currentUserSettings[Settings.USER_NAME] = uiState.userName
                    currentUserSettings[Settings.USE_FINGERPRINT_TO_LOGIN] = uiState.fingerprintLogin
                    currentUserSettings[Settings.USER_EMAIL] = uiState.userEmail
                    currentUserSettings[Settings.USER_PIN] = uiState.pinCodeToLogin
                }

                SettingsScreenUiState.Loading -> showLoadingUi()
            }
        }
        binding.apply {
            toolbarSettings.apply {
                setNavigationOnClickListener { findNavController().navigateUp() }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menu_item__save_settings -> {
                            checkUserSettings()
                            true
                        }

                        else -> false
                    }
                }
            }
            imageUserPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            switchUseFingerPrintToLogin.apply {
                setOnCheckedChangeListener { _, state ->
                    settingsScreenViewModel.changeSettings(Settings.USE_FINGERPRINT_TO_LOGIN, state)
                }
            }
        }
    }

    private fun showLoadingUi() {
        showLoading()
    }

    private fun showContentUi(
        name: String,
        email: String,
        useFingerprintToLogin: Boolean
    ) {
        hideLoading()
        binding.apply {
            editTextUserName.setText(name)
            switchUseFingerPrintToLogin.isChecked = useFingerprintToLogin
            editTextUserEmail.setText(email)
        }
    }

    private fun showLoading() {
        binding.linearProgressIndicatorSettings.isVisible = true
    }

    private fun hideLoading() {
        binding.linearProgressIndicatorSettings.isVisible = false
    }

    private fun checkUserSettings() {
        binding.apply {
            if (editTextUserName.text.toString().isEmpty()) {
                textInputLayoutUserName.error = textInputLayoutUserName.helperText
            } else {
                textInputLayoutUserName.error = null
                when {
                    currentUserSettings[Settings.USE_FINGERPRINT_TO_LOGIN] != switchUseFingerPrintToLogin.isChecked ->
                        settingsScreenViewModel.changeSettings(Settings.USE_FINGERPRINT_TO_LOGIN, switchUseFingerPrintToLogin.isChecked)
                    currentUserSettings[Settings.USER_NAME] != editTextUserName.text.toString() ->
                        settingsScreenViewModel.changeSettings(Settings.USER_NAME, editTextUserName.text.toString())
                    // todo добавить остальные настройки. логика сохранени обдумать
                }
                findNavController().navigateUp()
            }
        }
    }
}