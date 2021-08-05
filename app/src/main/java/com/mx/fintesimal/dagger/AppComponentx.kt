package com.mx.fintesimal.dagger

import com.mx.fintesimal.PlacesApplication
import com.mx.fintesimal.dagger.modules.ActivitiesModule
import com.mx.fintesimal.dagger.modules.AppModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivitiesModule::class])
interface AppComponentx : AndroidInjector<PlacesApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PlacesApplication): Builder

        fun build(): AppComponentx
    }

    override fun inject(app: PlacesApplication)
}