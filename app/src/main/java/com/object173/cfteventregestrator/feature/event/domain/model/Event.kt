package com.object173.cfteventregestrator.feature.event.domain.model

import com.object173.cfteventregestrator.feature.base.domain.City

data class Event(
    val id : Long,
    val title : String,
    val cities : Array<City>?,
    val description : String,
    val format : Int,
    val date : EventDate?,
    val cardImage : String,
    val status : Int,
    val iconStatus : String,
    val eventFormat : String,
    val eventFormatEng : String
)