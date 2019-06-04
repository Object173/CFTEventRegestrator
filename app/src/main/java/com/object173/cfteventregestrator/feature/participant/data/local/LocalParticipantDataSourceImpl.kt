package com.object173.cfteventregestrator.feature.participant.data.local

import androidx.room.Transaction
import com.object173.cfteventregestrator.feature.participant.data.local.dao.ParticipantDAO
import com.object173.cfteventregestrator.feature.participant.data.local.entity.ParticipantDB
import com.object173.cfteventregestrator.feature.participant.data.local.entity.ParticipantEventJoinDB
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import io.reactivex.Flowable

class LocalParticipantDataSourceImpl(
    private val mParticipantDAO: ParticipantDAO
) : LocalParticipantDataSource {

    override fun updateParticipants(eventId : Long, participants: List<Participant>) {
        mParticipantDAO.updateParticipants(eventId,
            participants.map { convertToParticipantDB(it)})
    }

    override fun getParticipants(eventId: Long): Flowable<List<Participant>> {
        return mParticipantDAO.getEventParticipants(eventId)
            .map {list -> list.map { convertToParticipant(it)}}
    }

    override fun getParticipant(id: Long): Flowable<Participant> {
        return mParticipantDAO.getParticipant(id)
            .map { convertToParticipant(it)}
    }

    companion object {
        fun convertToParticipant(participantDB : ParticipantDB) : Participant =
                Participant(participantDB.id, participantDB.phone, participantDB.city,
                    participantDB.company, participantDB.position, participantDB.addition,
                    participantDB.registeredDate, participantDB.isVisited, participantDB.agreedByManager,
                    participantDB.lastName, participantDB.firstName, participantDB.patronymic, participantDB.email)

        fun convertToParticipantDB(participant : Participant) : ParticipantDB =
            ParticipantDB(participant.id, participant.phone, participant.city, participant.company,
                participant.position, participant.addition, participant.registeredDate, participant.isVisited,
                participant.agreedByManager, participant.lastName, participant.firstName, participant.patronymic, participant.email)
    }
}