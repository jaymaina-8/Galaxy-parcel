package com.jaytech.galaxytransporter.ui.theme.screens.Parcel

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_RECEIPT
import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
import com.jaytech.galaxytransporter.ui.theme.data.Parcel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelFormScreen(
    navController: NavController,
    parcelViewModel: ParcelViewModel = viewModel()
) {
    val context = LocalContext.current
    var senderName by remember { mutableStateOf("") }
    var senderPhone by remember { mutableStateOf("") }
    var goodsType by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var value by remember { mutableStateOf("") }
    var receiverName by remember { mutableStateOf("") }
    var receiverPhone by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    val isLoading by parcelViewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Parcel") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = senderName, onValueChange = { senderName = it }, label = { Text("Sender Name") })
            OutlinedTextField(value = senderPhone, onValueChange = { senderPhone = it }, label = { Text("Sender Phone") })
            OutlinedTextField(value = goodsType, onValueChange = { goodsType = it }, label = { Text("Goods Type") })
            OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
            OutlinedTextField(value = value, onValueChange = { value = it }, label = { Text("Value") })
            OutlinedTextField(value = receiverName, onValueChange = { receiverName = it }, label = { Text("Receiver Name") })
            OutlinedTextField(value = receiverPhone, onValueChange = { receiverPhone = it }, label = { Text("Receiver Phone") })
            OutlinedTextField(value = destination, onValueChange = { destination = it }, label = { Text("Destination") })
            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Delivery Price (KES)") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (senderName.isBlank() || senderPhone.isBlank() || receiverName.isBlank()) {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val newParcel = Parcel(
                        senderName = senderName,
                        senderPhone = senderPhone,
                        receiverName = receiverName,
                        receiverPhone = receiverPhone,
                        goodsType = goodsType,
                        quantity = quantity,
                        value = value,
                        destination = destination,
                        price = price,
                        timestamp = System.currentTimeMillis()
                    )

                    parcelViewModel.submitParcel(
                        parcel = newParcel,
                        onSuccess = {
                            navController.currentBackStackEntry
                                ?.savedStateHandle
                                ?.set("parcel", newParcel)

                            navController.navigate(ROUTE_RECEIPT)
                        },
                        onFailure = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        }
                    )
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text("Submit Parcel")
                }
            }
        }
    }
}
