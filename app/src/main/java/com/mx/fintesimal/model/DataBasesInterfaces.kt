package com.mx.fintesimal.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mx.fintesimal.model.dao.PlacesDao
import com.mx.fintesimal.model.database.DbPlaces

const val versionDB = 1

@Database(entities = [DbPlaces::class], version = versionDB, exportSchema = false)
abstract class CustomerDataBase : RoomDatabase() {
    abstract fun customerSQL(): PlacesDao

    companion object {
        private var customerInstance: CustomerDataBase? = null
        fun getInstance(context: Context): CustomerDataBase? {
            if (customerInstance == null) {
                synchronized(CustomerDataBase::class) {
                    customerInstance = Room.databaseBuilder(context.applicationContext,
                            CustomerDataBase::class.java, "DbCustomer.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return customerInstance
        }

        fun destroyInstance() {
            customerInstance = null
        }
    }
}
