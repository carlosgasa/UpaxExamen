package com.examen.carlosgs.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.examen.carlosgs.data.model.MovieModel

@Dao
interface MovieDao {
    @Insert
    fun insert(movieModel: MovieModel)

    @Update
    fun update(vararg movieModel : MovieModel)

    @Query("SELECT * FROM " + MovieModel.TABLE_NAME)
    fun getAll() : List<MovieModel>

    @Query("DELETE FROM " + MovieModel.TABLE_NAME)
    fun deleteAll()
}