package com.mbano.eventsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mbano.core.testing.mock.data.mockresponse.MockEventApiResponses
import com.mbano.eventsapp.ui.events.EventsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertNotNull

class EventViewModelTest {
    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private lateinit var eventViewModel: EventsViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `should emit Success result when events are returned`(): Unit = runBlocking {
        runBlocking {
            // GIVEN WebServices returns success response for registerUser API call
            val webServices = MockEventApiResponses.success()

            eventViewModel = EventsViewModel(webServices)

            eventViewModel.getEventsData()

            val result = eventViewModel.eventDataResult.value

            assertNotNull(result.isNotEmpty())
        }
    }

    @Test
    fun `should emit fail and empty result when events fails to run to completion`(): Unit = runBlocking {
        val webServices = MockEventApiResponses.apiFailure()

        eventViewModel = EventsViewModel(webServices)

        eventViewModel.getEventsData()

        val result = eventViewModel.eventDataResult.value

       assert(result.isEmpty())
    }
}