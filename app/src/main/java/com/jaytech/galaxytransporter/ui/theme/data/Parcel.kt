package com.jaytech.galaxytransporter.ui.theme.data

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Parcel(
    val id: String = "",
    val senderName: String = "",
    val senderPhone: String = "",
    val goodsType: String = "",
    val quantity: String = "",
    val value: String = "",
    val receiverName: String = "",
    val receiverPhone: String = "",
    val destination: String = "",
    val price: String = "",
    val timestamp: Long? = null,
    val status: String = ""

) : Parcelable
