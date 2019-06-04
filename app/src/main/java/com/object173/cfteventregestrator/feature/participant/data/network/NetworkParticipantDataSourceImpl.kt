package com.object173.cfteventregestrator.feature.participant.data.network

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit

class NetworkParticipantDataSourceImpl(
    val retrofit : Retrofit
) : NetworkParticipantDataSource {

    private val api = retrofit.create(ParticipantApi::class.java)

    override fun fetchParticipants(eventId: Long): Single<List<Participant>> {
        return api.eventList(106)
    }

    override fun pushParticipants(eventId: Long, participants: Array<ParticipantVisited>) : Completable {
         return api.updateParticipants(106, participants)
    }
}