package com.object173.cfteventregestrator.feature.base.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addToComposite(composite : CompositeDisposable) {
    composite.add(this)
}