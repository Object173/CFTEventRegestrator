package com.object173.cfteventregestrator.feature.participant.list.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseListViewModel
import com.object173.cfteventregestrator.feature.base.presentation.list.FilterItem
import com.object173.cfteventregestrator.feature.participant.list.domian.ParticipantListInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

class ParticipantListViewModel(
    private val interactor : ParticipantListInteractor
) : BaseListViewModel<Long, ParticipantBinding>() {

    enum class ChangeState {
        DEFAULT,
        CHANGED
    }

    private val changes = MutableLiveData<MutableList<Long>>()

    val changesCount : LiveData<Int> by lazy {
        Transformations.map(changes) {it.size}
    }

    val changeStatus : LiveData<ChangeState> by lazy {
        Transformations.map(changes
        ) {if(it.isEmpty()) ChangeState.DEFAULT else ChangeState.CHANGED}
    }

    val participantCount : LiveData<Int> by lazy {
        Transformations.map(dataList) {it.size}
    }

    val visitedCount : LiveData<Int> by lazy {
        Transformations.map(dataList) {it.map {it.participant.isVisited}.count {it}}
    }

    var queryString : String = ""
    set(value) {
        field = value.toLowerCase().trim()
        invalidateData()
    }

    private val applyChanges by lazy {
        interactor.pushParticipants(key!!,
            dataList.value!!
                .filter {changes.value!!.contains(it.participant.id)}
                .map {it.participant})
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {setUpdateStatus(true)}
            .doOnTerminate {
                setUpdateStatus(false)
            }
    }

    init{
        changes.value = mutableListOf()
    }

    override fun updateDataByKey(key: Long): Completable {
        return interactor.updateParticipants(key)
    }

    override fun getDataByKey(key: Long): Flowable<List<ParticipantBinding>> {
        return interactor.getParticipants(key)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val newChanges : MutableList<Long> = mutableListOf()
                val newMap = it.map {participant ->
                    val isChanged = changes.value!!.contains(participant.id)
                    if(isChanged) {newChanges.add(participant.id)}
                    ParticipantBinding(participant, isChanged)
                }
                if(!changes.value!!.isEmpty()) changes.value = newChanges
                newMap
            }
    }

    fun setChangedParticipant(item : ParticipantBinding) {
        item.visited.value = !item.visited.value!!

        if(item.participant.isVisited != item.visited.value) {
            changes.value!!.add(item.participant.id)
        }
        else {
            changes.value!!.remove(item.participant.id)
        }
        changes.value = changes.value
    }

    fun cancelChanges() {
        dataList.value?.forEach {it.cancelChanges()}
        changes.value = mutableListOf()
    }

    @SuppressLint("CheckResult")
    fun applyChanges() {
        if(updateStatus.value!!) return

        applyChanges
            .subscribe(
                {
                    dataList.value?.forEach {it.cancelChanges()}
                    changes.value = mutableListOf()
                },
                {handleError(it)})
    }

    override fun filterData(list: List<ParticipantBinding>): List<ParticipantBinding> {
        val newList = super.filterData(list)
        return if (queryString.isEmpty()) newList
        else
            newList.filter { participant ->
                val fullName = participant.participant.lastName + " " + participant.participant.firstName
                fullName.toLowerCase().trim().contains(queryString)
            }
    }

    override fun filterMenu(): Int? = R.menu.participant_list_filter

    override fun filters(): List<FilterItem<ParticipantBinding>> {
        return listOf(FilterItem(R.id.sort_name) { list->
            list.sortedBy {it.participant.lastName + " " + it.participant.firstName + " " + it.participant.patronymic} },
            FilterItem(R.id.sort_company) { list ->
                list.sortedBy {it.participant.company}},
            FilterItem(R.id.sort_visited) { list->
                list.sortedBy {it.participant.isVisited}}
        )
    }
}