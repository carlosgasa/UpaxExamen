package com.examen.carlosgs.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.examen.carlosgs.data.model.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class MoviesDB : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}