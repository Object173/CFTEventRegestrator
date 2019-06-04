package com.object173.cfteventregestrator.feature.participant.item.presentation

import android.content.Intent
import android.net.Uri
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.databinding.FragmentParticipantItemBinding
import com.object173.cfteventregestrator.feature.base.presentation.item.BaseItemFragment
import com.object173.cfteventregestrator.feature.participant.domain.model.Participant
import javax.inject.Inject


class ParticipantItemFragment : BaseItemFragment<Long, Participant, ParticipantItemViewModel>() {

    @Inject lateinit var viewModelFactory : ParticipantItemViewModelFactory

    override fun getViewModel(key: Long): ParticipantItemViewModel {
        return ViewModelProviders.of(activity!!, viewModelFactory).get(ParticipantItemViewModel::class.java)
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        viewModel: ParticipantItemViewModel
    ): ViewDataBinding {
        val binding : FragmentParticipantItemBinding = DataBindingUtil.inflate(inflater , R.layout.fragment_participant_item, container , false)
        binding.viewModel = viewModel
        return binding
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.prticipant_item_default, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val sendItem = menu!!.findItem(R.id.action_send)
        val callItem = menu.findItem(R.id.action_call)

        sendItem.isVisible = viewModel.data.value?.email?.let {!it.isEmpty()} ?: false
        callItem.isVisible = viewModel.data.value?.phone?.let {!it.isEmpty()} ?: false

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val participant = viewModel.data.value
        when(item?.itemId) {
            R.id.action_send -> {
                val emailIntent = Intent(
                    Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", participant?.email, null)
                )
                startActivity(Intent.createChooser(emailIntent, getString(R.string.send_action_chooser_title)))
            }
            R.id.action_call -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", participant?.phone, null))
                startActivity(Intent.createChooser(intent, getString(R.string.call_action_chooser_title)))
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun getTitle(): String {
        return viewModel.fullName.value?.let {it} ?: super.getTitle()
    }

    override fun changeItem(item: Participant) {
        setTitle(viewModel.fullName.value)
        activity?.invalidateOptionsMenu()
    }
}