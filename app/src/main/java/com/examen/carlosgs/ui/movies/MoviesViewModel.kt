package com.examen.carlosgs.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examen.carlosgs.data.model.MovieModel
import com.examen.carlosgs.data.MoviesRepository

class MoviesViewModel : ViewModel() {

    val moviesListData = MutableLiveData<List<MovieModel>>()

    suspend fun loadPopularMovies() {
        moviesListData.postValue(MoviesRepository().getPopularMovies())
    }

}