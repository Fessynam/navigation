package com.example.navigation.components.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DataSaverOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import com.example.navigation.screen.Screens

val barItems = listOf(
    Screens(
        title = "Home",
        selected = Icons.Filled.Home,
        unselected = Icons.Outlined.Home,
        router = "home"
    ),

    Screens(
        title = "Budget",
        selected = Icons.Filled.DataSaverOn,
        unselected = Icons.Outlined.DataSaverOn,
        router = "budget"
    ),

    Screens(
        title = "Notification",
        selected = Icons.Filled.Notifications,
        unselected = Icons.Outlined.Notifications,
        router = "notification"
    ),

    Screens(
        title = "User",
        selected = Icons.Filled.Person,
        unselected = Icons.Outlined.Person,
        router = "user"
    )
)