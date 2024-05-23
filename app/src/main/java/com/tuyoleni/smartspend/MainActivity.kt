package com.tuyoleni.smartspend

import android.annotation.SuppressLint
import android.os.Build.VERSION_CODES.O
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.tuyoleni.smartspend.authentication.login.LoginScreen
import com.tuyoleni.smartspend.authentication.register.RegisterScreen
import com.tuyoleni.smartspend.components.navigation.bottom.BottomNavigationBar
import com.tuyoleni.smartspend.components.navigation.bottom.barItems
import com.tuyoleni.smartspend.screen.budget.BudgetScreen
import com.tuyoleni.smartspend.screen.home.HomeScreen
import com.tuyoleni.smartspend.screen.notification.NotificationScreen
import com.tuyoleni.smartspend.screen.profile.UserScreen
import com.tuyoleni.smartspend.ui.theme.SmartSpendTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            SmartSpendTheme {
                val auth = FirebaseAuth.getInstance()
                val navController = rememberNavController()
                val isLoggedIn = auth.currentUser != null
                val startDestination = if (isLoggedIn) "home" else "login"
                setupBudgetUpdateListener(this)

                Scaffold(bottomBar = {
                    if (isLoggedIn) {
                        BottomNavigationBar(screens = barItems, navController = navController)
                    }
                }, containerColor = MaterialTheme.colorScheme.surfaceContainer) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("signup") { RegisterScreen(navController) }

                        if (isLoggedIn) {
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
}