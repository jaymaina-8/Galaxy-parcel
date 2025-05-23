package com.jaytech.galaxytransporter.ui.theme.screens

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jaytech.galaxytransporter.R
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HOME
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_REGISTER
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_SPLASH
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current

    // This handles navigation delay
    LaunchedEffect(Unit) {
        delay(2000L)  // splash screen delay (2 seconds)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navController.navigate(ROUTE_HOME) {
                popUpTo(ROUTE_SPLASH) { inclusive = true }
            }
        } else {
            navController.navigate(ROUTE_REGISTER) {
                popUpTo(ROUTE_SPLASH) { inclusive = true }
            }
        }
    }

    // UI content for splash screen
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.tt), // your truck image
            contentDescription = "Truck Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


    }
}

