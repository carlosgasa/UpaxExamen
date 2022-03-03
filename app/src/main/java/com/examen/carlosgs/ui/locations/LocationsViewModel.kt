package com.examen.carlosgs.ui.locations

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.carlosgs.data.model.LocationModel
import com.examen.carlosgs.services.FirebaseService
import java.lang.Exception

class LocationsViewModel : ViewModel() {

    val locationsListData = MutableLiveData<List<LocationModel>>()
    val progress = MutableLiveData<Boolean>()

    init {
        loadLocationsFromFirebase()
    }

    private fun loadLocationsFromFirebase() {
        progress.postValue(true)
        FirebaseService().getGPSHistory().addOnCompleteListener() { result ->
            val list  = mutableListOf<LocationModel>()
            for (document in result.result) {
                try {
                    list.add(document.toObject(LocationModel::class.java))
                } catch (e : Exception) {
                    e.printStackTrace()
                    progress.postValue(false)

                }
            }
            locationsListData.postValue(list)
            progress.postValue(false)
        }
    }
}