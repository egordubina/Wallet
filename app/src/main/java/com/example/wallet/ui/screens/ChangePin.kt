package com.example.wallet.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wallet.R
import com.example.wallet.databinding.FragmentChangePinBinding
import com.example.wallet.ui.viewmodels.ChangePinViewModel

class ChangePin : Fragment(R.layout.fragment__change_pin) {
    private var _binding: FragmentChangePinBinding? = null
    private val binding: FragmentChangePinBinding get() = checkNotNull(_binding)
    private val changePinViewModel: ChangePinViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}