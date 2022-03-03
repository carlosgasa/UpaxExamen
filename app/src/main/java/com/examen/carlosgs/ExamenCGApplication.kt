package com.examen.carlosgs

import android.app.Application
import androidx.room.Room
import com.examen.carlosgs.data.database.MoviesDB

class ExamenCGApplication : Application() {

    companion object {
        var URL_BASE = ""
        var MOVIES_API_KEY = ""

        lateinit var moviesDB : MoviesDB
        private const val DATABASE_NAME = "movies_database"

    }

    override fun onCreate() {
        super.onCreate()

        URL_BASE = getString(R.string.base_url_movies)
        MOVIES_API_KEY = getString(R.string.api_key_movies)

        //inicializar bd
        moviesDB = Room.databaseBuilder(this, MoviesDB::class.java, DATABASE_NAME).allowMainThreadQueries().build()

    }
}
