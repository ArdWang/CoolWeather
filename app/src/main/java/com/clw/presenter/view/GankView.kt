package com.clw.presenter.view

import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.gank.Results

interface GankView :BaseView{
    fun onGetGank(result:MutableList<Results>)
}