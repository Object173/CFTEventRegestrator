package com.object173.cfteventregestrator.feature.base.presentation.list

import android.annotation.SuppressLint
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.object173.cfteventregestrator.R
import com.object173.cfteventregestrator.feature.base.device.NoConnectivityException
import com.object173.cfteventregestrator.feature.base.extensions.addToComposite
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import java.io.IOException
import java.io.Serializable
import java.net.SocketTimeoutException

abstract class BaseListViewModel<K : Serializable, V> : ViewModel() {

    enum class ViewState {
        EMPTY,
        FAIL,
        CONTENT
    }

    private val disposable = CompositeDisposable()

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus : LiveData<Boolean> = _updateStatus

    val viewState : LiveData<ViewState> by lazy {
        Transformations.map(dataList) {if(it.isEmpty() && !updateStatus.value!!) ViewState.EMPTY else ViewState.CONTENT}
    }

    val errorMessage = PublishSubject.create<Int>()

    protected var key : K? = null
    private lateinit var _dataList : MutableLiveData<List<V>>
    protected val dataList : LiveData<List<V>> by lazy {
        Transformations.map(_dataList) {filterData(it)}
    }

    private val filters = filters()
    val filtersEnabled = !filters.isEmpty()
    var currentFilter : Int = if(filters.isEmpty()) 0 else filters[0].id
    set(value) {
        field = value
        invalidateData()
    }

    fun getData(key : K?) : LiveData<List<V>> {
        if(!::_dataList.isInitialized || this.key != key) {
            this.key = key

            _dataList = MutableLiveData()
            _dataList.value = emptyList()

            updateData()

            val dataSource =
                if (key != null) getDataByKey(key)
                else getDataAll()

            dataSource
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _dataList.value = it
                    },
                    {
                        ViewState.FAIL
                        handleError(it)
                    })
                .addToComposite(disposable)
        }

        return dataList
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    @SuppressLint("CheckResult")
    fun updateData() {
        val updateCompletable =
            key?.let {updateDataByKey(it)} ?: updateDataAll()

        updateCompletable
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {_updateStatus.value = true}
            .subscribe(
                {_updateStatus.value = false},
                {
                    _updateStatus.value = false
                    handleError(it)
                }
            )
    }

    protected open fun updateDataAll() : Completable {
        return Completable.create {}
    }

    protected open fun updateDataByKey(key : K) : Completable {
        return Completable.create {}
    }

    protected open fun getDataAll() : Flowable<List<V>> {
        return Flowable.empty()
    }

    protected open fun getDataByKey(key : K) : Flowable<List<V>> {
        return Flowable.empty()
    }

    protected open fun handleError(error : Throwable) {
        errorMessage.onNext( when(error) {
            is HttpException -> R.string.error_fail_request
            is SocketTimeoutException -> R.string.error_server_not_responding
            is NoConnectivityException -> R.string.error_no_internet
            is IOException -> R.string.error_bad_response
            else -> R.string.error_not_found_item
        })
    }

    protected fun setUpdateStatus(status : Boolean) {
        _updateStatus.value = status
    }

    protected open fun filters() : List<FilterItem<V>> = emptyList()
    open fun filterMenu() : Int? = null

    val filterSetItemListener = {item : MenuItem -> setFilter(item)}
    private fun setFilter(item : MenuItem) : Boolean {
        currentFilter = item.itemId
        return true
    }

    protected open fun filterData(list : List<V>) : List<V> {
        return filters.find{it.id == currentFilter}?.action?.invoke(list) ?: list
    }

    protected fun invalidateData() {_dataList.value?.let {_dataList.value = it}}
}