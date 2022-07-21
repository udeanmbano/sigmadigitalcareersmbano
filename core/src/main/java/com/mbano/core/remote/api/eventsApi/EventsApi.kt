package com.mbano.core.remote.api.eventsApi

import com.mbano.core.BuildConfig
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.slack.eithernet.ApiResult
import retrofit2.http.GET

interface EventsApi {
    companion object Endpoints {
        val BASE_URL = "${BuildConfig.ApiBaseURL}/"
        private const val GET_EVENTS_ROUTE = "getEvents"
    }

    @GET(GET_EVENTS_ROUTE)
    suspend fun getEvents(): ApiResult<List<EventResult>, Unit>
}