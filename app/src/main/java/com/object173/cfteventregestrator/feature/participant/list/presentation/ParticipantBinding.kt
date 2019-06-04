package com.object173.cfteventregestrator.feature.participant.list.presentation

import androidx.lifecycle.MutableLiveData
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant

class ParticipantBinding (
    val participant : Participant,
    changes : Boolean
) {
    val visited  = MutableLiveData<Boolean>()
    val fullName = participant.lastName + " " + participant.firstName

    init {
        visited.value =
            if(changes) !participant.isVisited
            else participant.isVisited
    }

    fun cancelChanges() {
        visited.value = participant.isVisited
    }
}