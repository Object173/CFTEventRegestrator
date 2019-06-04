package com.object173.cfteventregestrator.feature.participant.list.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.object173.cfteventregestrator.App
import com.object173.cfteventregestrator.feature.participant.data.ParticipantRepositoryImpl
import com.object173.cfteventregestrator.feature.participant.list.domian.ParticipantListInteractor
import com.object173.cfteventregestrator.feature.participant.list.domian.ParticipantListInteractorImpl
import java.lang.IllegalStateException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ParticipantListViewModelFactory @Inject constructor(
    private val interactor : ParticipantListInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass == ParticipantListViewModel::class.java) {
            return ParticipantListViewModel(interactor) as T
        }
        throw IllegalStateException()
    }
}