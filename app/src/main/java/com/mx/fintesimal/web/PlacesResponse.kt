package com.mx.fintesimal.web

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.mx.fintesimal.model.database.DbPlaces
import com.mx.fintesimal.model.database.localizacion

data class PlacesResponse(
    @SerializedName("lista")
    var lista: ArrayList<DbPlaces>?,
    ) {

}