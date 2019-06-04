package com.object173.cfteventregestrator.feature.participant.data.local

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import io.reactivex.Flowable
import io.reactivex.Observable

interface LocalParticipantDataSource {
    fun getParticipants(eventId : Long) : Flowable<List<Participant>>
    fun updateParticipants(eventId : Long, participants : List<Participant>)

    fun getParticipant(id : Long) : Flowable<Participant>
}