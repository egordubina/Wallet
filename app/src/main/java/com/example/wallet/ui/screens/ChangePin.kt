package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.wallet.R
import com.example.wallet.databinding.FragmentChangePinBinding
import com.example.wallet.ui.uistate.ChangePinUiState
import com.example.wallet.ui.viewmodels.ChangePinViewModel
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class ChangePin : Fragment(R.layout.fragment__change_pin) {
    private var _binding: FragmentChangePinBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val changePinViewModel: ChangePinViewModel by viewModels { ChangePinViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                changePinViewModel.uiState.collect { uiState ->
                    when (uiState) {
                        ChangePinUiState.Content -> showContentUi()
                        ChangePinUiState.Error -> showErrorUi()
                        ChangePinUiState.IncorrectPin -> showIncorrectUi()
                        ChangePinUiState.Loading -> showLoadingUi()
                        ChangePinUiState.Success -> showSuccessUi()
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
        _binding = FragmentChangePinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showContentUi() {
        binding.apply {

        }
    }

    private fun showLoadingUi() {
        binding.apply {

        }
    }

    private fun showSuccessUi() {
        binding.apply {

        }
    }

    private fun showIncorrectUi() {
        binding.apply {

        }
    }

    private fun showErrorUi() {
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}