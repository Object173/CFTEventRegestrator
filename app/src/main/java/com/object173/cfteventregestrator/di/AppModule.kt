package com.object173.cfteventregestrator.di

import android.app.Application
import android.content.Context
import com.object173.cfteventregestrator.App
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesContext(app: Application): Context = app.applicationContext
}