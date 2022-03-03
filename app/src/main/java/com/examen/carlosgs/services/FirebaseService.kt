package com.examen.carlosgs.services
import android.location.Location
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.ExperimentalCoroutinesApi

class FirebaseService {
    private val REF_GPS_HISTORY = "HISTORIAL_GPS"

    private val mRef = FirebaseFirestore.getInstance()

    fun saveLocationGPS(location: Location?) {

        val l = hashMapOf(
            "longitud" to location?.longitude,
            "latitud" to location?.latitude,
            "fecha" to FieldValue.serverTimestamp()
        )
        mRef.collection(REF_GPS_HISTORY).add(l)
    }

    fun getGPSHistory(): Task<QuerySnapshot> {
        return mRef.collection(REF_GPS_HISTORY).get()
    }
}