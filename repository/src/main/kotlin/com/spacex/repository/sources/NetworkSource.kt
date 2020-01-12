package com.spacex.repository.sources

import android.net.NetworkInfo
import com.spacex.repository.Repository
import com.spacex.repository.entities.rockets.RocketEntity
import com.spacex.repository.interactors.base.EmptyResultError
import com.spacex.repository.interactors.base.Failure
import com.spacex.repository.interactors.base.NetworkError
import com.spacex.repository.interactors.base.Reason
import com.spacex.repository.interactors.base.ResponseError
import com.spacex.repository.interactors.base.Result
import com.spacex.repository.interactors.base.Success
import com.spacex.repository.interactors.base.TimeoutError
import com.spacex.repository.network.ApiImpl
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Provider


/**
 * NetworkSource for fetching results using api and wrapping them as contracted in [repository][Repository],
 * returning either [failure][Failure] with proper [reason][Reason] or [success][Success] with data
 */
internal class NetworkSource @Inject constructor(
    private val apiImpl: ApiImpl,
    private val networkInfoProvider: Provider<NetworkInfo>
) : Repository() {

    //region Properties

    private val isNetworkConnected: Boolean
        get() {
            val networkInfo = networkInfoProvider.get()
            return networkInfo != null && networkInfo.isConnected
        }
    //endregion

    //region Functions

    override suspend fun getRockets(): Result<List<RocketEntity>> =
        safeExecute({ apiImpl.getRockets() }) { response -> response }


    private inline fun <T, R> safeExecute(
        block: () -> Response<T>,
        transform: (T) -> R
    ) =
        if (isNetworkConnected) {
            try {
                block().extractResponseBody(transform)
            } catch (e: IOException) {
                Failure(TimeoutError())
            }
        } else {
            Failure(NetworkError())
        }

    private inline fun <T, R> Response<T>.extractResponseBody(transform: (T) -> R) =
        if (isSuccessful) {
            body()?.let {
                Success(transform(it))
            } ?: Failure(EmptyResultError())
        } else {
            Failure(ResponseError())
        }

    //endregion
}
