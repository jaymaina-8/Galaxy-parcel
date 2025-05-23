package com.jaytech.galaxytransporter.ui.theme.screens.Recipt

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.jaytech.galaxytransporter.R
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(
    navController: NavController,
    parcel1: Parcel = Parcel()
) {
    val context = LocalContext.current
    val view = LocalView.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Receipt") },
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ✅ Logo + Company
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF6200EE))
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.gt),
                    contentDescription = "Company Logo",
                    modifier = Modifier
                        .height(80.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Galaxy Parcel Transporting Company",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }

            Divider(color = Color(0xFF6200EE), thickness = 3.dp)

            Text("Parcel Sent Successfully!", style = MaterialTheme.typography.titleMedium)

            Text("Sender Info", style = MaterialTheme.typography.titleMedium)
            InfoItem("Name", parcel1.senderName)
            InfoItem("Phone", parcel1.senderPhone)

            Text("Receiver Info", style = MaterialTheme.typography.titleMedium)
            InfoItem("Name", parcel1.receiverName)
            InfoItem("Phone", parcel1.receiverPhone)

            Text("Parcel Info", style = MaterialTheme.typography.titleMedium)
            InfoItem("Goods", parcel1.goodsType)
            InfoItem("Quantity", parcel1.quantity)
            InfoItem("Value", parcel1.value)
            InfoItem("Destination", parcel1.destination)
            InfoItem("Delivery Price", "KES ${parcel1.price}")

            Text("Status", style = MaterialTheme.typography.titleMedium)
            InfoItem(
                "Date",
                SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date())
            )
            InfoItem("Parcel ID", parcel1.id ?: "N/A")

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { navController.navigate("home") }, modifier = Modifier.weight(1f)) {
                    Text("Done")
                }

                Spacer(Modifier.width(8.dp))

                Button(
                    onClick = {
                        val bitmap = captureScreen(view)
                        if (bitmap != null) {
                            shareBitmap(bitmap, context)
                        } else {
                            Toast.makeText(context, "Error capturing receipt", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Print/Share")
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Thank you for choosing Galaxy Parcel!",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic)
            )
        }
    }
}

@Composable
fun InfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
    Divider()
}

fun captureScreen(view: View): Bitmap? {
    return try {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        bitmap
    } catch (e: Exception) {
        null
    }
}

fun shareBitmap(bitmap: Bitmap, context: android.content.Context) {
    val file = File(context.cacheDir, "receipt.png")
    FileOutputStream(file).use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
    val intent = android.content.Intent().apply {
        action = android.content.Intent.ACTION_SEND
        type = "image/png"
        putExtra(android.content.Intent.EXTRA_STREAM, uri)
        addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(android.content.Intent.createChooser(intent, "Share Receipt"))
}
