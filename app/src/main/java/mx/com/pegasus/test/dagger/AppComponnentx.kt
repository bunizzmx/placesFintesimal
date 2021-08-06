package mx.com.pegasus.test.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import mx.com.pegasus.test.PlacesApplication
import mx.com.pegasus.test.dagger.modules.ActivitiesModule
import mx.com.pegasus.test.dagger.modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivitiesModule::class])
interface AppComponnentx : AndroidInjector<PlacesApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: PlacesApplication): Builder

        fun build(): AppComponnentx
    }

    override fun inject(app: PlacesApplication)
}