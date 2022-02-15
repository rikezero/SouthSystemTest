package com.example.southsystemtest.data.repository

import com.example.southsystemtest.data.model.CheckInRequest
import com.example.southsystemtest.data.model.EventListResponseItem
import com.example.southsystemtest.data.remote.datasource.EventsDataSource
import kotlinx.coroutines.flow.Flow

interface EventsRepository{
    fun getEventsList(): Flow<ArrayList<EventListResponseItem>?>
    fun getEventDetails(eventId: String): Flow<EventListResponseItem?>
    fun checkIn(request: CheckInRequest): Flow<Unit>
}

class EventsRepositoryImpl: EventsRepository {

    private val dataSource = EventsDataSource()

    override fun getEventsList() = dataSource.getEventsList()

    override fun getEventDetails(eventId: String) = dataSource.getEventDetails(eventId)

    override fun checkIn(request: CheckInRequest) = dataSource.checkIn(request)
}