package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.news.NewsResp
import com.clw.ext.excute
import com.clw.presenter.view.NewsView
import com.clw.service.impl.NewsServiceImpl


class NewsPresenter:BasePresenter<NewsView>() {

    private var newsService = NewsServiceImpl()

    fun getNews(params:MutableMap<String,String>){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        newsService.getNews(params).excute(object :BaseObserver<NewsResp>(mView){
            override fun onNext(t: NewsResp) {
                mView.getNews(t.data)
            }
        },lifeProvider)
    }

}