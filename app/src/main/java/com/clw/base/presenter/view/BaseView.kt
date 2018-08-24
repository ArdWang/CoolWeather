package com.clw.base.presenter.view

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(text:String)
}