package com.object173.cfteventregestrator.feature.event.data.local

import com.object173.cfteventregestrator.feature.event.data.local.dao.CityDAO
import com.object173.cfteventregestrator.feature.event.data.local.dao.EventDAO
import com.object173.cfteventregestrator.feature.event.data.local.entity.CityDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventDB
import com.object173.cfteventregestrator.feature.base.domain.City
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventWithCitiesDB
import com.object173.cfteventregestrator.feature.event.domain.model.EventDate
import com.object173.cfteventregestrator.feature.event.domain.model.Event
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

class LocalEventDataSourceImpl @Inject constructor(
    private val mEventDao : EventDAO,
    private val mCityDao : CityDAO
) : LocalEventDataSource {

    override fun updateEvents(events: List<Event>) {
        mCityDao.insertCities(
            events.flatMap {
                it.cities?.map { convertToCityDB(it)} ?: emptyList()
            }
        )
        mEventDao.updateEvents(
            events.map { convertToEventWithCitiesDB(it)}
        )
    }

    override fun getEvents(): Flowable<List<Event>> {
        return mEventDao.getEvents()
            .map {
                it.map {convertToEvent(it.event, it.cityIds.map {mEventDao.getCity(it)})}
            }
    }

    override fun getEvent(eventId: Long): Flowable<Event> {
        return mEventDao.getEvent(eventId)
            .map {convertToEvent(it.event, it.cityIds.map {mEventDao.getCity(it)})}
    }

    companion object {
        private fun convertToEventDB(event : Event) : EventDB {
            val startDate = if (event.date != null) event.date.start else null
            val endDate = if (event.date != null) event.date.end else null

            return EventDB(
                event.id, event.title, event.description, event.format, startDate, endDate,
                event.cardImage, event.status, event.iconStatus, event.eventFormat, event.eventFormatEng
            )
        }

        private fun convertToCityDB(city : City) : CityDB {
            return CityDB(
                city.id,
                city.nameRus,
                city.nameEng,
                city.icon,
                city.isActive
            )
        }

        private fun convertToEventWithCitiesDB(event: Event) : EventWithCitiesDB {
            val result = EventWithCitiesDB()
            result.event = convertToEventDB(event)
            result.cityIds = event.cities?.map {it.id} ?: emptyList()
            return result
        }

        private fun convertToEvent(eventDB : EventDB, citiesDB : List<CityDB>) : Event {
            return Event(
                eventDB.id, eventDB.title, citiesDB.map { convertToCity(it) }.toTypedArray(),
                eventDB.description, eventDB.format, EventDate(eventDB.startDate,eventDB.endDate), eventDB.cardImage, eventDB.status, eventDB.iconStatus,
                eventDB.eventFormat, eventDB.eventFormatEng
            )
        }

        private fun convertToCity(cityDB : CityDB) : City {
            return City(
                cityDB.id,
                cityDB.nameRus,
                cityDB.nameEng,
                cityDB.icon,
                cityDB.isActive
            )
        }
    }
}