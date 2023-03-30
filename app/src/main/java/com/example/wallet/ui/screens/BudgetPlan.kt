package com.example.wallet.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.wallet.R
import com.example.wallet.ui.viewmodels.BudgetPlanViewModel
import com.google.android.material.transition.MaterialSharedAxis

class BudgetPlan : Fragment(R.layout.fragment__budget_plan) {
    private val budgetPlanViewModel: BudgetPlanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }
}