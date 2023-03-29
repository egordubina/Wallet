package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.wallet.R
import com.example.wallet.data.models.SettingsIds
import com.example.wallet.data.models.SettingsIds.USER_EMAIL
import com.example.wallet.data.models.SettingsIds.USER_NAME
import com.example.wallet.data.models.SettingsIds.USER_PIN
import com.example.wallet.data.models.SettingsIds.USE_FINGERPRINT_TO_LOGIN
import com.example.wallet.databinding.FragmentSettingsScreenBinding
import com.example.wallet.ui.uistate.SettingsScreenUiState
import com.example.wallet.ui.viewmodels.SettingsScreenViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Settings : Fragment(R.layout.fragment__settings_screen) {
    private val settingsScreenViewModel: SettingsScreenViewModel by viewModels { SettingsScreenViewModel.Factory }
    private val currentUserSettings: MutableMap<SettingsIds, Any> = mutableMapOf()
    private var settingsChangeFlag: Boolean = false
    private var _binding: FragmentSettingsScreenBinding? = null
    private val binding get() = _binding!!
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            checkUserSettings()
        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null)
                binding.imageUserPhoto.load(uri) { crossfade(true) }
        }
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
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
        settingsScreenViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is SettingsScreenUiState.Content -> {
                    showContentUi(
                        name = uiState.userName,
                        email = uiState.userEmail,
                        useFingerprintToLogin = uiState.fingerprintLogin
                    )
                    with(currentUserSettings) {
                        set(USER_NAME, uiState.userName)
                        set(USE_FINGERPRINT_TO_LOGIN, uiState.fingerprintLogin)
                        set(USER_EMAIL, uiState.userEmail)
                        set(USER_PIN, uiState.pinCodeToLogin)
                    }
                }

                SettingsScreenUiState.Loading -> showLoadingUi()
                SettingsScreenUiState.Error -> showFailedUi()
            }
        }
        binding.apply {
            toolbarSettings.apply {
                setNavigationOnClickListener { checkUserSettings() }
            }
        }
    }

    private fun showContentUi(
        name: String,
        email: String,
        useFingerprintToLogin: Boolean
    ) {
        hideLoading()
        binding.apply {
            editTextUserName.setText(name)
            editTextUserEmail.setText(email)
            switchUseFingerPrintToLogin.isChecked = useFingerprintToLogin

            toolbarSettings.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_item__save_settings -> {
                        checkUserSettings()
                        true
                    }

                    else -> false
                }
            }
            imageUserPhoto.setOnClickListener {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            buttonActionToChangePinCode.setOnClickListener {
                cardChangePinCode.isVisible = true
                buttonActionToChangePinCode.isVisible = false
            }
            buttonActionSavePinCode.setOnClickListener { checkPinCodes() }
            buttonActionCancelChangePinCode.setOnClickListener {
                cardChangePinCode.isVisible = false
                buttonActionToChangePinCode.isVisible = true
            }
        }
    }

    private fun checkPinCodes() {
        binding.apply {
            textInputLayoutSettingsCurrentPinCode.error = null
            textInputLayoutSettingsNewPinCode.error = null
            when {
                editTextSettingsCurrentPinCode.text.toString() != currentUserSettings[USER_PIN].toString() ->
                    textInputLayoutSettingsCurrentPinCode.error =
                        getString(R.string.incorrect_pin_code)

                editTextSettingsNewPinCode.text.toString().length !in 4..8 ->
                    textInputLayoutSettingsNewPinCode.error =
                        textInputLayoutSettingsNewPinCode.helperText

                editTextSettingsCurrentPinCode.text.toString() == editTextSettingsNewPinCode.text.toString() -> {
                    textInputLayoutSettingsNewPinCode.error = getString(R.string.pin_codes_equals)
                    textInputLayoutSettingsCurrentPinCode.error =
                        getString(R.string.pin_codes_equals)
                }

                else -> {
                    try {
                        editTextSettingsNewPinCode.text.toString().toInt()
                        cardChangePinCode.isVisible = false
                        settingsScreenViewModel.changeSettings(
                            USER_PIN, editTextSettingsNewPinCode.text.toString()
                        )
                        Toast.makeText(
                            requireContext(),
                            R.string.pin_code_has_been_save,
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        textInputLayoutSettingsNewPinCode.error = getString(R.string.only_number_pin_code)
                    }
                }
            }
        }
    }

    private fun showFailedUi() {
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
    }

    private fun checkUserSettings() {
        binding.apply {
            textInputLayoutUserName.error = null
            textInputLayoutUserEmail.error = null
            when {
                editTextUserName.text.toString().isEmpty() -> {
                    textInputLayoutUserName.error = textInputLayoutUserName.helperText
                    return
                }

                editTextUserEmail.text.toString().isEmpty() -> {
                    textInputLayoutUserEmail.error = textInputLayoutUserEmail.helperText
                    return
                }

                // fingerprint settings
                currentUserSettings[USE_FINGERPRINT_TO_LOGIN] != switchUseFingerPrintToLogin.isChecked -> {
                    settingsScreenViewModel.changeSettings(
                        USE_FINGERPRINT_TO_LOGIN,
                        switchUseFingerPrintToLogin.isChecked.toString()
                    )
                    settingsChangeFlag = true
                }

                // name settings
                currentUserSettings[USER_NAME] != editTextUserName.text.toString() -> {
                    settingsScreenViewModel.changeSettings(
                        USER_NAME,
                        editTextUserName.text.toString()
                    )
                    settingsChangeFlag = true
                }

                // email settings
                currentUserSettings[USER_EMAIL] != editTextUserEmail.text.toString() -> {
                    settingsScreenViewModel.changeSettings(
                        USER_EMAIL,
                        editTextUserEmail.text.toString()
                    )
                    settingsChangeFlag = true
                }
            }
            if (settingsChangeFlag) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.settings_have_been_saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}