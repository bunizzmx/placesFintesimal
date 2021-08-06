package mx.com.pegasus.test.dagger.modules

import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import mx.com.pegasus.test.dagger.viewmodel.ViewModelFactory
import mx.com.pegasus.test.view.MainActivity
import mx.com.pegasus.test.view.fragments.MapFragment


@Module
internal abstract class ActivitiesModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector(modules = arrayOf(ViewModelsModule::class))
    internal abstract fun contributesMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = arrayOf(ViewModelsModule::class))
    internal abstract fun contributesMapFragment(): MapFragment


}