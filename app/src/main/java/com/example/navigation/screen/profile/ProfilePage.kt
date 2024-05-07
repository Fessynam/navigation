package com.example.navigation.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun UserScreen(navController: NavHostController) {
    Box (
        Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ){
        Text(
            text = "User Page"
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun UserScreenPreview(){
//    UserScreen(navController)
//}