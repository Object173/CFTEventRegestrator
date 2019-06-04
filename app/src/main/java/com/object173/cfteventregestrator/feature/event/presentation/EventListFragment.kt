package com.object173.cfteventregestrator.feature.event.presentation

import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.presentation.list.*
import com.object173.cfteventregestrator.feature.event.domain.model.Event
import javax.inject.Inject

class EventListFragment : BaseListFragment<Long, Event>() {

    @Inject lateinit var viewModelFactory: EventListViewModelFactory

    override fun getCurrentViewModel(): BaseListViewModel<Long, Event> {
        return ViewModelProviders.of(this, viewModelFactory)
            .get(EventListViewModel::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAdapter(): BaseRecyclerViewAdapter<Event, BaseViewHolder<Event>> {
        return EventListAdapter() as BaseRecyclerViewAdapter<Event, BaseViewHolder<Event>>
    }

    override fun onItemClick(item: Event) {
        activity?.let {
            goToFragment(R.id.action_eventListFragment_to_participantListFragment,
                BaseListFragment.getArgs(item.id, item.title))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.event_list_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun myViewModel() : EventListViewModel {
        return viewModel as EventListViewModel
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        if(!myViewModel().queryString.isEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(myViewModel().queryString, true)
            searchView.clearFocus()
        }
        else {
            searchItem.collapseActionView()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                myViewModel().queryString = newText
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
        })

        super.onPrepareOptionsMenu(menu)
    }
}
