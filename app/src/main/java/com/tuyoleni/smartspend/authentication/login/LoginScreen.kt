package com.tuyoleni.smartspend.authentication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.tuyoleni.smartspend.authentication.User
import com.tuyoleni.smartspend.components.navigation.top.ScreenTopAppBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(navController: NavController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() } // Moved outside of LaunchedEffect
    val auth = FirebaseAuth.getInstance()
    val user = User(
        name = "", email = email, password = password
    )

    Scaffold(topBar = {
        ScreenTopAppBar(
            title = navController.currentBackStackEntry?.destination?.route ?: "Nonexistent"
        )
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
                .padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            // Email text field
            item {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Password text field
            item {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Submit button
            item {
                Button(
                    onClick = {
                        auth.signInWithEmailAndPassword(user.email, user.password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    navController.navigate("home")
                                } else {
                                    CoroutineScope(Dispatchers.Default).launch {
                                        snackbarHostState.showSnackbar(
                                            message = task.exception?.message ?: "Unknown error"
                                        )
                                    }
                                }
                            }
                    }, modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                ) {
                    Text("Login")
                }
            }

            // Signup link
            item {
                TextButton(
                    onClick = { navController.navigate("signup") },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Don't have an account? Sign up")
                }
            }
        }
    }
    SnackbarHost(hostState = snackbarHostState)
    LaunchedEffect(key1 = true) {
        snackbarHostState.showSnackbar("Login failed. Please try again.")
    }
}