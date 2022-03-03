package com.examen.carlosgs.services

import android.location.Location
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class FirebaseService {
    private val REF_GPS_HISTORY = "HISTORIAL_GPS"
    private val REF_FILES = "IMAGENES"

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

    fun getFiles(): Task<QuerySnapshot> {
        return mRef.collection(REF_FILES).get()
    }

    fun uploadFile(file: Uri, listener: OnUploadFileListener) {
        val storage = FirebaseStorage.getInstance()
        val ref = storage.reference.child("files/${Date().time}")

        val uploadTask = ref.putFile(file)

        //Comenzar a subir imagen, para obtener su url y guardarlo en coleccion
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    listener.onError()
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val _file = hashMapOf(
                    "url" to task.result.toString(),
                    "fecha" to FieldValue.serverTimestamp()
                )
                mRef.collection(REF_FILES).add(_file).addOnCompleteListener {
                    if (it.isSuccessful) {
                        listener.onSuccess()
                    } else {
                        listener.onError()
                    }
                }
            } else {
                listener.onError()
            }
        }
    }

    interface OnUploadFileListener {
        fun onSuccess()
        fun onError()

    }
}