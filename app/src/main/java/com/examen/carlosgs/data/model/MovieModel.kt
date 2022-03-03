package com.examen.carlosgs.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.examen.carlosgs.services.APIService
import java.io.Serializable

@Entity(tableName = MovieModel.TABLE_NAME)
class MovieModel(

    @ColumnInfo(name = "adult")
    val adult: Boolean?,

    @ColumnInfo(name = "backdrop_path")
    val backdrop_path: String?,

    @ColumnInfo(name = "budget")
    val budget: Long?,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "original_language")
    val original_language: String?,

    @ColumnInfo(name = "original_title")
    val original_title: String?,

    @ColumnInfo(name = "poster_path")
    val poster_path: String?,

    @ColumnInfo(name = "overview")
    val overview: String?,

    @ColumnInfo(name = "popularity")
    val popularity: Double?,

    @ColumnInfo(name = "release_date")
    val release_date: String?,

    @ColumnInfo(name = "revenue")
    val revenue: Long?,

    @ColumnInfo(name = "runtime")
    val runtime: Long?,

    @ColumnInfo(name = "status")
    val status: String?,

    @ColumnInfo(name = "tagline")
    val tagline: String?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "video")
    val video: Boolean?,

    @ColumnInfo(name = "vote_average")
    val vote_average: Double?,

    @ColumnInfo(name = "vote_count")
    val vote_count: Long?,

    ) : Serializable {

    companion object {
        const val TABLE_NAME = "movies"
    }

    fun getUrlBanner(): String {
        return APIService.URL_IMAGES + backdrop_path
    }

    fun getUrl(): String {
        return APIService.URL_IMAGES + poster_path
    }
}