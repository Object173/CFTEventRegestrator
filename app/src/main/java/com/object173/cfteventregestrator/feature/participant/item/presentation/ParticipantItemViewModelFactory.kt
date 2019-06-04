package com.object173.cfteventregestrator.feature.participant.item.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.object173.cfteventregestrator.feature.participant.item.domain.ParticipantItemInteractor
import java.lang.IllegalStateException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ParticipantItemViewModelFactory @Inject constructor(
    private val interactor : ParticipantItemInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass == ParticipantItemViewModel::class.java) {
            return ParticipantItemViewModel(interactor) as T
        }
        throw IllegalStateException()
    }
}