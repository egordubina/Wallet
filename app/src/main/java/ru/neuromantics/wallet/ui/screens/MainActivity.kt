package ru.neuromantics.wallet.ui.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import ru.neuromantics.wallet.R
import ru.neuromantics.wallet.ui.WalletNavHost

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        val navHostFragment: NavHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
//        val navController = navHostFragment.navController
        setContent {
            Mdc3Theme {
                WalletNavHost()
            }
        }
    }
}