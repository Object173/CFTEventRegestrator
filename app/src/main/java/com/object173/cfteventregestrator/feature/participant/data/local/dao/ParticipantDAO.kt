package com.object173.cfteventregestrator.feature.participant.data.local.dao

import androidx.room.*
import com.object173.cfteventregestrator.feature.participant.data.local.entity.ParticipantDB
import com.object173.cfteventregestrator.feature.participant.data.local.entity.ParticipantEventJoinDB
import io.reactivex.Flowable

@Dao
abstract class ParticipantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertParticipant(participant : ParticipantDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertParticipants(participants : List<ParticipantDB>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertParticipantEventJoin(participantEventJoin : ParticipantEventJoinDB)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertParticipantEventJoins(participantEventJoins : List<ParticipantEventJoinDB>)

    @Query("select * from participant WHERE id=:id")
    abstract fun getParticipant(id : Long) : Flowable<ParticipantDB>

    @Query("select * from participant")
    abstract fun getParticipants() : Flowable<List<ParticipantDB>>

    @Transaction
    @Query("select * from participant where " +
            "participant.id IN (select participant_event_join.participantId from participant_event_join where eventId=:eventId)")
    abstract fun getEventParticipants(eventId : Long) : Flowable<List<ParticipantDB>>

    @Transaction
    open fun updateParticipants(eventId : Long, participants : List<ParticipantDB>) {
        participants.forEach {
            insertParticipant(it)
            insertParticipantEventJoin(ParticipantEventJoinDB(it.id, eventId))
        }
    }
}