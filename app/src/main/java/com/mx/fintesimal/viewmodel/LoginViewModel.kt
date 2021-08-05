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
import retrofit2.converter.gson.GsonConverterFactory

import javax.inject.Inject

class LoginViewModel @Inject constructor( private val pricesDB: PlacesDao) : ViewModel() {
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()

    public val dogImages = MutableLiveData<ArrayList<DbPlaces>>()
    fun getPlacesFromWeb() {
        val retrofitService = PlacesService.getInstance()
        job = CoroutineScope(Dispatchers.IO).launch {
            if(pricesDB.getPrices().size<=0) {
                val response = retrofitService.getAllMovies()
                Log.e("ERROR","FOM WEB-->")
                Log.e("ERROR","-->" + response.body())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        dogImages.postValue(response.body() as ArrayList<DbPlaces>?)
                        if(pricesDB.getPrices().size<=0) {
                            insertPlaces((response.body() as ArrayList<DbPlaces>?)!!)
                        }
                        // loading.value = false
                    } else {
                        onError("Error : ${response.message()} ")
                    }
                }
            }else{
                Log.e("ERROR","FOM DB-->")
                dogImages.postValue(pricesDB.getPrices() as ArrayList<DbPlaces>?)
            }

        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
       // loading.value = false
    }

    private fun getPreferences(dbPlaces: DbPlaces) {
        doAsync {
            pricesDB.updatePrices(dbPlaces)
            uiThread {

            }
        }

    }

    fun insertPlaces(dbPlaces: ArrayList<DbPlaces>) {
        doAsync {
            pricesDB.insertListFB(dbPlaces)
            uiThread {

            }
        }

    }


    override fun onCleared() {
        super.onCleared()
    }

}