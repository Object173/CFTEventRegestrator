package com.object173.cfteventregestrator.feature.participant.list.domian

import com.object173.cfteventregestrator.feature.participant.domain.ParticipantRepository
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Flowable

class ParticipantListInteractorImpl(
    private val repository : ParticipantRepository
) : ParticipantListInteractor {

    override fun updateParticipants(eventId: Long): Completable {
        return repository.updateParticipants(eventId)
    }

    override fun getParticipants(eventId: Long): Flowable<List<Participant>> {
        return repository.getParticipants(eventId)
    }

    override fun pushParticipants(eventId: Long, participants: List<Participant>): Completable {
         return repository.pushParticipants(eventId, participants)
    }
}