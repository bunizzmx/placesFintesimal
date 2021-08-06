package mx.com.pegasus.test.viewmodel

import androidx.lifecycle.ViewModel

import mx.com.pegasus.test.model.dao.PlacesDao
import mx.com.pegasus.test.model.database.DbPlaces
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class MapViewModel @Inject constructor(private val pricesDB: PlacesDao) : ViewModel() {
    // ESTE METODO SOLO SE ENCARGA DE HACER EL UPDATE DE LA VISITA
    fun updatePlace(dbPlaces: DbPlaces) {
        doAsync {
            pricesDB.updatePlaces(dbPlaces)
            uiThread { }
        }
    }
    override fun onCleared() {
        super.onCleared()
    }

}