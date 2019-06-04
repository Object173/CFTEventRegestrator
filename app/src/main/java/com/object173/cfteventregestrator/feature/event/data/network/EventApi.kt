package com.object173.cfteventregestrator.feature.event.data.network

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Single
import retrofit2.http.GET

interface EventApi {
    @GET("/api/v1/Events/registration")
    fun eventList(): Single<List<Event>>
}