package com.jaytech.galaxytransporter.ui.theme.screens.custorbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp


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
            containerColor = Color(0xFF0D47A1) // Deep Blue
        )
    )
}
@Composable
fun CustomBottomBar(onItemSelected: (String) -> Unit) {
    NavigationBar(
        containerColor = Color(0xFF0D47A1),
        contentColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            alwaysShowLabel = true
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("send") },
            icon = { Icon(Icons.Default.Send, contentDescription = "Send") },
            label = { Text("Send Parcel") },
            alwaysShowLabel = true
        )

        NavigationBarItem(
            selected = false,
            onClick = { onItemSelected("logout") },
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Logout") },
            label = { Text("Logout") },
            alwaysShowLabel = true
        )
    }
}
