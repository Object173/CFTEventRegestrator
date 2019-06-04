package com.object173.cfteventregestrator.feature.event.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.object173.cfteventregestrator.databinding.EventListItemBinding
import com.object173.cfteventregestrator.feature.event.domain.model.Event
import androidx.recyclerview.widget.DiffUtil
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseRecyclerViewAdapter
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseViewHolder

class EventListAdapter : BaseRecyclerViewAdapter<Event, EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : EventListItemBinding = DataBindingUtil.inflate(inflater, R.layout.event_list_item, parent, false)
        return EventViewHolder(binding)
    }

    override fun diffResult(oldList: List<Event>, newList: List<Event>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(EventDiffUtilCallback(oldList, newList))
    }
}

class EventViewHolder(mBinding : EventListItemBinding) :
    BaseViewHolder<Event>(mBinding) {

    override fun getValue(): Event? {
        return (binding as EventListItemBinding).event
    }

    override fun setValue(value: Event) {
        (binding as EventListItemBinding).event = value
    }
}

class EventDiffUtilCallback(
    private val oldList : List<Event>,
    private val newList : List<Event>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
}