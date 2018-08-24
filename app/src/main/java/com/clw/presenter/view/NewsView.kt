package com.clw.presenter.view

import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.news.Data

interface NewsView :BaseView{
    fun getNews(result: MutableList<Data>)
}