package com.clw.base.presenter

import com.clw.base.presenter.view.BaseView
import com.clw.common.MainApplication.Companion.context
import com.clw.utils.NetWorkUtils
import com.trello.rxlifecycle2.LifecycleProvider

open class BasePresenter <T:BaseView>{

    lateinit var mView:T

    lateinit var lifeProvider:LifecycleProvider<*>

    fun checkNetWork():Boolean{
        if (NetWorkUtils.isNetWorkAvailable(context)) {
            return true
        }
        mView.onError("网络不可用")
        return false
    }
}