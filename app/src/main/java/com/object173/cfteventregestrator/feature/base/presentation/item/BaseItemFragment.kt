package com.object173.cfteventregestrator.feature.base.presentation.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.object173.cfteventregestrator.feature.base.extensions.addToComposite
import com.object173.cfteventregestrator.feature.base.presentation.BaseNavigationFragment
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
abstract class BaseItemFragment<K : Serializable, V, VM : BaseItemViewModel<K, V>> :
    BaseNavigationFragment() {

    companion object {
        private const val ARG_KEY = "BaseItemFragment_key"

        fun <K : Serializable> getArgs(key : K) : Bundle {
            val args = Bundle()
            args.putSerializable(ARG_KEY, key)
            return args
        }
    }

    private val disposable = CompositeDisposable()

    protected val key : K by lazy {
        arguments!!.getSerializable(ARG_KEY) as K
    }

    protected val viewModel by lazy {
        val key = arguments!!.getSerializable(ARG_KEY) as K
        getViewModel(key)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.key = key
    }

    override fun onStart() {
        super.onStart()
        setHomeButton(null, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = getBinding(inflater, container, viewModel)
        binding.lifecycleOwner = this

        viewModel.data.observe(this, Observer {changeItem(it)})
        viewModel.errorMessage
            .subscribe {handleError(it)}
            .addToComposite(disposable)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }

    override fun handleError(messageId : Int) {
        view?.let {
            Snackbar.make(it, messageId, Snackbar.LENGTH_LONG).show()
        }
    }

    protected abstract fun getViewModel(key : K) : VM
    protected abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?, viewModel : VM) : ViewDataBinding
    protected abstract fun changeItem(item : V)
}