package com.object173.cfteventregestrator.feature.event.domain

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

interface EventRepository {
    fun updateEvents() : Completable
    fun getEvents() : Flowable<List<Event>>
    fun getEvent(eventId : Long) : Flowable<Event>
}