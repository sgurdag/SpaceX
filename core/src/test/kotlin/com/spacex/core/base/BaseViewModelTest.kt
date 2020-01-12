package com.spacex.core.base

import com.spacex.core.BaseTestWithMainThread
import com.spacex.core.base.viewmodel.BaseViewModel
import io.mockk.coVerify
import io.mockk.spyk
import org.junit.jupiter.api.Test

class BaseViewModelTest : BaseTestWithMainThread() {

    @Test
    fun `refresh should invoke loadData`() {
        val baseVm = spyk(TestViewModel())
        baseVm.refresh()

        coVerify(exactly = 1) { baseVm.loadData() }
    }

    @Test
    fun `retry should invoke loadData`() {
        val baseVm = spyk(TestViewModel())
        baseVm.retry()

        coVerify(exactly = 1) { baseVm.loadData() }
    }
}

class TestViewModel : BaseViewModel<Unit>() {
    override suspend fun loadData() {
        // no - op
    }
}
