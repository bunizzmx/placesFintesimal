package mx.com.pegasus.test

import android.content.Context
import androidx.multidex.MultiDex
import dagger.android.support.DaggerApplication


import mx.com.pegasus.test.dagger.DaggerAppComponnentx
import mx.com.pegasus.test.dagger.applyAutoInjector

class PlacesApplication : DaggerApplication() {

    var inputStream : ArrayList<String>?
        get() = inputStream
        set(value) {
            inputStream = value
        }



        private var placesApplication: PlacesApplication? = null
        private var context: Context? = null


    override fun applicationInjector() = DaggerAppComponnentx.builder().application(this).build()

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()

        context = applicationContext
        placesApplication = this


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}
