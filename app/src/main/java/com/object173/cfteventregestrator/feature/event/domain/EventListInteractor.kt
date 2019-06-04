package com.object173.cfteventregestrator.feature.event.domain

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable

interface EventListInteractor {
    fun updateEvents() : Completable
    fun getEvents() : Flowable<List<Event>>
}