package com.example.navigation.screen.notification

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable
fun NotificationScreen(navController: NavHostController) {
    Box (
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ){
        Text(
            text = "Notification Page"
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NotificationScreenPreview(){
//    NotificationScreen(navController)
//}