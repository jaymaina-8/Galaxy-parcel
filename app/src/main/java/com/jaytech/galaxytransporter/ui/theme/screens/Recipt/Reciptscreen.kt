package com.jaytech.galaxytransporter.ui.theme.screens.receipt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import com.jaytech.galaxytransporter.ui.theme.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(navController: NavController) {
    val parcel = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Parcel>("parcel")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parcel Receipt") },
            )
        }
    ) { paddingValues ->
        if (parcel == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No parcel data found.")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text("Parcel ID: ${parcel.id}", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                Text("From: ${parcel.senderName} - ${parcel.senderPhone}")
                Text("To: ${parcel.receiverName} - ${parcel.receiverPhone}")
                Spacer(modifier = Modifier.height(12.dp))
                Text("Goods Type: ${parcel.goodsType}")
                Text("Quantity: ${parcel.quantity}")
                Text("Value: ${parcel.value} KES")
                Text("Destination: ${parcel.destination}")
                Text("Price: ${parcel.price} KES")
                Spacer(modifier = Modifier.height(12.dp))
                Text("Date Sent: ${formatDate(parcel.timestamp)}")

                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Done")
                }
            }
        }
    }
}
