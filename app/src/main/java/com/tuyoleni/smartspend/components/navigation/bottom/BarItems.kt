package com.tuyoleni.smartspend.components.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DataSaverOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DataSaverOn
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import com.tuyoleni.smartspend.screen.Screens
import com.tuyoleni.smartspend.screen.budget.BudgetScreen
import com.tuyoleni.smartspend.screen.home.HomeScreen
import com.tuyoleni.smartspend.screen.notification.NotificationScreen
import com.tuyoleni.smartspend.screen.profile.UserScreen

val barItems = listOf(
    Screens(
        title = "Home",
        selected = Icons.Filled.Home,
        unselected = Icons.Outlined.Home,
        router = "home",
        content = { navController -> HomeScreen() }
    ),

    Screens(
        title = "Budget",
        selected = Icons.Filled.DataSaverOn,
        unselected = Icons.Outlined.DataSaverOn,
        router = "budget",
        content = { navController -> BudgetScreen(navController) }

    ),

    Screens(
        title = "Notification",
        selected = Icons.Filled.Notifications,
        unselected = Icons.Outlined.Notifications,
        router = "notification",
        content = { navController -> NotificationScreen(navController) }

    ),

    Screens(
        title = "User",
        selected = Icons.Filled.Person,
        unselected = Icons.Outlined.Person,
        router = "user",
        content = { navController -> UserScreen(navController) }
    )
)