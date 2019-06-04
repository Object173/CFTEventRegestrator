package com.object173.cfteventregestrator.feature.event.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityDB(
    @PrimaryKey(autoGenerate = false) var id : Long,
    @ColumnInfo(name = "nameRus") var nameRus : String,
    @ColumnInfo(name = "nameEng") var nameEng : String,
    @ColumnInfo(name = "icon") var icon : String,
    @ColumnInfo(name = "isActive") var isActive : Boolean
)