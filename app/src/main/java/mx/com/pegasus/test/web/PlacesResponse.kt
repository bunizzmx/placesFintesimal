package mx.com.pegasus.test.web

import com.google.gson.annotations.SerializedName
import mx.com.pegasus.test.model.database.DbPlaces


data class PlacesResponse(
    @SerializedName("lista")
    var lista: ArrayList<DbPlaces>?
    ) {

}