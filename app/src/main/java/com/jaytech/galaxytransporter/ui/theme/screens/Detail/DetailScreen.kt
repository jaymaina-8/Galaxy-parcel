package com.jaytech.galaxytransporter.ui.theme.screens.Detail

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_PARCELFORM
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import com.jaytech.galaxytransporter.ui.theme.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    parcel: Parcel?
) {
    val context = LocalContext.current

    // Get the parcel passed from HistoryScreen
    val parcel = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Parcel>("parcel")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text =  "Parcel Datails" ,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary // white
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer // light background for body
    ) { padding ->

        if (parcel == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Parcel data not found.")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Parcel ID: ${parcel.id}", style = MaterialTheme.typography.titleLarge)
                Text("From: ${parcel.senderName} - ${parcel.senderPhone}")
                Text("To: ${parcel.receiverName} - ${parcel.receiverPhone}")
                Text("Goods Type: ${parcel.goodsType}")
                Text("Quantity: ${parcel.quantity}")
                Text("Value: ${parcel.value}")
                Text("Destination: ${parcel.destination}")
                Text("Price: KES ${parcel.price}")
                Text("Date Sent: ${formatDate(parcel.timestamp)}")

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Edit Button
                    Button(
                        onClick = {
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("editParcel", parcel)
                            navController.navigate(ROUTE_PARCELFORM)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Edit")
                    }

                    // Delete Button
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        onClick = {
                            FirebaseFirestore.getInstance()
                                .collection("parcels")
                                .document(parcel.id)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Parcel deleted", Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, "Failed to delete parcel", Toast.LENGTH_SHORT).show()
                                }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}
