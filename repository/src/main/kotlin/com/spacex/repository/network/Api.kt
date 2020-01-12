package com.spacex.repository.network

import com.spacex.repository.entities.rockets.RocketEntity
import retrofit2.Response
import retrofit2.http.GET

/**
 * Retrofit interface for networking
 */
internal interface Api {

    //region Get

    @GET("rockets")
    suspend fun getRockets(): Response<List<RocketEntity>>

    //endregion
}
