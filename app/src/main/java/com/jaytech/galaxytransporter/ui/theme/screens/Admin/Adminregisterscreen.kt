package com.jaytech.galaxytransporter.ui.theme.screens.Admin


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.jaytech.galaxytransporter.ui.theme.model.AuthResultState
import com.jaytech.galaxytransporter.ui.theme.model.AuthViewModel


@Composable
fun RegisterScreen(navController: NavHostController, viewModel: AuthViewModel = viewModel()) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthResultState.Success -> {
                Toast.makeText(context, (authState as AuthResultState.Success).message, Toast.LENGTH_SHORT).show()
                navController.navigate("home") {
                    popUpTo("register") { inclusive = true }
                }
            }
            is AuthResultState.Failure -> {
                Toast.makeText(context, (authState as AuthResultState.Failure).error, Toast.LENGTH_LONG).show()
            }
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Admin Login", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.loginOrRegister(email, password)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = authState !is AuthResultState.Loading
        ) {
            Text(if (authState is AuthResultState.Loading) "Please wait..." else "Login / Register")
        }
    }
}
