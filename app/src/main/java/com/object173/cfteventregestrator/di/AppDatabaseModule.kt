package com.object173.cfteventregestrator.di

import android.app.Application
import androidx.room.Room
import com.object173.cfteventregestrator.db.AppDatabase
import com.object173.cfteventregestrator.feature.event.data.local.dao.CityDAO
import com.object173.cfteventregestrator.feature.event.data.local.dao.EventDAO
import com.object173.cfteventregestrator.feature.participant.data.local.dao.ParticipantDAO
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class AppDatabaseModule {

    @Provides
    @Singleton
    fun getDB(app : Application) :AppDatabase {
        return Room.databaseBuilder(app,
            AppDatabase::class.java, "database.db")
            .build()
    }

    @Provides
    @Singleton
    fun getEventDao(db : AppDatabase) : EventDAO {
        return db.eventDAO()
    }

    @Provides
    @Singleton
    fun getCityDao(db : AppDatabase) : CityDAO {
        return db.cityDAO()
    }

    @Provides
    @Singleton
    fun getParticipantDao(db : AppDatabase) : ParticipantDAO {
        return db.participantDAO()
    }
}