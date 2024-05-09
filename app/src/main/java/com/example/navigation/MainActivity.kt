package com.example.navigation

// Import the NavigationBar composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigation.components.navigation.bottom.BottomNavigationBar
import com.example.navigation.components.navigation.bottom.barItems
import com.example.navigation.screen.budget.BudgetScreen
import com.example.navigation.screen.home.HomeScreen
import com.example.navigation.screen.notification.NotificationScreen
import com.example.navigation.screen.profile.UserScreen
import com.example.navigation.ui.theme.NavigationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                val navController = rememberNavController()

                Scaffold(
//                    topBar = {
//                        TopNavigationBar(navController = navController)
//                    },
                    bottomBar = {
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
