package com.object173.cfteventregestrator.feature.participant.data.network

import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.list.domian.model.ParticipantVisited
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ParticipantApi {
    @GET("/api/v1/Registration/members/event/{eventId}")
    fun eventList(@Path("eventId") eventId : Long): Single<List<Participant>>

    @POST("/api/v1/Registration/members/event/{eventId}/confirmation")
    fun updateParticipants(@Path("eventId") eventId : Long, @Body participants : Array<ParticipantVisited>) : Completable
}