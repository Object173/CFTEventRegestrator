package com.object173.cfteventregestrator.di

import com.object173.cfteventregestrator.App
import com.object173.cfteventregestrator.feature.event.di.EvenFragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton
import android.app.Application


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    AppDatabaseModule::class,
    RetrofitModule::class,
    EvenFragmentModule::class,
    FragmentModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}