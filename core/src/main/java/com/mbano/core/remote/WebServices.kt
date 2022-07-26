package com.mbano.core.remote

import com.mbano.core.remote.api.eventsApi.EventsApi
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.remote.api.scheduleApi.ScheduleApi
import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface WebServices {

    val webScope: CoroutineScope

    val activeNetworkRequests: SharedFlow<List<ActiveNetworkRequest>>

    sealed class ActiveNetworkRequest() {
        class GetEventData() : ActiveNetworkRequest()
        class GetScheduleData() : ActiveNetworkRequest()

    }

    suspend fun enqueueNetworkRequest(networkRequest: ActiveNetworkRequest)

    suspend fun completeNetworkRequest(networkRequest: ActiveNetworkRequest)


    suspend fun getEventsData(): ApiResult<List<EventResult>, Unit>
    suspend fun getScheduleData(): ApiResult<List<ScheduleResult>, Unit>

}