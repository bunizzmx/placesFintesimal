package com.mx.fintesimal.web

import androidx.lifecycle.MutableLiveData
import com.mx.fintesimal.model.database.DbPlaces
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

interface PlacesService {
    @GET("interview")
    suspend fun getAllMovies() : Response<List<DbPlaces>>


    companion object {
        var retrofitService: PlacesService? = null
        fun getInstance() : PlacesService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fintecimal-test.herokuapp.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(PlacesService::class.java)
            }
            return retrofitService!!
        }

    }
}