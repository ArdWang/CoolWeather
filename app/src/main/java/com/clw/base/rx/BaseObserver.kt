package com.clw.base.rx

import com.clw.base.presenter.view.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

open class BaseObserver<T>(private val baseView:BaseView):Observer<T> {
    override fun onComplete() {
        baseView.hideLoading()
    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {

    }

    override fun onError(e: Throwable) {
        baseView.hideLoading()
    }


}