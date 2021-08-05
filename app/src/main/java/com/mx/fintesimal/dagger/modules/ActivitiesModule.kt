package com.mx.fintesimal.dagger.modules

import androidx.lifecycle.ViewModelProvider
import com.mx.fintesimal.dagger.viewmodel.ViewModelFactory
import com.mx.fintesimal.view.MainActivity
import com.mx.fintesimal.view.fragments.MapFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivitiesModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = arrayOf(ViewModelsModule::class))
    internal abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(ViewModelsModule::class))
    internal abstract fun contributesMapFragment(): MapFragment




}