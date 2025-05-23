package com.jaytech.galaxytransporter.ui.theme.screens.history



import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_DETAIL
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HOME
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_PARCELFORM
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import com.jaytech.galaxytransporter.ui.theme.formatDate
import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomBottomBar
import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: ParcelViewModel
) {
    val parcelList by viewModel.parcelList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchParcelsRealtime()
    }

    // Filter & sort
    val filteredList = parcelList
        .filter {
            it.senderName.contains(searchQuery, ignoreCase = true) ||
                    it.receiverName.contains(searchQuery, ignoreCase = true) ||
                    it.destination.contains(searchQuery, ignoreCase = true)
        }
        .sortedByDescending { it.timestamp } // newest first

    Scaffold(
        topBar = { CustomTopBar(title = "Parcel History") },
        bottomBar = {
            CustomBottomBar { selected ->
                when (selected) {
                    "home" -> navController.navigate(ROUTE_HOME)
                    "send" -> navController.navigate(ROUTE_PARCELFORM)
                    "history" -> {} // already here
                    "logout" -> {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("register") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by sender, receiver, destination") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            if (filteredList.isEmpty()) {
                Text("No parcels found.", style = MaterialTheme.typography.bodyLarge)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filteredList) { parcel ->
                        ParcelHistoryItem(parcel = parcel) {
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("parcel", parcel)

                            navController.navigate(ROUTE_DETAIL)
                        }
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
fun ParcelHistoryItem(parcel: Parcel, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text("Parcel ID: ${parcel.id}", style = MaterialTheme.typography.titleSmall)
        Text("To: ${parcel.receiverName} - ${parcel.destination}")
        Text("From: ${parcel.senderName}")
        Text("Date: ${formatDate(parcel.timestamp)}", style = MaterialTheme.typography.labelSmall)
        Text("Status: ${parcel.status}", style = MaterialTheme.typography.bodySmall)
    }
}







//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.google.firebase.auth.FirebaseAuth
//import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_DETAIL
//import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_HOME
//import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_PARCELFORM
//import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_REGISTER
//import com.jaytech.galaxytransporter.ui.theme.data.Parcel
//import com.jaytech.galaxytransporter.ui.theme.formatDate
//import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
//import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomBottomBar
//import com.jaytech.galaxytransporter.ui.theme.screens.custorbars.CustomTopBar
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun HistoryScreen(
//    navController: NavController,
//    viewModel: ParcelViewModel
//) {
//    val parcelList by viewModel.parcelList.collectAsState()
//    var searchQuery by remember { mutableStateOf("") }
//
//    LaunchedEffect(Unit) {
//        viewModel.fetchParcelsRealtime()
//    }
//
//    val filteredList = parcelList
//        .sortedByDescending { it.timestamp }
//        .filter {
//            it.senderName.contains(searchQuery, ignoreCase = true) ||
//                    it.receiverName.contains(searchQuery, ignoreCase = true) ||
//                    it.destination.contains(searchQuery, ignoreCase = true)
//        }
//
//    Scaffold(
//        topBar = { CustomTopBar(title = "Parcel History") },
//        bottomBar = {
//            CustomBottomBar { selected ->
//                when (selected) {
//                    "home" -> navController.navigate(ROUTE_HOME)
//                    "send" -> navController.navigate(ROUTE_PARCELFORM)
//                    "history" -> {}
//                    "logout" -> {
//                        FirebaseAuth.getInstance().signOut()
//                        navController.navigate(ROUTE_REGISTER) {
//                            popUpTo(ROUTE_HOME) { inclusive = true }
//                        }
//                    }
//                }
//            }
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { searchQuery = it },
//                label = { Text("Search by name or destination") },
//                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 12.dp)
//            )
//
//            if (filteredList.isEmpty()) {
//                Text("No parcels found.", style = MaterialTheme.typography.bodyLarge)
//            } else {
//                LazyColumn {
//                    items(filteredList) { parcel ->
//                        ParcelHistoryItem(parcel = parcel) {
//                            navController.currentBackStackEntry?.savedStateHandle?.set("parcel", parcel)
//                            navController.navigate(ROUTE_DETAIL)
//                        }
//                        Divider()
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun ParcelHistoryItem(parcel: Parcel, onClick: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { onClick() }
//            .padding(vertical = 8.dp)
//    ) {
//        Text("Parcel ID: ${parcel.id}", style = MaterialTheme.typography.titleSmall)
//        Text("To: ${parcel.receiverName} - ${parcel.destination}")
//        Text("From: ${parcel.senderName}")
//        Text("Date: ${formatDate(parcel.timestamp)}", style = MaterialTheme.typography.labelSmall)
//    }
//}
