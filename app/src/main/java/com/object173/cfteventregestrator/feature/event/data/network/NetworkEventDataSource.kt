package com.object173.cfteventregestrator.feature.event.data.network

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Single

interface NetworkEventDataSource {
    fun fetchEvents() : Single<List<Event>>
}