package com.example.southsystemtest.ui.eventdetail

import com.example.southsystemtest.data.model.CheckInRequest

sealed class EventDetailIntent {
    data class CheckIn(val request: CheckInRequest) : EventDetailIntent()
    object Share : EventDetailIntent()
}
