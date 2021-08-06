package mx.com.pegasus.test.dagger.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mx.com.pegasus.test.dagger.viewmodel.ViewModelKey
import mx.com.pegasus.test.viewmodel.*

@Module
internal abstract class ViewModelsModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindLoginViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindMapViewModel(viewModel: MapViewModel): ViewModel





}