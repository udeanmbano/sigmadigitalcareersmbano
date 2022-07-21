package com.mbano.core.remote

import com.mbano.core.BuildConfig
import com.mbano.core.remote.api.eventsApi.EventsApi
import com.mbano.core.remote.api.scheduleApi.ScheduleApi
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

    val eventsApi: EventsApi by lazy { buildEventApi() }
    val scheduleApi: ScheduleApi by lazy { buildScheduleApi() }

    private fun clientBuilder(): OkHttpClient.Builder {
        val clientBuilder = OkHttpClient()
            .newBuilder()

        // Turn off host name verification for debug builds,
        // so endpoints can be called using ip address like 10.0.2.2 on the emulator
        if (BuildConfig.DEBUG) {
            clientBuilder.hostnameVerifier { _, _ -> true }
        }

        return clientBuilder
    }

    private fun networkBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BuildConfig.ApiBaseURL.toString()) //TODO: We should be configuring this base URL from elsewhere now, it shouldn't live in AuthApi anymore
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(GsonConverterFactory.create())

    private fun buildEventApi(): EventsApi {
        val eventClientBuilder = clientBuilder()

        // Add auth api interceptor that will apply operations required for all auth api calls

        applyNetworkLoggingConfig(eventClientBuilder)

        return networkBuilder()
            .client(eventClientBuilder.build())
            .build()
            .create(EventsApi::class.java)
    }

    private fun buildScheduleApi(): ScheduleApi {
        val scheduleClientBuilder = clientBuilder()

        // Add auth api interceptor that will apply operations required for all auth api calls

        applyNetworkLoggingConfig(scheduleClientBuilder)

        return networkBuilder()
            .client(scheduleClientBuilder.build())
            .build()
            .create(ScheduleApi::class.java)
    }
    /**
     * Adds a relevant Logging based on build network configurations, it's important that this is
     * the last method called when creating a Network Client otherwise logging will miss any
     * modifications made by interceptors added after this call.
     *
     * @see LoggingInterceptor
     */
    private fun applyNetworkLoggingConfig(clientBuilder: OkHttpClient.Builder) {
        // Log network traffic if enabled on this build type
        if (BuildConfig.LogNetwork)
            clientBuilder.addInterceptor(LoggingInterceptor())
    }
}