package mx.com.pegasus.test.model.dao

import androidx.room.*
import mx.com.pegasus.test.model.database.DbPlaces


@Dao
interface PlacesDao {
    @Query("SELECT * FROM DbPlaces ORDER BY id ASC")
    fun getPlaces(): MutableList<DbPlaces>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(dbPlaces: DbPlaces): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPlaces(precios: List<DbPlaces>)

    @Update
    fun updatePlaces(dbPlaces: DbPlaces)

    @Query("DELETE FROM DbPlaces")
    fun deleteAll()
}