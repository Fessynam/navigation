package com.example.navigation.components.navigation.top

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(text = navController.currentBackStackEntry?.destination?.route ?:"Nonexistent")
        }
    )
}
