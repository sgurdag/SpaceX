package com.spacex.rocketsapp.di

import com.spacex.rockets.di.RocketsContributor
import com.spacex.rocketsapp.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {


    @ContributesAndroidInjector(
        modules = [RocketsContributor::class]
    )
    abstract fun mainActivity(): MainActivity
}
