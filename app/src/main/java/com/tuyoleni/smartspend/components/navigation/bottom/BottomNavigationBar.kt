package com.tuyoleni.smartspend.components.navigation.bottom

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuyoleni.smartspend.screen.Screens

@Composable
fun BottomNavigationBar(screens: List<Screens>, navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val containerColor = MaterialTheme.colorScheme.surfaceContainer

    NavigationBar(
        containerColor = containerColor, tonalElevation = 0.dp
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
