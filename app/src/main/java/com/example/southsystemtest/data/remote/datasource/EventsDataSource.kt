package com.example.southsystemtest.data.remote.datasource

import com.example.southsystemtest.data.model.CheckInRequest
import com.example.southsystemtest.data.model.EventListResponseItem
import com.example.southsystemtest.retrofit.RetroInit
import kotlinx.coroutines.flow.flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

class EventsDataSource {
    private var url = "http://5f5a8f24d44d640016169133.mockapi.io/api/"
    private var service = EventsServices::class
    private val serviceEvent = RetroInit(url).create(service)

    fun getEventsList() = flow {
        emit(serviceEvent.getEventsList())
    }

    fun getEventDetails(eventId: String) = flow {
        emit(serviceEvent.getEventDetails(eventId))
    }

    fun checkIn(request: CheckInRequest) = flow {
        emit(serviceEvent.checkIn(request))
    }
}

interface EventsServices {
    @GET("events")
    suspend fun getEventsList(): ArrayList<EventListResponseItem>?

    @GET("events/{id}")
    suspend fun getEventDetails(
        @Path("id")
        eventId: String
    ): EventListResponseItem?

    @POST("checkin")
    suspend fun checkIn(@Body request:CheckInRequest)
}