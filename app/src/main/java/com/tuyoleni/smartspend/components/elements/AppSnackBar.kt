package com.tuyoleni.smartspend.components.elements

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AppSnackBar(errorMessage: String) {
    Snackbar(
//        containerColor = Color.Red,
//        contentColor = Color.White,
    ) {
        Text(text = errorMessage)
    }
}