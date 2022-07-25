package com.mbano.eventsapp.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbano.core.remote.WebServices
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.core.remote.api.scheduleApi.response.ScheduleResult
import com.mbano.eventsapp.ui.events.uI.EventUI
import com.mbano.eventsapp.ui.schedule.ui.ScheduleUI
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ScheduleViewModel(private val webServices: WebServices) : ViewModel() {

    private val _scheduleDataResult = MutableStateFlow<List<ScheduleUI>>(listOf())
    val scheduleDataResult = _scheduleDataResult.asStateFlow()

    fun getScheduleData() =
        viewModelScope.launch {
            while (isActive) {
                when (val result = webServices.getScheduleData()) {
                    is ApiResult.Success -> {
                        transformData(result.value)
                    }
                    is ApiResult.Failure -> {
                        _scheduleDataResult.emit(listOf())
                    }
                }
                //update every 30 seconds
                delay(TIMER_INTERVAL)
            }
        }

    private suspend fun transformData(response: List<ScheduleResult>) {
        val schedules = mutableListOf<ScheduleUI>()
        response.forEach {
            schedules.add(
                ScheduleUI(
                    id = it.id,
                    title = it.title,
                    subtitle = it.subtitle,
                    date = it.date,
                    imageUrl = it.imageUrl
                )
            )
        }
        _scheduleDataResult.emit(schedules)
    }

    companion object {
        const val TIMER_INTERVAL=30000L
    }
}