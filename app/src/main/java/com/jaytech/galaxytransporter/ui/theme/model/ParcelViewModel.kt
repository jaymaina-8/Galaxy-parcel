package com.jaytech.galaxytransporter.ui.theme.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.jaytech.galaxytransporter.ui.theme.data.Parcel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ParcelViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val _parcelList = MutableStateFlow<List<Parcel>>(emptyList())
    val parcelList: StateFlow<List<Parcel>> = _parcelList

    private var parcelListener: ListenerRegistration? = null

    fun fetchParcelsRealtime() {
        // Prevent multiple listeners
        if (parcelListener != null) return

        parcelListener = db.collection("parcels")
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    return@addSnapshotListener
                }

                val parcels = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Parcel::class.java)?.copy(id = doc.id)
                }

                viewModelScope.launch {
                    _parcelList.emit(parcels)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        parcelListener?.remove()
    }
}
