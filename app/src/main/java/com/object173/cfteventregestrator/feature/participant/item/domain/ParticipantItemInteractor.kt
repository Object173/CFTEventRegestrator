package com.object173.cfteventregestrator.feature.participant.item.domain

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import io.reactivex.Flowable

interface ParticipantItemInteractor {
    fun getParticipant(id : Long) : Flowable<Participant>
}