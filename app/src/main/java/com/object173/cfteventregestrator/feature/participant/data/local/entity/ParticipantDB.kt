package com.object173.cfteventregestrator.feature.participant.data.local.entity

import androidx.room.*
import com.object173.cfteventregestrator.feature.event.data.local.entity.EventDB
import java.util.*

@Entity(tableName = "participant")
data class ParticipantDB(
    @PrimaryKey(autoGenerate = false) var id: Long,
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "position") var position: String,
    @ColumnInfo(name = "addition") var addition: String,
    @ColumnInfo(name = "registeredDate") var registeredDate: Date,
    @ColumnInfo(name = "isVisited") var isVisited: Boolean,
    @ColumnInfo(name = "agreedByManager") var agreedByManager: String,
    @ColumnInfo(name = "lastName") var lastName: String,
    @ColumnInfo(name = "firstName") var firstName: String,
    @ColumnInfo(name = "patronymic") var patronymic: String,
    @ColumnInfo(name = "email") var email: String
)

@Entity(tableName = "participant_event_join",
    primaryKeys = ["participantId", "eventId"],
    foreignKeys = [
        ForeignKey(
            entity = ParticipantDB::class,
            parentColumns = ["id"],
            childColumns = ["participantId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EventDB::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ])
data class ParticipantEventJoinDB (
    var participantId : Long,
    var eventId : Long
)