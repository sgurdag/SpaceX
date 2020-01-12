package com.spacex.repository.sources

import android.net.NetworkInfo
import com.spacex.repository.R
import com.spacex.repository.interactors.base.EmptyResultError
import com.spacex.repository.interactors.base.Failure
import com.spacex.repository.interactors.base.NetworkError
import com.spacex.repository.interactors.base.ResponseError
import com.spacex.repository.interactors.base.Success
import com.spacex.repository.interactors.base.onFailure
import com.spacex.repository.interactors.base.onSuccess
import com.spacex.repository.network.ApiImpl
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import javax.inject.Provider

@UseExperimental(ExperimentalCoroutinesApi::class)
class NetworkSourceTest {

    private val apiImpl = mockk<ApiImpl>(relaxed = true)
    private val networkInfoProvider = mockk<Provider<NetworkInfo>>(relaxed = true) {
        every { get() } returns mockk(relaxed = true)
    }

    private val source = spyk(NetworkSource(apiImpl, networkInfoProvider))

    @Nested
    inner class GetRockets {

        @Test
        fun `should return network error when internet is not connected`() {
            every { networkInfoProvider.get().isConnected } returns false

            runBlockingTest {
                val result = source.getRockets()

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf NetworkError::class
                }
            }
        }

        @Test
        fun `should return response error when it is not successful`() {
            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getRockets().isSuccessful } returns false

            runBlockingTest {
                val result = source.getRockets()

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf ResponseError::class
                    (it as ResponseError).messageRes shouldEqualTo R.string.reason_response
                }
            }
        }

        @Test
        fun `should return empty result error when body is null`() {
            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getRockets().isSuccessful } returns true

            runBlockingTest {
                val result = source.getRockets()

                result shouldBeInstanceOf Failure::class
                result.onFailure {
                    it shouldBeInstanceOf EmptyResultError::class
                }
            }
        }

        @Test
        fun `should return success with data if execution is successful`() {
            every { networkInfoProvider.get().isConnected } returns true
            coEvery { apiImpl.getRockets().isSuccessful } returns true

            runBlockingTest {
                val result = source.getRockets()

                result shouldBeInstanceOf Success::class
                result.onSuccess {
                    it shouldBeInstanceOf List::class
                    it.size shouldEqualTo  1
                    it[0].id shouldEqualTo 1013
                }
            }
        }
    }
}
