package com.object173.cfteventregestrator.feature.base.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.databinding.FragmentListBinding
import com.object173.cfteventregestrator.feature.base.extensions.addToComposite
import com.object173.cfteventregestrator.feature.base.presentation.BaseNavigationFragment
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
abstract class BaseListFragment<K : Serializable, V> : BaseNavigationFragment() {

    companion object {
        private const val ARG_KEY = "BaseListFragment_key"
        private const val ARG_TITLE = "BaseListFragment_title"

        fun <K : Serializable> getArgs(key : K) : Bundle {
            val args = Bundle()
            args.putSerializable(ARG_KEY, key)
            return args
        }

        fun <K : Serializable> getArgs(key : K, title : String) : Bundle {
            val args = getArgs(key)
            args.putString(ARG_TITLE, title)
            return args
        }
    }

    private val disposable = CompositeDisposable()

    protected val key : K? by lazy {
        arguments?.let {it[ARG_KEY]?.let {it as K}}
    }

    protected lateinit var filters : List<FilterItem<V>>

    protected lateinit var binding : FragmentListBinding
    protected val viewModel by lazy {
        getCurrentViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_list, container , false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initRecyclerView()
        initRefreshLayout()

        viewModel.errorMessage
            .subscribe {handleError(it)}
            .addToComposite(disposable)

        return binding.root
    }

    protected abstract fun getCurrentViewModel() : BaseListViewModel<K, V>
    protected open fun onItemClick(item : V) {}
    protected open fun onItemLongClick(item : V) {}
    protected abstract  fun getAdapter() : BaseRecyclerViewAdapter<V, BaseViewHolder<V>>

    private fun initRefreshLayout() {
        binding.swipeRefreshLayout.setProgressViewOffset(false,
            resources.getDimensionPixelOffset(R.dimen.refresher_offset),
            resources.getDimensionPixelOffset(R.dimen.refresher_offset_end))
    }

    private fun initRecyclerView() {
        val recyclerAdapter = getAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.adapter = recyclerAdapter

        viewModel.getData(key).observe(this, Observer {recyclerAdapter.updateList(it)})

        recyclerAdapter.onItemClick
            .subscribe{onItemClick(it)}
            .addToComposite(disposable)
        recyclerAdapter.onItemLongClick
            .subscribe{onItemLongClick(it)}
            .addToComposite(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    override fun getTitle(): String = arguments?.getString(ARG_TITLE) ?: super.getTitle()
}