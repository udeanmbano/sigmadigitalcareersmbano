package com.mbano.core.testing.mock.data

import com.mbano.core.remote.WebServices
import com.mbano.core.remote.api.eventsApi.EventsApi
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.remote.api.scheduleApi.ScheduleApi
import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class MockWebServices : WebServices {


    override val webScope: CoroutineScope
        get() = CoroutineScope(Dispatchers.IO)

    private val _activeNetworkRequests = MutableSharedFlow<List<WebServices.ActiveNetworkRequest>>(1)
    override val activeNetworkRequests: SharedFlow<List<WebServices.ActiveNetworkRequest>>
        get() = _activeNetworkRequests.asSharedFlow()

    override suspend fun completeNetworkRequest(networkRequest: WebServices.ActiveNetworkRequest) {

    }

    override suspend fun enqueueNetworkRequest(networkRequest: WebServices.ActiveNetworkRequest) {

    }

       override suspend fun getEventsData(
    ): ApiResult<List<EventResult>, Unit> {
        val response =EventResult(
            id="1",
        title= "Liverpool v Porto",
        subtitle= "UEFA Champions League",
        date= "2022-07-26T01:46:05.524Z",
        imageUrl= "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20",
        videoUrl= "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/promo.mp4?alt=media"

        )
        val eventResponse:ArrayList<EventResult> = ArrayList<EventResult>()
        eventResponse.add(response)
        return ApiResult.success(eventResponse)
    }

    override suspend fun getScheduleData(
    ): ApiResult<List<ScheduleResult>, Unit> {
        val response =ScheduleResult(
            id="1",
            title= "Liverpool v Porto",
            subtitle= "UEFA Champions League",
            date= "2022-07-26T01:46:05.524Z",
            imageUrl= "https://firebasestorage.googleapis.com/v0/b/dazn-recruitment/o/310176837169_image-header_pDach_1554579780000.jpeg?alt=media&token=1777d26b-d051-4b5f-87a8-7633d3d6dd20",
           )
        val scheduleResponse:ArrayList<ScheduleResult> = ArrayList<ScheduleResult>()
        scheduleResponse.add(response)
        return ApiResult.success(scheduleResponse)
    }

}