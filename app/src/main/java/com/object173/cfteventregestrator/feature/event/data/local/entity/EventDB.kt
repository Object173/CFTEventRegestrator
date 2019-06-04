package com.object173.cfteventregestrator.feature.event.data.local.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "event")
data class EventDB(
    @PrimaryKey(autoGenerate = false) val id : Long,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "description") val description : String,
    @ColumnInfo(name = "format") val format : Int,
    @ColumnInfo(name = "startDate") val startDate : Date?,
    @ColumnInfo(name = "endDate") val endDate : Date?,
    @ColumnInfo(name = "cardImage") val cardImage : String,
    @ColumnInfo(name = "status") val status : Int,
    @ColumnInfo(name = "iconStatus") val iconStatus : String,
    @ColumnInfo(name = "eventFormat") val eventFormat : String,
    @ColumnInfo(name = "eventFormatEng") val eventFormatEng : String
)

@Entity(tableName = "event_city_join",
    primaryKeys = ["eventId", "cityId"],
    foreignKeys = [
        ForeignKey(
            entity = EventDB::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CityDB::class,
            parentColumns = ["id"],
            childColumns = ["cityId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ])
data class EventCityJoinDB (
    var eventId : Long,
    var cityId : Long
)

class EventWithCitiesDB {
    @Embedded lateinit var event : EventDB

    @Relation(parentColumn = "id", entityColumn = "eventId",
        entity = EventCityJoinDB::class, projection = ["cityId"])
    lateinit var cityIds : List<Long>
}

