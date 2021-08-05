package com.mx.fintesimal.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DbPlaces(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @SerializedName("streetName")
        @ColumnInfo(name = "streetName")
        var domicilio: String,
        @SerializedName("suburb")
        @ColumnInfo(name = "suburb")
        var suburb: String,
        @ColumnInfo(name = "latitude")
        var latitude: Double,
        @ColumnInfo(name = "longitude")
        var longitude: Double,
        @SerializedName("visited")
        @ColumnInfo(name = "visited")
        var visited: Boolean,
        @SerializedName("location")
        @Ignore
        var location: localizacion?,

) {
    constructor() : this(null,"","",0.0,0.0,false,null)

    override fun toString(): String {
        return domicilio
    }

}