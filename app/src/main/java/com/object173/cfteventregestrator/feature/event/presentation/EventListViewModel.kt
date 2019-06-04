package com.object173.cfteventregestrator.feature.event.presentation

import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseListViewModel
import com.object173.cfteventregestrator.feature.base.presentation.list.FilterItem
import com.object173.cfteventregestrator.feature.event.domain.model.Event
import com.object173.cfteventregestrator.feature.event.domain.EventListInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*

class EventListViewModel(private val mInteractor : EventListInteractor) : BaseListViewModel<Long, Event>() {

    var queryString : String = ""
        set(value) {
            field = value.toLowerCase().trim()
            invalidateData()
        }

    override fun updateDataAll(): Completable {
        return mInteractor.updateEvents()
    }

    override fun getDataAll(): Flowable<List<Event>> {
        return mInteractor.getEvents()
    }

    override fun filterData(list: List<Event>): List<Event> {
        val newList = super.filterData(list)

        return if (queryString.isEmpty()) newList
        else
            newList.filter { event ->
                val title = event.title
                title.toLowerCase().trim().contains(queryString)
            }
    }

    override fun filterMenu(): Int? = R.menu.event_list_filter

    override fun filters(): List<FilterItem<Event>> {
        return listOf(FilterItem(R.id.filter_all, {it}),
            FilterItem(R.id.filter_active) { list ->
                val currentDate = Date()
                list.filter {event ->
                    event.date?.let{between(currentDate, it.start, it.end)} ?: false}
            },
            FilterItem(R.id.filter_no_active) { list ->
                val currentDate = Date()
                list.filter {event ->
                    event.date?.let{!between(currentDate, it.start, it.end)} ?: true}
            }
        )
    }

    fun between(date: Date?, dateStart: Date?, dateEnd: Date?): Boolean {
        return (date?.after(dateStart) ?: false) && (date?.before(dateEnd) ?: false)
    }
}