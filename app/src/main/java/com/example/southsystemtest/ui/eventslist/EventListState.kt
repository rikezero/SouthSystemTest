package com.example.southsystemtest.ui.eventslist

import com.example.southsystemtest.data.model.EventListResponseItem
import java.util.ArrayList

sealed class EventListState {
    object EventIdError : EventListState()
    data class LoadEventList(val eventList: ArrayList<EventListResponseItem>?) : EventListState()
    data class Loading(val loading: Boolean) : EventListState()
    data class Error(val msg: String) : EventListState()
    data class LoadEventDetailScreen(val eventDetail: EventListResponseItem?) : EventListState()
}
