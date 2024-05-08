package com.example.navigation.components.navigation.bottom

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navigation.screen.Screens

@Composable
fun BottomNavigationBar(screens: List<Screens>, navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = Color.Transparent, tonalElevation = 0.dp
    ) {
        screens.forEachIndexed { index, item ->
            NavigationBarItem(selected = selectedItem == index, onClick = {
                selectedItem = index
                navController.navigate(item.router)
            }, icon = {
                Icon(
                    imageVector = if (selectedItem == index) item.selected else item.unselected,
                    contentDescription = item.title
                )
            }, label = { Text(text = item.title) })
        }
    }
}
