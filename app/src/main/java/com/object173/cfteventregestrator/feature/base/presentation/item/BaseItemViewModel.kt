package com.object173.cfteventregestrator.feature.base.presentation.item

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.extensions.addToComposite
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.io.Serializable

abstract class BaseItemViewModel<K : Serializable, V> : ViewModel() {

    enum class ViewState {
        PROGRESS,
        FAIL,
        CONTENT
    }

    private val disposable = CompositeDisposable()

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    var key : K? = null
    set(value) {
        if(value == null || this.key == value) return

        getData(value)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{_viewState.value = ViewState.PROGRESS}
            .subscribe(
                {
                    _data.value = it
                    _viewState.value = ViewState.CONTENT
                },
                {
                    handleError(it)
                    _viewState.value = ViewState.FAIL
                })
            .addToComposite(disposable)

        field = value
    }

    private val _data = MutableLiveData<V>()
    val data : LiveData<V> = _data

    val errorMessage = PublishSubject.create<Int>()

    protected open fun handleError(error : Throwable) {
        errorMessage.onNext(R.string.error_not_found_item)
    }

    protected abstract fun getData(key : K) : Flowable<V>

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}