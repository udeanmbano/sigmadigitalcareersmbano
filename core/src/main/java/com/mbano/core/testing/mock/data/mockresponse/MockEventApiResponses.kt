package com.mbano.core.testing.mock.data.mockresponse


import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.testing.mock.data.MockResponse
import com.mbano.core.testing.mock.data.MockWebServices
import com.slack.eithernet.ApiResult
import java.io.IOException

object MockEventApiResponses : MockResponse {

    override fun success() = object : MockWebServices() {
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
    }

    override fun networkFailure() = object : MockWebServices() {
        override suspend fun getEventsData(
        ): ApiResult<List<EventResult>, Unit> {
            return ApiResult.networkFailure(IOException("A MOCK Network Failure occurred"))
        }
    }

    override fun apiFailure() = object : MockWebServices() {
        override suspend fun getEventsData(
        ): ApiResult<List<EventResult>, Unit> {
            return ApiResult.apiFailure()
        }
    }

    override fun httpFailure() = object : MockWebServices() {
        override suspend fun getEventsData(
        ): ApiResult<List<EventResult>, Unit> {
            return ApiResult.httpFailure(404)
        }
    }

    override fun unknownFailure() = object : MockWebServices() {
        override suspend fun getEventsData(
        ): ApiResult<List<EventResult>, Unit> {
            return ApiResult.unknownFailure(Throwable("A MOCK Unknown Failure occurred"))
        }
    }


}