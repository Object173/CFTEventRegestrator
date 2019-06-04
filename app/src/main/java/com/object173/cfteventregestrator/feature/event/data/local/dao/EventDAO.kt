package com.object173.cfteventregestrator.feature.event.data.local.dao

import androidx.room.*
import com.object173.cfteventregestrator.feature.event.data.local.entity.CityDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventCityJoinDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventWithCitiesDB
import io.reactivex.Flowable

@Dao
abstract class EventDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEvent(event : EventDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEvents(events : List<EventDB>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEventCityJoin(eventCityJoin : EventCityJoinDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEventCityJoins(eventCityJoins : List<EventCityJoinDB>)

    @Transaction
    @Query("SELECT * FROM event")
    abstract fun getEvents() : Flowable<List<EventWithCitiesDB>>

    @Transaction
    @Query("SELECT * FROM event WHERE id=:eventId")
    abstract fun getEvent(eventId : Long) : Flowable<EventWithCitiesDB>

    @Query("SELECT * FROM city WHERE id=:id")
    abstract fun getCity(id : Long) : CityDB

    @Transaction
    open fun updateEvents(events : List<EventWithCitiesDB>) {
        events.forEach{event ->
            insertEvent(event.event)
            event.cityIds.forEach {city ->
                insertEventCityJoin(EventCityJoinDB(event.event.id, city))
            }
        }
    }
}