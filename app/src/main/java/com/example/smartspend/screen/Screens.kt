package com.example.smartspend.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class Screens(
    val title: String,
    val selected: ImageVector,
    val unselected: ImageVector,
    val router: String,
    val content: @Composable (NavController) -> Unit
)
