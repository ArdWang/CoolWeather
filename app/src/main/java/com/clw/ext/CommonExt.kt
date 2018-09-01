package com.clw.ext

import android.view.View
import android.widget.ImageView
import com.clw.base.rx.BaseFunction
import com.clw.utils.GlideUtils
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/**
    扩展点击事件，参数为方法
 */
fun View.onClick(method:() -> Unit): View {
    setOnClickListener { method() }
    return this
}

/**
 * RxJava2
 * 扩展Observable
 */
fun <T> Observable<T>.excute(observer: Observer<T>, lifeProvider: LifecycleProvider<*>){
    this.subscribeOn(Schedulers.io())
            .compose(lifeProvider.bindToLifecycle())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
}


/**
 * RxJava2
 * 扩展类型转换
 */
fun <T> Observable<T>.convert(): Observable<T> {
    return this.map(BaseFunction())
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String) {
    GlideUtils.loadUrlImage(context, url, this)
}








