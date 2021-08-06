package mx.com.pegasus.test.model.database

import androidx.room.ColumnInfo

data class localizacion(
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @ColumnInfo(name = "longitude")
    var longitude: Double

    ) {
    constructor() : this(0.0,0.0)
}