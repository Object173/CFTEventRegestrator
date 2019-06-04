package com.object173.cfteventregestrator.feature.participant.list.domian

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Flowable

interface ParticipantListInteractor {
    fun getParticipants(eventId : Long) : Flowable<List<Participant>>
    fun updateParticipants(eventId : Long) : Completable
    fun pushParticipants(eventId : Long, participants : List<Participant>) : Completable
}