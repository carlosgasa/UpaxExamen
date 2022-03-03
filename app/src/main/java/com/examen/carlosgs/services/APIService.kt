package com.examen.carlosgs.services

import com.examen.carlosgs.ExamenCGApplication
import com.examen.carlosgs.data.model.ResponseMovie
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface APIService {

    @GET("trending/all/day")
    suspend fun getTrendMovies(@Query("api_key") key: String = ExamenCGApplication.MOVIES_API_KEY): ResponseMovie

    companion object {

        const val URL_IMAGES = "https://image.tmdb.org/t/p/w500/"


        fun getRetrofit(): APIService {
            val interceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).connectTimeout(15, TimeUnit.SECONDS).readTimeout(40,TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build()


            return Retrofit.Builder()
                .baseUrl(ExamenCGApplication.URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}