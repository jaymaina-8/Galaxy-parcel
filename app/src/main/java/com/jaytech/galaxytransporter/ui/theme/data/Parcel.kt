import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parcel(
    val id: String = "",
    val senderName: String = "",
    val senderPhone: String = "",
    val receiverName: String = "",
    val receiverPhone: String = "",
    val goodsType: String = "",
    val quantity: String = "",
    val value: String = "",
    val destination: String = "",
    val price: String = "",
    val timestamp: Long? = null,
    val status: String = ""
) : Parcelable
