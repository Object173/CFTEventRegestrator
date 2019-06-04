package com.object173.cfteventregestrator.feature.event.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.object173.cfteventregestrator.feature.event.data.local.entity.CityDB

@Dao
abstract class CityDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCity(city : CityDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCities(cities : List<CityDB>)
}