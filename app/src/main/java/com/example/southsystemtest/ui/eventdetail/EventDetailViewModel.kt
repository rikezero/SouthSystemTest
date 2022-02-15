package com.example.southsystemtest.ui.eventdetail

import com.example.southsystemtest.data.model.CheckInRequest
import com.example.southsystemtest.data.repository.EventsRepositoryImpl
import com.example.southsystemtest.ui.base.BaseViewModel
import com.example.southsystemtest.utils.onCollect

class EventDetailViewModel : BaseViewModel<EventDetailIntent, EventDetailState>() {

    private val repository = EventsRepositoryImpl()

    override fun handle(intent: EventDetailIntent) {
        when (intent) {
            is EventDetailIntent.Share -> share()
            is EventDetailIntent.CheckIn -> checkIn(intent.request)
        }
    }

    private fun checkIn(request: CheckInRequest) {
        repository.checkIn(request).onCollect(
            onLoading = {
                state.value = EventDetailState.Loading(it)
            },
            onSuccess = {
                state.value = EventDetailState.CheckedIn
            },
            onError = {
                state.value = EventDetailState.Error
            }
        )
    }

    private fun share() {
        state.value = EventDetailState.Share
    }

}