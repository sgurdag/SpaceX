package com.spacex.repository.network

import com.spacex.repository.entities.rockets.RocketEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal const val TIMEOUT_DURATION = 7L

internal class ApiImpl @Inject constructor() : Api {

    //region Properties

    private val service by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_DURATION, TimeUnit.SECONDS)
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    ).build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.spacexdata.com/v3/")
            .build()
            .create(Api::class.java)
    }

    //endregion


    override suspend fun getRockets(): Response<List<RocketEntity>> = service.getRockets()

}
