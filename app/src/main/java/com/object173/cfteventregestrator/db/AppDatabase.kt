package com.object173.cfteventregestrator.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.object173.cfteventregestrator.db.converter.DateConverter
import com.object173.cfteventregestrator.feature.event.data.local.dao.CityDAO
import com.object173.cfteventregestrator.feature.event.data.local.dao.EventDAO
import com.object173.cfteventregestrator.feature.participant.data.local.dao.ParticipantDAO
import com.object173.cfteventregestrator.feature.participant.data.local.entity.*
import com.object173.cfteventregestrator.feature.event.data.local.entity.CityDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventCityJoinDB
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventDB

@Database(entities = [CityDB::class, EventCityJoinDB::class, EventDB::class, ParticipantDB::class,
    ParticipantEventJoinDB::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDAO(): EventDAO
    abstract fun cityDAO() : CityDAO
    abstract fun participantDAO() : ParticipantDAO
}