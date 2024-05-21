package com.tuyoleni.smartspend.screen.notification

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tuyoleni.smartspend.components.navigation.top.ScreenTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavController) {
    Scaffold(topBar = {
        ScreenTopAppBar(
            title = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent"
        )
    }) { padding ->
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
        ) {
            //TODO: Implement the Notification screen
            
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview(){
    val navController = rememberNavController()
    NotificationScreen(navController)
}