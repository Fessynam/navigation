package com.tuyoleni.smartspend

import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tuyoleni.smartspend.components.navigation.bottom.BottomNavigationBar
import com.tuyoleni.smartspend.components.navigation.bottom.barItems
import com.tuyoleni.smartspend.screen.budget.BudgetScreen
import com.tuyoleni.smartspend.screen.home.HomeScreen
import com.tuyoleni.smartspend.screen.notification.NotificationScreen
import com.tuyoleni.smartspend.screen.profile.UserScreen
import com.tuyoleni.smartspend.ui.theme.SmartSpendTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartSpendTheme {
                val navController = rememberNavController()

                Scaffold(bottomBar = {
                    BottomNavigationBar(screens = barItems, navController = navController)
                }) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("home") { HomeScreen() }
                        composable("budget") { BudgetScreen(navController) }
                        composable("notification") { NotificationScreen(navController) }
                        composable("user") { UserScreen(navController) }
                    }
                }
            }
        }
    }
}
