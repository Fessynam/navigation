package com.example.navigation.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.components.navigation.bottom.barItems

@Composable
fun HomeScreen(navController: NavHostController) {
   // TopNavigationBar(title = barItems.first { it.router == navController.currentDestination?.route }.title)
    Box(
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Page"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController)
}