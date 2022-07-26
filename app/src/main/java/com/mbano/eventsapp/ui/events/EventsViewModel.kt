package com.mbano.eventsapp.ui.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbano.core.remote.WebServices
import com.mbano.core.remote.api.eventsApi.response.EventResult
import com.mbano.eventsapp.ui.events.uI.EventUI
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel(private val webServices: WebServices) : ViewModel() {


    private val _eventDataResult = MutableStateFlow<List<EventUI>>(listOf())
    val eventDataResult = _eventDataResult.asStateFlow()

    fun getEventsData() =
        viewModelScope.launch {
            when (val result = webServices.getEventsData()) {
                is ApiResult.Success -> {
                    transformData(result.value)
                }
                is ApiResult.Failure -> {
                    // TODO: handle error
                    _eventDataResult.emit(listOf())

                }
            }
        }


    private suspend fun transformData(response: List<EventResult>) {
        val events = mutableListOf<EventUI>()
        response.forEach {
            events.add(
                EventUI(
                    id = it.id,
                    title = it.title,
                    subtitle = it.subtitle,
                    date = it.date,
                    imageUrl = it.imageUrl,
                    videoUrl = it.videoUrl

                )
            )
        }


        _eventDataResult.emit(events)
    }
}
