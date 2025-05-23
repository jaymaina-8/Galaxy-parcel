package com.jaytech.galaxytransporter.ui.theme.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HISTORY
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HOME
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_PARCELFORM
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_REGISTER
import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomBottomBar
import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomTopBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "Galaxy Parcel Dashboard")
        },
        bottomBar = {
            CustomBottomBar { selected ->
                when (selected) {
                    "home" -> navController.navigate(ROUTE_HOME) { launchSingleTop = true }
                    "send" -> navController.navigate(ROUTE_PARCELFORM) { launchSingleTop = true }
                    "history" -> navController.navigate(ROUTE_HISTORY) { launchSingleTop = true }
                    "logout" -> {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("register") {
                            popUpTo(ROUTE_HOME) { inclusive = true }
                        }
                    }
                }
            }
        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome, Admin!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    navController.navigate(ROUTE_PARCELFORM)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Send Parcel", fontSize = 16.sp)
            }
        }
    }
}
