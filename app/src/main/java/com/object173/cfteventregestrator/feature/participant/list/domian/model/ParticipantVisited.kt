package com.object173.cfteventregestrator.feature.participant.list.domian.model

import java.util.*

data class ParticipantVisited (
    val id : Long,
    val visited : Boolean
) {
    val date : Date

    init {
        date = Date()
    }
}