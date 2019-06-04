package com.object173.cfteventregestrator.feature.participant.list.presentation

import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.presentation.item.BaseItemFragment
import com.object173.cfteventregestrator.feature.base.presentation.list.*
import javax.inject.Inject


class ParticipantListFragment : BaseListFragment<Long, ParticipantBinding>() {

    @Inject lateinit var viewModelFactory : ParticipantListViewModelFactory

    override fun onStart() {
        super.onStart()

        val mediator = MediatorLiveData<Int>()
        mediator.addSource(myViewModel().changeStatus) {
            activity?.invalidateOptionsMenu()
            mediator.value = 0
        }
        mediator.addSource(myViewModel().changesCount) {mediator.value = it}
        mediator.addSource(myViewModel().participantCount){mediator.value = it}
        mediator.addSource(myViewModel().visitedCount){mediator.value = it}

        mediator.observe(this, Observer {updateActionBar()})
    }

    override fun getSubtitle(): String {
        val viewModel = myViewModel()
        return when(viewModel.changeStatus.value) {
            ParticipantListViewModel.ChangeState.DEFAULT -> getString(R.string.event_action_bar_title_default, viewModel.visitedCount.value, viewModel.participantCount.value)
            ParticipantListViewModel.ChangeState.CHANGED -> getString(R.string.event_action_bar_title_changed, viewModel.changesCount.value)
            else -> super.getSubtitle()
        }
    }

    override fun getCurrentViewModel(): BaseListViewModel<Long, ParticipantBinding> {
        return ViewModelProviders.of(this, viewModelFactory).get(ParticipantListViewModel::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAdapter(): BaseRecyclerViewAdapter<ParticipantBinding, BaseViewHolder<ParticipantBinding>> {
        return ParticipantListAdapter() as BaseRecyclerViewAdapter<ParticipantBinding, BaseViewHolder<ParticipantBinding>>
    }

    override fun onItemClick(item: ParticipantBinding) {
        myViewModel().setChangedParticipant(item)
    }

    override fun onItemLongClick(item: ParticipantBinding) {
        activity?.let {
            goToFragment(R.id.action_participantListFragment_to_participantItemFragment,
                BaseItemFragment.getArgs(item.participant.id))
        }
    }

    private fun myViewModel() : ParticipantListViewModel {
        return viewModel as ParticipantListViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.participant_list_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                myViewModel().queryString = newText
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                myViewModel().queryString = query
                return false
            }
        })

        if(!myViewModel().queryString.isEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(myViewModel().queryString, false)
            searchView.clearFocus()
        }
        else {
            searchItem.collapseActionView()
        }

        when(myViewModel().changeStatus.value) {
            ParticipantListViewModel.ChangeState.DEFAULT -> {
                setHomeButton(null, false)
                menu.findItem(R.id.action_apply)?.isVisible = false
            }
            ParticipantListViewModel.ChangeState.CHANGED -> {
                setHomeButton(android.R.drawable.ic_menu_close_clear_cancel, true)
                menu.findItem(R.id.action_apply)?.isVisible = true
            }
        }

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_apply -> myViewModel().applyChanges()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onHomeClick(): Boolean {
        myViewModel().cancelChanges()
        return true
    }
}