package com.jaytech.galaxytransporter.ui.theme


import java.text.SimpleDateFormat
import java.util.*

fun formatDate(timestamp: Long?): String {
    return if (timestamp != null) {
        val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        sdf.format(Date(timestamp))
    } else {
        "Unknown"
    }
}
