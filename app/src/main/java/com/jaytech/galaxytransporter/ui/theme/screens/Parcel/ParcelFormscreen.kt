package com.jaytech.galaxytransporter.ui.theme.screens.Parcel

import androidx.compose.foundation.text.KeyboardOptions

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_RECEIPT
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcelForm(
    navController: NavController,
    viewModel: ParcelViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()

    // Get parcel if editing
    val existingParcel = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Parcel>("editParcel")

    var senderName by remember { mutableStateOf(existingParcel?.senderName ?: "") }
    var senderPhone by remember { mutableStateOf(existingParcel?.senderPhone ?: "") }
    var goodsType by remember { mutableStateOf(existingParcel?.goodsType ?: "Electronics") }
    var quantity by remember { mutableStateOf(existingParcel?.quantity ?: "") }
    var value by remember { mutableStateOf(existingParcel?.value ?: "") }
    var receiverName by remember { mutableStateOf(existingParcel?.receiverName ?: "") }
    var receiverPhone by remember { mutableStateOf(existingParcel?.receiverPhone ?: "") }
    var destination by remember { mutableStateOf(existingParcel?.destination ?: "Nairobi") }
    var price by remember { mutableStateOf(existingParcel?.price ?: "") }

    val goodsTypes = listOf("Electronics", "Clothing", "Books", "Furniture", "Other")
    val destinations = listOf("Nairobi", "Mombasa", "Kisumu", "Eldoret", "Other")

    // Dropdown expansion state
    var goodsTypeExpanded by remember { mutableStateOf(false) }
    var destinationExpanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (existingParcel != null) "Edit Parcel" else "Send Parcel",
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

    Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = senderName,
                onValueChange = { senderName = it },
                label = { Text("Sender Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = senderPhone,
                onValueChange = { senderPhone = it },
                label = { Text("Sender Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Goods Type Dropdown
            ExposedDropdownMenuBox(
                expanded = goodsTypeExpanded,
                onExpandedChange = { goodsTypeExpanded = !goodsTypeExpanded }
            ) {
                OutlinedTextField(
                    value = goodsType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Goods Type") },
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = goodsTypeExpanded,
                    onDismissRequest = { goodsTypeExpanded = false }
                ) {
                    goodsTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type) },
                            onClick = {
                                goodsType = type
                                goodsTypeExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = value,
                onValueChange = { value = it },
                label = { Text("Declared Value") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = receiverName,
                onValueChange = { receiverName = it },
                label = { Text("Receiver Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = receiverPhone,
                onValueChange = { receiverPhone = it },
                label = { Text("Receiver Phone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // Destination Dropdown
            ExposedDropdownMenuBox(
                expanded = destinationExpanded,
                onExpandedChange = { destinationExpanded = !destinationExpanded }
            ) {
                OutlinedTextField(
                    value = destination,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Destination") },
                    trailingIcon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = destinationExpanded,
                    onDismissRequest = { destinationExpanded = false }
                ) {
                    destinations.forEach { dest ->
                        DropdownMenuItem(
                            text = { Text(dest) },
                            onClick = {
                                destination = dest
                                destinationExpanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Delivery Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (senderName.isBlank() || receiverName.isBlank() ||
                        senderPhone.isBlank() || receiverPhone.isBlank() ||
                        goodsType.isBlank() || quantity.isBlank() ||
                        value.isBlank() || destination.isBlank() || price.isBlank()
                    ) {
                        Toast.makeText(context, "Please fill all fields.", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val parcel = Parcel(
                        id = existingParcel?.id ?: UUID.randomUUID().toString(),
                        senderName = senderName,
                        senderPhone = senderPhone,
                        goodsType = goodsType,
                        quantity = quantity,
                        value = value,
                        receiverName = receiverName,
                        receiverPhone = receiverPhone,
                        destination = destination,
                        price = price,
                        timestamp = System.currentTimeMillis(),
                        status = existingParcel?.status ?: "Pending"
                    )

                    viewModel.submitParcel(
                        parcel = parcel,
                        onSuccess = {
                            navController.navigate(ROUTE_RECEIPT)
                        },
                        onFailure = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(if (existingParcel != null) "Update Parcel" else "Submit Parcel")
                }
            }
        }
    }
}


//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavController
//import com.jaytech.galaxytransporter.ui.theme.Navigation.ROUTE_RECEIPT
//import com.jaytech.galaxytransporter.ui.theme.model.ParcelViewModel
//import com.jaytech.galaxytransporter.ui.theme.data.Parcel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ParcelForm(
//    navController: NavController,
//    parcelViewModel: ParcelViewModel = viewModel()
//) {
//    val context = LocalContext.current
//
//    var senderName by remember { mutableStateOf("") }
//    var senderPhone by remember { mutableStateOf("") }
//    var goodsType by remember { mutableStateOf("") }
//    var quantity by remember { mutableStateOf("") }
//    var value by remember { mutableStateOf("") }
//    var receiverName by remember { mutableStateOf("") }
//    var receiverPhone by remember { mutableStateOf("") }
//    var destination by remember { mutableStateOf("") }
//    var price by remember { mutableStateOf("") }
//
//    val isLoading by parcelViewModel.isLoading.collectAsState()
//    val scrollState = rememberScrollState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("New Parcel") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .padding(16.dp)
//                .verticalScroll(scrollState)
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            OutlinedTextField(value = senderName, onValueChange = { senderName = it }, label = { Text("Sender Name") })
//            OutlinedTextField(value = senderPhone, onValueChange = { senderPhone = it }, label = { Text("Sender Phone") })
//            OutlinedTextField(value = goodsType, onValueChange = { goodsType = it }, label = { Text("Goods Type") })
//            OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantity") })
//            OutlinedTextField(value = value, onValueChange = { value = it }, label = { Text("Value") })
//            OutlinedTextField(value = receiverName, onValueChange = { receiverName = it }, label = { Text("Receiver Name") })
//            OutlinedTextField(value = receiverPhone, onValueChange = { receiverPhone = it }, label = { Text("Receiver Phone") })
//            OutlinedTextField(value = destination, onValueChange = { destination = it }, label = { Text("Destination") })
//            OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Delivery Price (KES)") })
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    if (senderName.isBlank() || senderPhone.isBlank() || receiverName.isBlank()) {
//                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
//                        return@Button
//                    }
//
//                    val newParcel = Parcel(
//                        senderName = senderName,
//                        senderPhone = senderPhone,
//                        receiverName = receiverName,
//                        receiverPhone = receiverPhone,
//                        goodsType = goodsType,
//                        quantity = quantity,
//                        value = value,
//                        destination = destination,
//                        price = price,
//                        timestamp = System.currentTimeMillis()
//                    )
//
//                    parcelViewModel.submitParcel(
//                        parcel = newParcel,
//                        onSuccess = {
//                            navController.currentBackStackEntry
//                                ?.savedStateHandle
//                                ?.set("parcel", newParcel)
//                            navController.navigate(ROUTE_RECEIPT)
//                        },
//                        onFailure = {
//                            Toast.makeText(context, "Failed to submit parcel", Toast.LENGTH_LONG).show()
//                        }
//                    )
//                },
//                enabled = !isLoading,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                if (isLoading) {
//                    CircularProgressIndicator(
//                        color = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier.size(20.dp)
//                    )
//                } else {
//                    Text("Submit Parcel")
//                }
//            }
//        }
//    }
//}
