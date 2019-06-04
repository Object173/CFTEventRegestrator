package com.object173.cfteventregestrator.feature.event.data

import com.object173.cfteventregestrator.feature.event.domain.model.Event
import com.object173.cfteventregestrator.feature.event.data.local.LocalEventDataSource
import com.object173.cfteventregestrator.feature.event.data.network.NetworkEventDataSource
import com.object173.cfteventregestrator.feature.event.domain.EventRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

class EventRepositoryImpl @Inject constructor(
    var mLocalDataSource: LocalEventDataSource,
    var mNetworkDataSource: NetworkEventDataSource
) : EventRepository {

    override fun getEvents(): Flowable<List<Event>> {
        return mLocalDataSource.getEvents().observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateEvents(): Completable {
        return mNetworkDataSource.fetchEvents()
                    .flatMapCompletable { Completable.fromAction { mLocalDataSource.updateEvents(it) }}
                    .subscribeOn(Schedulers.io())
    }

    override fun getEvent(eventId : Long): Flowable<Event> {
        return mLocalDataSource.getEvent(eventId)
    }
}