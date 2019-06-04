package com.object173.cfteventregestrator.feature.participant.list.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.databinding.ParticipantListItemBinding
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseRecyclerViewAdapter
import com.object173.cfteventregestrator.feature.base.presentation.list.BaseViewHolder
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import io.reactivex.subjects.PublishSubject

class ParticipantListAdapter : BaseRecyclerViewAdapter<ParticipantBinding, ParticipantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : ParticipantListItemBinding = DataBindingUtil.inflate(inflater, R.layout.participant_list_item, parent, false)
        return ParticipantViewHolder(binding)
    }

    override fun diffResult(oldList: List<ParticipantBinding>, newList: List<ParticipantBinding>): DiffUtil.DiffResult {
        return DiffUtil.calculateDiff(EventDiffUtilCallback(oldList,newList))
    }
}

class ParticipantViewHolder(mBinding : ParticipantListItemBinding) :
    BaseViewHolder<ParticipantBinding>(mBinding) {

    override fun getValue(): ParticipantBinding? {
        return (binding as ParticipantListItemBinding).item
    }

    override fun setValue(value: ParticipantBinding) {
        (binding as ParticipantListItemBinding).item = value
    }
}

class EventDiffUtilCallback(
    private val oldList : List<ParticipantBinding>,
    private val newList : List<ParticipantBinding>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].participant.id == newList[newItemPosition].participant.id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].participant == newList[newItemPosition].participant &&
                oldList[oldItemPosition].visited == newList[newItemPosition].visited
}