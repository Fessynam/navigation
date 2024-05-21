package com.tuyoleni.smartspend.screen.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(navController: NavController) {
    var showDropdownMenu by rememberSaveable { mutableStateOf(false) }
    val auth = FirebaseAuth.getInstance()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Add a dropdown menu for logout
                    IconButton(onClick = {showDropdownMenu = true}) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More options"
                        )
                    }
                    DropdownMenu(
                        expanded = showDropdownMenu,
                        onDismissRequest = { showDropdownMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Logout",
                                    color = MaterialTheme.colorScheme.error,
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Logout,
                                    contentDescription = "Logout"
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.ChevronRight,
                                    contentDescription = "Navigate to login"
                                )
                            },
                            onClick = {
                                auth.signOut()
                                navController.popBackStack()
                                navController.navigate("login")
                                showDropdownMenu = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
            )
        }
    ) { padding ->
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding()),
        ) {
            //TODO: Implement the profile screen

        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    val navController = rememberNavController()
    UserScreen(navController)
}