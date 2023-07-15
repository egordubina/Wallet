package ru.neuromantics.wallet.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.neuromantics.wallet.ui.screens.HomeScreen
import ru.neuromantics.wallet.ui.screens.AddTransactionScreen
import ru.neuromantics.wallet.ui.screens.SettingsScreen

enum class WalletDestinations {
    HOME,
    SETTINGS,
    ADD_TRANSACTION,
}

@Composable
fun WalletNavHost() {
    val navController = rememberNavController()
    val onBackButtonClick: () -> Unit = { navController.navigateUp() }
    NavHost(
        navController = navController,
        startDestination = WalletDestinations.HOME.name
    ) {
        composable(WalletDestinations.HOME.name) {
            HomeScreen(
                onSettingsButtonClick = { navController.navigate(WalletDestinations.SETTINGS.name) },
                onAddTransactionButtonClick = { navController.navigate(WalletDestinations.ADD_TRANSACTION.name) }
            )
        }
        composable(WalletDestinations.SETTINGS.name) {
            SettingsScreen(
                onBackButtonClick = onBackButtonClick
            )
        }
        composable(WalletDestinations.ADD_TRANSACTION.name) {
            AddTransactionScreen(
                onBackButtonClick = onBackButtonClick
            )
        }
    }
}