package com.mx.fintesimal.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mx.fintesimal.model.dao.PlacesDao
import com.mx.fintesimal.model.database.DbPlaces
import com.mx.fintesimal.web.PlacesService
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MapViewModel @Inject constructor(private val pricesDB: PlacesDao) : ViewModel() {
    val errorMessage = MutableLiveData<String>()

    public val dogImages = MutableLiveData<ArrayList<DbPlaces>>()

     fun updatePlace(dbPlaces: DbPlaces) {
        doAsync {
            pricesDB.updatePrices(dbPlaces)
            uiThread {

            }
        }

    }






    override fun onCleared() {
        super.onCleared()
    }

}