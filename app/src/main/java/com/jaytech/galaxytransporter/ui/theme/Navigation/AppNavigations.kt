package com.jaytech.galaxytransporter.ui.theme.Navigation

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.composable
import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
import com.jaytech.galaxytransporter.ui.theme.screens.history.HistoryScreen
import com.jaytech.galaxytransporter.ui.theme.screens.home.HomeScreen
import androidx.navigation.compose.NavHost
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import com.jaytech.galaxytransporter.ui.theme.screens.Admin.RegisterScreen
import com.jaytech.galaxytransporter.ui.theme.screens.Detail.DetailScreen
import com.jaytech.galaxytransporter.ui.theme.screens.Parcel.ParcelForm
import com.jaytech.galaxytransporter.ui.theme.screens.SplashScreen
import com.jaytech.galaxytransporter.ui.theme.screens.receipt.ReceiptScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    val parcelViewModel: ParcelViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = ROUTE_SPLASH
    ) {
        // Splash Screen
        composable(ROUTE_SPLASH) {
            SplashScreen(navController)
        }


        // Register (Login) Screen
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }

        // Home Dashboard
        composable(ROUTE_HOME) {
            HomeScreen(navController)
        }

        // Send Parcel Form
        composable(ROUTE_PARCELFORM) {
            ParcelForm(
                navController)
        }

        // Parcel History
        composable(ROUTE_HISTORY) {
            HistoryScreen(navController, parcelViewModel)
        }
    composable (ROUTE_RECEIPT) {
      ReceiptScreen(
      navController)
        }
        composable(ROUTE_DETAIL) { backStackEntry ->
            val parcel = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Parcel>("parcel")
            DetailScreen(navController = navController, parcel = parcel)
        }

    }
}

