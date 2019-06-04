package com.object173.cfteventregestrator.feature.event.data.local

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Flowable
import io.reactivex.Observable

interface LocalEventDataSource {
    fun updateEvents(events : List<Event>)
    fun getEvents() : Flowable<List<Event>>
    fun getEvent(eventId : Long) : Flowable<Event>
}