package com.spacex.repository.interactors

import com.spacex.repository.entities.rockets.RocketEntity
import com.spacex.repository.interactors.base.BaseInteractor
import com.spacex.repository.interactors.base.None
import com.spacex.repository.interactors.base.Result
import com.spacex.repository.sources.NetworkSource
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class GetRockets @Inject constructor() : BaseInteractor<List<RocketEntity>, None>() {

    // Other sources will be injected to this class and data source will be managed through this level.

    @field:Inject
    internal lateinit var networkSource: NetworkSource

    override suspend fun FlowCollector<Result<List<RocketEntity>>>.run(params: None) {
        emit(networkSource.getRockets())
    }

}