package com.example.wallet.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wallet.R
import com.google.android.material.transition.MaterialSharedAxis

class Login : Fragment(R.layout.fragment__login_screen) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }
}