package com.examen.carlosgs.data.model

import com.google.gson.annotations.SerializedName


class ResponseMovie (@SerializedName("results") val movies : List<MovieModel>){
}