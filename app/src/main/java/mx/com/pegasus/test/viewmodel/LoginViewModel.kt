package mx.com.pegasus.test.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.*
import mx.com.pegasus.test.common.SingleLiveEvent

import mx.com.pegasus.test.model.dao.PlacesDao
import mx.com.pegasus.test.model.database.DbPlaces
import mx.com.pegasus.test.web.PlacesService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

import javax.inject.Inject

class LoginViewModel @Inject constructor(private val pricesDB: PlacesDao) : ViewModel() {
    var job: Job? = null
    val errorMessage = MutableLiveData<Boolean>()

    val dogImages = MutableLiveData<ArrayList<DbPlaces>>()
    val visitedAll = SingleLiveEvent<Boolean>()
    fun getPlacesFromWeb() {
        val retrofitService = PlacesService.getInstance()
        job = CoroutineScope(Dispatchers.IO).launch {
                val response = retrofitService.getAllMovies()
                Log.e("RESPONSE", "-->" + response.body())
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        dogImages.postValue(response.body() as ArrayList<DbPlaces>?)
                    } else {

                    }
                }
            }


    }

     fun validateLocalData() {
        doAsync {
            var num_reg:Int =pricesDB.getPrices().size
            uiThread {
                errorMessage.value = num_reg > 0
            }
        }
    }

    fun getLocalData() {
        doAsync {
            var placess : MutableList<DbPlaces> =pricesDB.getPrices()
            var count_visited :Int  = 0
            for (i in placess.indices) {
                if(placess.get(i).visited)
                     count_visited +=1
            }
            uiThread {
                Log.e("LOCAL_DATA", "-->" + placess.size)
                dogImages.postValue(placess as ArrayList<DbPlaces>?)
                if(count_visited >= placess.size){
                    visitedAll.value = true
                }
            }
        }
    }

    fun saveLocalData(places: List<DbPlaces>) {
        doAsync {
            if(pricesDB.getPrices().size<=0) {
                for (i in places.indices) {

                    places[i].latitude = places.get(i).location!!.latitude
                    places[i].longitude = places.get(i).location!!.longitude
                    Log.e("LATITUDE","-->" + places[i].latitude)
                    Log.e("LONGITUDE","-->" + places[i].longitude)
                }
                pricesDB.insertListFB(places)
            }
        }
    }


}