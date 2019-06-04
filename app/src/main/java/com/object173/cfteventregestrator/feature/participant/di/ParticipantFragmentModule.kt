package com.object173.cfteventregestrator.feature.participant.di

import com.object173.cfteventregestrator.feature.participant.data.ParticipantRepositoryImpl
import com.object173.cfteventregestrator.feature.participant.data.local.LocalParticipantDataSource
import com.object173.cfteventregestrator.feature.participant.data.local.LocalParticipantDataSourceImpl
import com.object173.cfteventregestrator.feature.participant.data.local.dao.ParticipantDAO
import com.object173.cfteventregestrator.feature.participant.data.network.NetworkParticipantDataSource
import com.object173.cfteventregestrator.feature.participant.data.network.NetworkParticipantDataSourceImpl
import com.object173.cfteventregestrator.feature.participant.domain.ParticipantRepository
import com.object173.cfteventregestrator.feature.participant.item.domain.ParticipantItemInteractor
import com.object173.cfteventregestrator.feature.participant.item.domain.ParticipantItemInteractorImpl
import com.object173.cfteventregestrator.feature.participant.item.presentation.ParticipantItemViewModelFactory
import com.object173.cfteventregestrator.feature.participant.list.domian.ParticipantListInteractor
import com.object173.cfteventregestrator.feature.participant.list.domian.ParticipantListInteractorImpl
import com.object173.cfteventregestrator.feature.participant.list.presentation.ParticipantListViewModelFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ParticipantFragmentModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun participantListViewModelFactory(interactor : ParticipantListInteractor)
                : ParticipantListViewModelFactory {
            return ParticipantListViewModelFactory(interactor)
        }

        @Provides
        @JvmStatic
        @Singleton
        fun participantItemViewModelFactory(interactor : ParticipantItemInteractor)
                : ParticipantItemViewModelFactory {
            return ParticipantItemViewModelFactory(interactor)
        }
    }

    @Provides
    @Singleton
    fun participantListInteractor(repository: ParticipantRepository) : ParticipantListInteractor {
        return ParticipantListInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun participantItemInteractor(repository: ParticipantRepository) : ParticipantItemInteractor {
        return ParticipantItemInteractorImpl(repository)
    }

    @Provides
    @Singleton
    fun participantRepository(localDataSource : LocalParticipantDataSource, networkDataSource : NetworkParticipantDataSource)
            : ParticipantRepository {
        return ParticipantRepositoryImpl(localDataSource, networkDataSource)
    }

    @Provides
    @Singleton
    fun localDataSource(participantDAO: ParticipantDAO) : LocalParticipantDataSource =
        LocalParticipantDataSourceImpl(participantDAO)

    @Provides
    @Singleton
    fun networkDataSource(retrofit: Retrofit) : NetworkParticipantDataSource {
        return NetworkParticipantDataSourceImpl(retrofit)
    }
}