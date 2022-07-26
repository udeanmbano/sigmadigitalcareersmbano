package com.mbano.core.testing.mock.data.mockresponse


import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.mbano.core.testing.mock.data.MockResponse
import com.mbano.core.testing.mock.data.MockWebServices
import com.slack.eithernet.ApiResult
import java.io.IOException

object MockScheduleApiResponses : MockResponse {

    override fun success() = object : MockWebServices() {}

    override fun networkFailure() = object : MockWebServices() {
        override suspend fun getScheduleData(
        ): ApiResult<List<ScheduleResult>, Unit> {
            return ApiResult.networkFailure(IOException("A MOCK Network Failure occurred"))
        }
    }

    //TODO: define mocks for specific api errors when we start handling them
    override fun apiFailure() = object : MockWebServices() {
        override suspend fun getScheduleData(
        ): ApiResult<List<ScheduleResult>, Unit> {
            return ApiResult.apiFailure()
        }
    }

    //TODO: define mocks for specific http codes when we start handling them
    override fun httpFailure() = object : MockWebServices() {
        override suspend fun getScheduleData(
        ): ApiResult<List<ScheduleResult>, Unit> {
            return ApiResult.httpFailure(404)
        }
    }

    override fun unknownFailure() = object : MockWebServices() {
        override suspend fun getScheduleData(
        ): ApiResult<List<ScheduleResult>, Unit> {
            return ApiResult.unknownFailure(Throwable("A MOCK Unknown Failure occurred"))
        }
    }


}