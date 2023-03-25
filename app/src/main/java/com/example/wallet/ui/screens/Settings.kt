package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.wallet.R
import com.example.wallet.databinding.FragmentSettingsScreenBinding
import com.example.wallet.ui.viewmodels.UserViewModel
import com.google.android.material.transition.MaterialSharedAxis

class Settings : Fragment(R.layout.fragment__settings_screen) {
    private lateinit var binding: FragmentSettingsScreenBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private val userViewModel: UserViewModel by activityViewModels()

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
        }
    }

    private fun checkUserSettings() {
        binding.apply {
            if (editTextUserName.text.toString().isEmpty()) {
                textInputLayoutUserName.error = textInputLayoutUserName.helperText
            } else {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.homeScreen, true).build()
                textInputLayoutUserName.error = null
                userViewModel.setUserName(editTextUserName.text.toString())
                findNavController().navigate(R.id.homeScreen, null, navOptions)
            }
        }
    }
}