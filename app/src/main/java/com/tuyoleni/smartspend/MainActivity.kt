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
import com.tuyoleni.smartspend.data.SpendingEarningManager
import com.tuyoleni.smartspend.screen.budget.BudgetScreen
import com.tuyoleni.smartspend.screen.home.HomeScreen
import com.tuyoleni.smartspend.screen.notification.NotificationScreen
import com.tuyoleni.smartspend.screen.profile.UserScreen
import com.tuyoleni.smartspend.ui.theme.SmartSpendTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Month
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    companion object {
        val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @RequiresApi(O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            SmartSpendTheme {
                val navController = rememberNavController()
                val isLoggedIn = auth.currentUser != null
                val startDestination = if (isLoggedIn) "home" else "login"

                Scaffold(bottomBar = {
                    if (isLoggedIn) {
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                withContext(Dispatchers.IO) {
                                    generateRandomTransactions() // Execute the function in a background thread
                                }
                                println("Random transactions generated successfully!")
                            } catch (e: Exception) {
                                println("Error generating random transactions: ${e.message}")
                            }
                        }
                        BottomNavigationBar (screens = barItems, navController = navController)
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

    @RequiresApi(O)
    suspend fun generateRandomTransactions() {
        val spendingManager = SpendingEarningManager(FireStoreRepository)
        val random = Random(System.currentTimeMillis()) // Initialize random generator

        // Loop through 12 months
        for (month in Month.entries) {
            val year = LocalDate.now().year // Get current year
            val numDaysInMonth = month.length(false) // Get number of days in the month

            repeat(4) {
                val day = random.nextInt(1, numDaysInMonth + 1)
                val amount = random.nextInt(100, 5000)
                val category = listOf(
                    "Food", "Entertainment", "Utilities", "Transport"
                ).random()
                val date = LocalDate.of(year, month, day)
                spendingManager.addSpending(date, category, amount)
            }

            spendingManager.addEarnings(LocalDate.of(year, month, 30), "Salary", 5000)
            spendingManager.addEarnings(LocalDate.of(year, month, 30).minusMonths(1), "Salary", 10000)
            spendingManager.addEarnings(LocalDate.of(year, month, 30).minusMonths(2), "Salary", 8000)
        }
    }
}