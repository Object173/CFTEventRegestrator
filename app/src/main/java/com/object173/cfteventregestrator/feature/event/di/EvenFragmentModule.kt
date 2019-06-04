package com.object173.cfteventregestrator.feature.event.di

import com.object173.cfteventregestrator.feature.event.data.EventRepositoryImpl
import com.object173.cfteventregestrator.feature.event.data.local.LocalEventDataSource
import com.object173.cfteventregestrator.feature.event.data.local.LocalEventDataSourceImpl
import com.object173.cfteventregestrator.feature.event.data.local.dao.CityDAO
import com.object173.cfteventregestrator.feature.event.data.local.dao.EventDAO
import com.object173.cfteventregestrator.feature.event.data.network.NetworkEventDataSource
import com.object173.cfteventregestrator.feature.event.data.network.NetworkEventDataSourceImpl
import com.object173.cfteventregestrator.feature.event.domain.EventListInteractor
import com.object173.cfteventregestrator.feature.event.domain.EventListInteractorImpl
import com.object173.cfteventregestrator.feature.event.domain.EventRepository
import com.object173.cfteventregestrator.feature.event.presentation.EventListViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class EvenFragmentModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun eventListViewModelFactory(interactor : EventListInteractor)
                : EventListViewModelFactory {
            return EventListViewModelFactory(interactor)
        }
    }

    @Provides
    @Singleton
    fun eventListInteractor(repository: EventRepository) : EventListInteractor {
        return EventListInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun eventRepository(localDataSource : LocalEventDataSource, networkDataSource : NetworkEventDataSource)
            : EventRepository {
        return EventRepositoryImpl(localDataSource, networkDataSource)
    }

    @Provides
    @Singleton
    fun localDataSource(eventDao : EventDAO, cityDAO: CityDAO) : LocalEventDataSource =
        LocalEventDataSourceImpl(eventDao, cityDAO)

    @Provides
    @Singleton
    fun networkDataSource(retrofit: Retrofit) : NetworkEventDataSource {
        return NetworkEventDataSourceImpl(retrofit)
    }
}