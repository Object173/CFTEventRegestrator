package com.object173.cfteventregestrator.feature.participant.item.domain

import com.object173.cfteventregestrator.feature.participant.domain.ParticipantRepository
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import io.reactivex.Flowable

class ParticipantItemInteractorImpl(
    private val repository : ParticipantRepository
) : ParticipantItemInteractor {

    override fun getParticipant(id: Long): Flowable<Participant> {
        return repository.getParticipant(id)
    }

}