package com.clw.presenter.view

import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.news.NewsConResp
import com.clw.data.protocol.news.NewsResp

interface NewsConView :BaseView{
    fun getNewsCon(result: NewsConResp)
}