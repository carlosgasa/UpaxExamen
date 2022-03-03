package com.examen.carlosgs.data

import com.examen.carlosgs.ExamenCGApplication
import com.examen.carlosgs.data.model.MovieModel
import com.examen.carlosgs.services.APIService
import java.lang.Exception

class MoviesRepository {

    suspend fun getPopularMovies() : List<MovieModel> {
        try {
            val listMovies = APIService.getRetrofit().getTrendMovies()
            ExamenCGApplication.moviesDB.movieDao().deleteAll()//remover t.odo lo local si ya hubo exito en la consulta
            val movies = listMovies.movies
            movies.forEach{
                try {
                    ExamenCGApplication.moviesDB.movieDao().insert(it)//insertar en bd local
                } catch (e : Exception) {e.printStackTrace()}
            }
            listMovies.movies
        } catch (e: Exception){
            e.printStackTrace()
        }

        //Si hubo algun error en la peticion, se regresan las peliculas guardadas en la bd local
        return ExamenCGApplication.moviesDB.movieDao().getAll()
    }
}