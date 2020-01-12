package com.spacex.rocketsapp

import com.spacex.core.di.DaggerCoreComponent
import com.spacex.rocketsapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory()
            .create(
                DaggerCoreComponent.factory()
                    .create(this)
            )


    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}
