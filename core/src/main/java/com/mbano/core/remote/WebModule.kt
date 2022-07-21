package com.mbano.core.remote

import org.koin.dsl.module

val webModule = module {
    single { NetworkClient.eventsApi }
    single { NetworkClient.scheduleApi}
    single { WebServicesImpl }
    single<WebServices> { WebServicesImpl }
}