package com.examen.carlosgs.ui.upload

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.carlosgs.data.model.FileModel
import com.examen.carlosgs.services.FirebaseService
import java.lang.Exception

class UploadFilesViewModel : ViewModel(), FirebaseService.OnUploadFileListener {

    val filesListData = MutableLiveData<List<FileModel>>()
    val errorData = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    init {
        loadFilesFromFirebase()
    }

    private fun loadFilesFromFirebase() {
        loading.postValue(true)
        FirebaseService().getFiles().addOnCompleteListener() { result ->
            val list  = mutableListOf<FileModel>()
            for (document in result.result) {
                try {
                    list.add(document.toObject(FileModel::class.java))
                } catch (e : Exception) {
                    e.printStackTrace()

                }
            }
            loading.postValue(false)
            filesListData.postValue(list)
        }
    }

    fun uploadFile(file: Uri){
        loading.postValue(true)
        FirebaseService().uploadFile(file, this)
    }


    override fun onSuccess() {
        loadFilesFromFirebase()//recargar la coleccion de imagenes para mostrar la que se subio
        loading.postValue(false)
    }

    override fun onError() {
        errorData.postValue(true)
        loading.postValue(false)
    }
}