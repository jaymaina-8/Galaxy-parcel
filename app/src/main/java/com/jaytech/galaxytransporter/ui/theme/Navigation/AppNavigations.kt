package com.jaytech.galaxytransporter.ui.theme.Navigation


import androidx.compose.runtime.Composable

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jaytech.galaxytransporter.ui.theme.screens.Admin.RegisterScreen
import com.jaytech.galaxytransporter.ui.theme.screens.Parcel.ParcelFormScreen
import com.jaytech.galaxytransporter.ui.theme.screens.home.HomeScreen
import com.jaytech.galaxytransporter.ui.theme.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "splash") {
        composable(ROUTE_SPLASH) { SplashScreen(navController) }
        composable(ROUTE_REGISTER) { RegisterScreen(navController) }
        composable(ROUTE_HOME) { HomeScreen(navController) }
    composable(ROUTE_PARCELFORM) { ParcelFormScreen(navController) }
    }
}