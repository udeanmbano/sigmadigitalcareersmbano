package com.mbano.core.remote

import com.mbano.core.remote.WebServices.ActiveNetworkRequest
import com.mbano.core.remote.api.eventsApi.EventsApi
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.remote.api.scheduleApi.ScheduleApi
import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

object WebServicesImpl : WebServices {
   private val eventsApi: EventsApi by inject(EventsApi::class.java)
    private val scheduleApi: ScheduleApi by inject(ScheduleApi::class.java)

    override val webScope = CoroutineScope(Dispatchers.IO)

    private val networkRequestState: MutableList<ActiveNetworkRequest> = mutableListOf()
    private val activeNetworkRequest = MutableSharedFlow<List<ActiveNetworkRequest>>(1)
    override val activeNetworkRequests = activeNetworkRequest.asSharedFlow()

  
    init {
        webScope.launch { activeNetworkRequest.emit(networkRequestState) }
    }

    override suspend fun enqueueNetworkRequest(networkRequest: ActiveNetworkRequest) {
        networkRequestState.add(networkRequest)
        activeNetworkRequest.emit(networkRequestState)
    }

    override suspend fun completeNetworkRequest(networkRequest: ActiveNetworkRequest) {
        networkRequestState.remove(networkRequest)
        activeNetworkRequest.emit(networkRequestState)
    }

  
    override suspend fun getEventsData(): ApiResult<List<EventResult>, Unit> =
        withContext(webScope.coroutineContext) {
            val getEventDataRequest =
                ActiveNetworkRequest.GetEventData()
            enqueueNetworkRequest(getEventDataRequest)

            val result = eventsApi.getEvents()
            completeNetworkRequest(getEventDataRequest)

            result
        }
    override suspend fun getScheduleData(): ApiResult<List<ScheduleResult>, Unit> =
        withContext(webScope.coroutineContext) {
            val getScheduleDataRequest =
                ActiveNetworkRequest.GetScheduleData()
            enqueueNetworkRequest(getScheduleDataRequest)

            val result = scheduleApi.getSchedule()
            completeNetworkRequest(getScheduleDataRequest)

            result
        }
}