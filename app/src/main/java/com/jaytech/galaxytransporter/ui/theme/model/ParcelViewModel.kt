package com.jaytech.galaxytransporter.ui.theme.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ParcelViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    // 🔄 Holds list of parcels
    private val _parcelList = MutableStateFlow<List<Parcel>>(emptyList())
    val parcelList: StateFlow<List<Parcel>> = _parcelList.asStateFlow()

    // 🔄 Loading state for UI during submission
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var parcelListener: ListenerRegistration? = null

    /**
     * Submit a new parcel to Firebase
     */
    fun submitParcel(
        parcel: Parcel,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        _isLoading.value = true

        db.collection("parcels")
            .add(parcel)
            .addOnSuccessListener {
                _isLoading.value = false
                onSuccess()
            }
            .addOnFailureListener { e ->
                _isLoading.value = false
                onFailure(e.message ?: "Failed to submit parcel.")
            }
    }

    /**
     * Fetch all parcels from Firestore in real-time and sort by latest
     */
    fun fetchParcelsRealtime() {
        // Avoid multiple listeners
        if (parcelListener != null) return

        parcelListener = db.collection("parcels")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                val parcels = snapshot.documents.mapNotNull { doc ->
                    try {
                        val data = doc.data ?: return@mapNotNull null
                        Parcel(
                            id = doc.id,
                            senderName = data["senderName"] as? String ?: "",
                            senderPhone = data["senderPhone"] as? String ?: "",
                            goodsType = data["goodsType"] as? String ?: "",
                            quantity = data["quantity"] as? String ?: "",
                            value = data["value"] as? String ?: "",
                            receiverName = data["receiverName"] as? String ?: "",
                            receiverPhone = data["receiverPhone"] as? String ?: "",
                            destination = data["destination"] as? String ?: "",
                            price = data["price"] as? String ?: "",
                            timestamp = (data["timestamp"] as? Number)?.toLong(),
                            status = data["status"] as? String ?: ""
                        )
                    } catch (e: Exception) {
                        null
                    }
                }

                viewModelScope.launch {
                    _parcelList.emit(parcels)
                }
            }
    }

    /**
     * Manually stop listening to Firestore updates
     */
    fun stopListening() {
        parcelListener?.remove()
        parcelListener = null
    }

    override fun onCleared() {
        super.onCleared()
        stopListening()
    }
}

//
//class ParcelViewModel : ViewModel() {
//
//    private val db = FirebaseFirestore.getInstance()
//
//    // 🔄 Holds list of all parcels for history screen
//    private val _parcelList = MutableStateFlow<List<Parcel>>(emptyList())
//    val parcelList: StateFlow<List<Parcel>> = _parcelList.asStateFlow()
//
//    // 🔄 Loading state for parcel submission
//    private val _isLoading = MutableStateFlow(false)
//    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
//
//    private var parcelListener: ListenerRegistration? = null
//
//    // 🔄 Submit new parcel to Firestore
//    fun submitParcel(
//        parcel: Parcel,
//        onSuccess: () -> Unit,
//        onFailure: (String) -> Unit
//    ) {
//        _isLoading.value = true
//
//        db.collection("parcels")
//            .add(parcel)
//            .addOnSuccessListener {
//                _isLoading.value = false
//                onSuccess()
//            }
//            .addOnFailureListener { exception ->
//                _isLoading.value = false
//                onFailure(exception.message ?: "An error occurred while submitting parcel.")
//            }
//    }
//
//    // 📦 Real-time fetch for parcel history
//    fun fetchParcelsRealtime() {
//        if (parcelListener != null) return  // avoid setting multiple listeners
//
//        parcelListener = db.collection("parcels")
//            .addSnapshotListener { snapshot, error ->
//                if (error != null || snapshot == null) return@addSnapshotListener
//
//                val parcels = snapshot.documents.mapNotNull { doc ->
//                    try {
//                        val data = doc.data ?: return@mapNotNull null
//                        Parcel(
//                            id = doc.id,
//                            senderName = data["senderName"] as? String ?: "",
//                            senderPhone = data["senderPhone"] as? String ?: "",
//                            goodsType = data["goodsType"] as? String ?: "",
//                            quantity = data["quantity"] as? String ?: "",
//                            value = data["value"] as? String ?: "",
//                            receiverName = data["receiverName"] as? String ?: "",
//                            receiverPhone = data["receiverPhone"] as? String ?: "",
//                            destination = data["destination"] as? String ?: "",
//                            price = data["price"] as? String ?: "",
//                            timestamp = (data["timestamp"] as? Number)?.toLong()
//                        )
//                    } catch (e: Exception) {
//                        null
//                    }
//                }
//
//                viewModelScope.launch {
//                    _parcelList.emit(parcels)
//                }
//            }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        parcelListener?.remove()
//    }
//}
