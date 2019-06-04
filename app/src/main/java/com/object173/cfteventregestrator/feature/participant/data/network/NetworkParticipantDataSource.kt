package com.object173.cfteventregestrator.feature.participant.data.network

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Single

interface NetworkParticipantDataSource {
    fun fetchParticipants(eventId : Long) : Single<List<Participant>>
    fun pushParticipants(eventId : Long, participants : Array<ParticipantVisited>) : Completable
}