package com.object173.cfteventregestrator.feature.event.domain

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

class EventListInteractorImpl @Inject constructor(private val mEventRepository: EventRepository) :
    EventListInteractor {

    override fun updateEvents(): Completable {
        return mEventRepository.updateEvents()
    }

    override fun getEvents() : Flowable<List<Event>> {
        return mEventRepository.getEvents()
    }
}