package com.spacex.rockets.di.modules

import androidx.lifecycle.ViewModel
import com.spacex.core.di.keys.ViewModelKey
import com.spacex.rockets.ui.vm.RocketsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RocketsFragmentModule {

    //region ViewModels

    @Binds
    @IntoMap
    @ViewModelKey(RocketsViewModel::class)
    abstract fun listViewModel(listViewModel: RocketsViewModel): ViewModel
    //endregion

}
