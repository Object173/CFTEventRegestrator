package com.object173.cfteventregestrator.feature.event.data.network

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

class NetworkEventDataSourceImpl @Inject constructor(retrofit : Retrofit) : NetworkEventDataSource {

    private val api : EventApi = retrofit.create(EventApi::class.java)

    override fun fetchEvents(): Single<List<Event>> {
        return api.eventList()
    }
}