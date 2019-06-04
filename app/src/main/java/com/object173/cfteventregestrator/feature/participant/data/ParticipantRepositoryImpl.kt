package com.object173.cfteventregestrator.feature.participant.data

import com.object173.cfteventregestrator.feature.participant.data.local.LocalParticipantDataSource
import com.object173.cfteventregestrator.feature.participant.data.network.NetworkParticipantDataSource
import com.object173.cfteventregestrator.feature.participant.domain.ParticipantRepository
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ParticipantRepositoryImpl(
    private val mLocalDataSource : LocalParticipantDataSource,
    private val mNetworkDataSource : NetworkParticipantDataSource
) : ParticipantRepository {

    override fun updateParticipants(eventId : Long): Completable {
        return Completable
            .fromSingle(
                mNetworkDataSource.fetchParticipants(eventId)
                .doOnSuccess {mLocalDataSource.updateParticipants(eventId, it)}
            )
            .subscribeOn(Schedulers.io())
    }

    override fun getParticipants(eventId : Long): Flowable<List<Participant>> {
        return mLocalDataSource.getParticipants(eventId)
    }

    override fun getParticipant(id: Long): Flowable<Participant> {
        return mLocalDataSource.getParticipant(id)
    }

    override fun pushParticipants(eventId: Long, participants: List<Participant>): Completable {
        return mNetworkDataSource.pushParticipants(eventId,
            participants.map {ParticipantVisited(it.id, it.isVisited)}.toTypedArray())
    }
}