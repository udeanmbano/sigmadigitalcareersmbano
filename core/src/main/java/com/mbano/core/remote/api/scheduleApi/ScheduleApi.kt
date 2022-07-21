package com.mbano.core.remote.api.scheduleApi

import com.mbano.core.BuildConfig
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.slack.eithernet.ApiResult
import retrofit2.http.GET

interface ScheduleApi {
    companion object Endpoints {
        val BASE_URL = "${BuildConfig.ApiBaseURL}/"
        private const val GET_SCHEDULE_ROUTE = "getSchedule"
    }

    @GET(GET_SCHEDULE_ROUTE)
    suspend fun getSchedule(): ApiResult<List<ScheduleResult>, Unit>
}