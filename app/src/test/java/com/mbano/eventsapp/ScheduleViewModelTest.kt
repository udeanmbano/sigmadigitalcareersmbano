package com.mbano.eventsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbano.core.testing.mock.data.mockresponse.MockScheduleApiResponses
import com.mbano.eventsapp.ui.schedule.ScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

class ScheduleViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var scheduleViewModel: ScheduleViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `should emit Success result when schedule are returned`(): Unit = runBlocking {
        runBlocking {
            val webServices = MockScheduleApiResponses.success()

            scheduleViewModel = ScheduleViewModel(webServices)

            scheduleViewModel.getScheduleData()

            val result = scheduleViewModel.scheduleDataResult.value

            assertNotNull(result.isNotEmpty())
        }
    }

    @Test
    fun `should emit fail and empty result when schedule fails to run to completion`(): Unit = runBlocking {
         val webServices = MockScheduleApiResponses.apiFailure()

        scheduleViewModel = ScheduleViewModel(webServices)

        scheduleViewModel.getScheduleData()

        val result = scheduleViewModel.scheduleDataResult.value

       assert(result.isEmpty())
    }
}