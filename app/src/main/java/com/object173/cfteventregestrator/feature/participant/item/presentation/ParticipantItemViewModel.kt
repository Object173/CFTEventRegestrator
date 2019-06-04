package com.object173.cfteventregestrator.feature.participant.item.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.object173.cfteventregestrator.feature.base.presentation.item.BaseItemViewModel
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import com.object173.cfteventregestrator.feature.participant.item.domain.ParticipantItemInteractor
import io.reactivex.Flowable

class ParticipantItemViewModel(
    private val interactor : ParticipantItemInteractor
) : BaseItemViewModel<Long, Participant>() {

    val fullName : LiveData<String> by lazy {
        Transformations.map(data) {it.lastName + " " + it.firstName}
    }

    override fun getData(key: Long): Flowable<Participant> {
        return interactor.getParticipant(key)
    }
}