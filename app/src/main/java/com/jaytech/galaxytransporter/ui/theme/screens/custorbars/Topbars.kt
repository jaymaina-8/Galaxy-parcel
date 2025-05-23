package com.jaytech.galaxytransporter.ui.theme.screens.custorbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HISTORY
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HOME
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_PARCELFORM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(title: String) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF000000) // Deep Blue
        )
    )
}
@Composable
fun CustomBottomBar(onItemSelected: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { onItemSelected("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Send, contentDescription = "Send Parcel") },
            label = { Text("Send") },
            selected = false,
            onClick = { onItemSelected("send") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "History") },
            label = { Text("History") },
            selected = false,
            onClick = { onItemSelected("history") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
            label = { Text("Logout") },
            selected = false,
            onClick = { onItemSelected("logout") }
        )
    }
}

