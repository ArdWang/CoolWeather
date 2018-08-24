package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.news.NewsConResp
import com.clw.ext.excute
import com.clw.presenter.view.NewsConView
import com.clw.service.impl.NewsServiceImpl

@Suppress("SENSELESS_COMPARISON")
class NewsConPresenter:BasePresenter<NewsConView>() {

    private var newsService = NewsServiceImpl()

    fun getNewsCon(newsId:Long){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        newsService.getNewsCon(newsId).excute(object :BaseObserver<NewsConResp>(mView){
            override fun onNext(t: NewsConResp) {
                if(t!=null){
                    mView.getNewsCon(t)
                }
            }
        },lifeProvider)
    }

}