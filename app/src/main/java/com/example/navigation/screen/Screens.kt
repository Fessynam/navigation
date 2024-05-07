package com.example.navigation.screen

import androidx.compose.ui.graphics.vector.ImageVector

data class Screens(
    val title: String,
    val selected: ImageVector,
    val unselected: ImageVector,
    val router: String
)
