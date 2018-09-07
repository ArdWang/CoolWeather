package com.clw.presenter.view

import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.news.LatestResp


interface LatestView :BaseView{
    fun getLatest(result: LatestResp)
}