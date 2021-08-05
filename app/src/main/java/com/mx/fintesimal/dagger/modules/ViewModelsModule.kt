package com.mx.fintesimal.dagger.modules

import androidx.lifecycle.ViewModel
import com.mx.fintesimal.dagger.viewmodel.ViewModelKey
import com.mx.fintesimal.viewmodel.LoginViewModel
import com.mx.fintesimal.viewmodel.MapViewModel
import com.mx.fintesimal.web.PlacesService
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
internal abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MapViewModel::class)
    abstract fun bindLoginMapViewModel(viewModel: MapViewModel): ViewModel
}