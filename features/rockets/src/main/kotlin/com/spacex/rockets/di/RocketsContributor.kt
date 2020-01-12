package com.spacex.rockets.di

import com.spacex.rockets.di.modules.RocketsFragmentModule
import com.spacex.rockets.di.scopes.RocketsFragmentScope
import com.spacex.rockets.ui.RocketsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Contributes fragments & view models in this module
 */
@Module
abstract class RocketsContributor {

    //region Contributes

    @ContributesAndroidInjector(
        modules = [RocketsFragmentModule::class]
    )
    @RocketsFragmentScope
    abstract fun rocketsFragment(): RocketsFragment
    //endregion
}
