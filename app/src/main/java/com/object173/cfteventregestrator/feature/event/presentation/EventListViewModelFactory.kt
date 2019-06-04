package com.object173.cfteventregestrator.feature.event.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.object173.cfteventregestrator.feature.event.domain.EventListInteractor
import java.lang.IllegalStateException

@Suppress("UNCHECKED_CAST")
class EventListViewModelFactory (
    private val interactor : EventListInteractor
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass == EventListViewModel::class.java) {
            return EventListViewModel(interactor) as T
        }
        throw IllegalStateException()
    }
}