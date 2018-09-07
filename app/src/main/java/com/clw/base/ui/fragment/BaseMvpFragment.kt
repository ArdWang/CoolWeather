package com.clw.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.base.presenter.BasePresenter
import com.clw.base.presenter.view.BaseView
import com.clw.utils.PMUtils
import com.clw.widgets.ProgressLoading
import org.jetbrains.anko.support.v4.toast

abstract class BaseMvpFragment<T: BasePresenter<*>> :BaseFragment(), BaseView{

    lateinit var mPresenter: T

    private lateinit var progressLoading: ProgressLoading

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        init()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun init() {
        mPresenter = PMUtils.getT(this,0)!!
        progressLoading = ProgressLoading.create(context!!)
    }



    override fun showLoading() {
        progressLoading.showLoading()
    }

    override fun hideLoading() {
        progressLoading.hideLoading()
    }

    override fun onError(text: String) {
        toast(text)
    }
}