package com.spacex.repository

import com.spacex.repository.entities.rockets.RocketEntity
import com.spacex.repository.interactors.base.Result

/**
 * Contract for sources to seperate low level business logic from build and return type
 */
abstract class Repository {

    //region Abstractions
    internal abstract suspend fun getRockets(): Result<List<RocketEntity>>
   //endregion
}
