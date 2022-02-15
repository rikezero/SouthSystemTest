package com.example.southsystemtest.ui.eventdetail

sealed class EventDetailState {
    data class Loading(val loading: Boolean) : EventDetailState()
    object CheckedIn: EventDetailState()
    object Share : EventDetailState()
    object Error : EventDetailState()
}
