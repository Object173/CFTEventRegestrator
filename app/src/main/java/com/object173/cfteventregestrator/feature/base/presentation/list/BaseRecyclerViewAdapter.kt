package com.object173.cfteventregestrator.feature.base.presentation.list

import android.annotation.SuppressLint
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

abstract class BaseRecyclerViewAdapter<V, VH : BaseViewHolder<V>> : RecyclerView.Adapter<VH>() {

    val onItemClick = PublishSubject.create<V>()
    val onItemLongClick = PublishSubject.create<V>()

    private var mList : List<V> = emptyList()

    @SuppressLint("CheckResult")
    fun updateList(newList: List<V>) {
        val oldList = mList
        Single.create<DiffUtil.DiffResult> {
            it.onSuccess(diffResult(oldList, newList))
        }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    mList = newList
                    it.dispatchUpdatesTo(this)},
                {}
            )
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(mList[position])

        holder.itemView.setOnClickListener{
            holder.getValue()?.let {onItemClick.onNext(it)}
        }
        holder.itemView.setOnLongClickListener {
            holder.getValue()?.let {onItemLongClick.onNext(it)}
            return@setOnLongClickListener true
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        onItemClick.onComplete()
        onItemLongClick.onComplete()
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.markAttach()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.markDetach()
    }

    protected abstract fun diffResult(oldList : List<V>, newList : List<V>) : DiffUtil.DiffResult
}

abstract class BaseViewHolder<V> (
    val binding : ViewDataBinding
) : RecyclerView.ViewHolder(binding.root), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.markState(Lifecycle.State.CREATED)
    }

    fun bind(value : V) {
        setValue(value)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    fun markAttach() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    fun markDetach() {
        lifecycleRegistry.markState(Lifecycle.State.STARTED)
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    protected abstract fun setValue(value : V)
    abstract fun getValue() : V?
}