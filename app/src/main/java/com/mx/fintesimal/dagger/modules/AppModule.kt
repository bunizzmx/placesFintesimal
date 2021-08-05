package com.mx.fintesimal.dagger.modules

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.preference.PreferenceManager
import com.mx.fintesimal.PlacesApplication
import com.mx.fintesimal.model.CustomerDataBase
import com.mx.fintesimal.model.dao.PlacesDao
import com.mx.fintesimal.utils.AndroidIdentifier



import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Singleton
    @Provides
    fun providesApp(app: PlacesApplication): Context = app.applicationContext

    @Singleton
    @Provides
    fun providesPackageManager(context: Context): PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

    @Singleton
    @Provides
    fun providesAndroidID(context: Context): AndroidIdentifier = AndroidIdentifier(context)

    @Singleton
    @Provides
    fun providesSharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Singleton
    @Provides
    fun providesPlacesDb(context: Context): PlacesDao = CustomerDataBase.getInstance(context)!!.customerSQL()
}