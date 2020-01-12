package com.spacex.rockets.ui.vm

import com.spacex.core.base.viewmodel.BaseViewModel
import com.spacex.repository.entities.rockets.RocketEntity
import com.spacex.repository.interactors.GetRockets
import com.spacex.repository.interactors.base.None
import com.spacex.repository.interactors.base.handle
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class RocketsViewModel @Inject constructor(
    private val getRockets: GetRockets
) : BaseViewModel<List<RocketEntity>>() {

    var isActiveOnly = false

    override suspend fun loadData() {

        getRockets(None()).collect{
            it.handle(::handleState, ::handleFailure, ::handleSuccess)
        }
    }

}
