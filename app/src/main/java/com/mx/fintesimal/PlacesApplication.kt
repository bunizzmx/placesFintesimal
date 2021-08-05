package com.mx.fintesimal

import android.content.Context
import androidx.multidex.MultiDex

import com.mx.fintesimal.dagger.applyAutoInjector
import dagger.android.support.DaggerApplication

class PlacesApplication : DaggerApplication() {


    override fun applicationInjector() = DaggerAppCompenent.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()

    }
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
