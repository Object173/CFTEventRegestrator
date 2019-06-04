package com.object173.cfteventregestrator

import com.object173.cfteventregestrator.di.*
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import kotlin.text.Typography.dagger

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var instance : App
        get
        private set
    }
}