package com.example.southsystemtest.ui.eventslist

sealed class EventListIntent {
    data class FetchEventDetails(val id: String?) : EventListIntent()
    object FetchEventList : EventListIntent()
}
