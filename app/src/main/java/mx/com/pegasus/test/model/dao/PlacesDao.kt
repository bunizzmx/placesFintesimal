package mx.com.pegasus.test.model.dao

import androidx.room.*
import mx.com.pegasus.test.model.database.DbPlaces


@Dao
interface PlacesDao {
    @Query("SELECT * FROM DbPlaces ORDER BY id ASC")
    fun getPrices(): MutableList<DbPlaces>

    @Query("SELECT * FROM DbPlaces WHERE id == :LastInserted")
    fun getPricesLastInserted(LastInserted: Long): DbPlaces

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrices(dbPlaces: DbPlaces): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListFB(precios: List<DbPlaces>)

    @Update
    fun updatePrices(dbPlaces: DbPlaces)

    @Query("DELETE FROM DbPlaces")
    fun deleteAll()
}