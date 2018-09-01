package com.clw.base.ui.activity

import android.os.Bundle
import com.clw.base.presenter.BasePresenter
import com.clw.base.presenter.view.BaseView
import com.clw.utils.PMUtils
import com.clw.widgets.ProgressLoading

abstract class BaseMvpActivity<T:BasePresenter<*>>:BaseActivity(),BaseView {

    lateinit var mPresenter: T

    lateinit var progressLoading: ProgressLoading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        setContentView(getLayoutResID())
        initView()
        mPresenter = PMUtils.getT(this,0)!!
        progressLoading = ProgressLoading.create(this)
    }


    abstract fun getLayoutResID():Int

    protected open fun initView() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun onError(text: String) {

    }

}