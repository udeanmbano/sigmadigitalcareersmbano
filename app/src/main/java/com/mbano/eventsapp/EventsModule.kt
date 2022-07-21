package com.mbano.eventsapp

import com.mbano.core.remote.WebServicesImpl
import com.mbano.eventsapp.ui.events.EventsViewModel
import com.mbano.eventsapp.ui.schedule.ScheduleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val EventsModule = module {

    viewModel { EventsViewModel(WebServicesImpl) }
    viewModel { ScheduleViewModel(WebServicesImpl) }
}