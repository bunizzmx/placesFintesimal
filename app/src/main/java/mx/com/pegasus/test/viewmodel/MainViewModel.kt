package mx.com.pegasus.test.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.*
import mx.com.pegasus.test.common.SingleLiveEvent

import mx.com.pegasus.test.model.dao.PlacesDao
import mx.com.pegasus.test.model.database.DbPlaces
import mx.com.pegasus.test.web.PlacesService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.lang.Exception

import javax.inject.Inject

class MainViewModel @Inject constructor(private val placesDb: PlacesDao) : ViewModel() {
    var job: Job? = null
    val errorMessage = MutableLiveData<Boolean>()
    val errorrequest = MutableLiveData<Boolean>()
    val dogImages = MutableLiveData<ArrayList<DbPlaces>>()
    val visitedAll = SingleLiveEvent<Boolean>()

    //OBTIENE LOS ITEMS DE WEB
    fun getPlacesFromWeb() {
        try {
            val retrofitService = PlacesService.getInstance()
            job = CoroutineScope(Dispatchers.IO).launch {
                val response = retrofitService.getAllMovies()
                if (response != null) {
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            dogImages.postValue(response.body() as ArrayList<DbPlaces>?)
                        } else {
                            errorrequest.postValue(true)
                        }
                    }
                } else {
                    errorMessage.postValue(true)
                }
            }
        }catch (EX:Exception){ errorrequest.postValue(true)}


    }
    //VALIDA SI YA TIENE LOS DATOS EN LA DB
     fun validateLocalData() {
        doAsync {
            var num_reg:Int =placesDb.getPlaces().size
            uiThread {
                errorMessage.value = num_reg > 0
            }
        }
    }

    //OBTIENE LOS ITEMS LOCALMENTE
    fun getLocalData() {
        doAsync {
            var placess : MutableList<DbPlaces> =placesDb.getPlaces()
            var count_visited :Int  = 0
            for (i in placess.indices) {
                if(placess.get(i).visited)
                     count_visited +=1
            }
            uiThread {
                dogImages.postValue(placess as ArrayList<DbPlaces>?)
                if(count_visited >= placess.size){
                    visitedAll.value = true
                }
            }
        }
    }

    //GUARDA UNA VEZ LA PRIMERA DESCARGA DE VISITAS
    fun saveLocalData(places: List<DbPlaces>) {
        doAsync {
            if(placesDb.getPlaces().size<=0) {
                for (i in places.indices) {
                    places[i].latitude = places.get(i).location!!.latitude
                    places[i].longitude = places.get(i).location!!.longitude
                }
                placesDb.insertAllPlaces(places)
            }
        }
    }


}