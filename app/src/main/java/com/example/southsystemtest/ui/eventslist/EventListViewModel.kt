package com.example.southsystemtest.ui.eventslist

import com.example.southsystemtest.data.repository.EventsRepositoryImpl
import com.example.southsystemtest.ui.base.BaseViewModel
import com.example.southsystemtest.utils.onCollect

class EventListViewModel : BaseViewModel<EventListIntent, EventListState>() {

    private val repository = EventsRepositoryImpl()

    override fun handle(intent: EventListIntent) {
        when (intent) {
            is EventListIntent.FetchEventList -> fetchEventList()
            is EventListIntent.FetchEventDetails -> fetchEventDetails(intent.id)
        }
    }

    private fun fetchEventDetails(id: String?) {
        if (id != null){
            repository.getEventDetails(id).onCollect(
                onSuccess = {
                    state.value = EventListState.LoadEventDetailScreen(it)
                },
                onLoading = {
                    state.value = EventListState.Loading(it)
                },
                onError = {
                    state.value = EventListState.Error(it.message.orEmpty())
                }
            )
        } else{
            state.value = EventListState.EventIdError
        }
    }

    private fun fetchEventList() {
        repository.getEventsList().onCollect(
            onSuccess = {
                state.value = EventListState.LoadEventList(it)
            },
            onLoading = {
                state.value = EventListState.Loading(it)
            },
            onError = {
                state.value = EventListState.Error(it.message.orEmpty())
            }
        )
    }

}