package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.news.LatestResp
import com.clw.ext.excute
import com.clw.presenter.view.LatestView
import com.clw.service.impl.NewsServiceImpl

class LatestPresenter:BasePresenter<LatestView>() {

    private var newsService = NewsServiceImpl()

    /**
     * 获取当前的新闻值
     */
    fun getLatest(){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        newsService.getLatest().excute(object :BaseObserver<LatestResp>(mView){
            override fun onNext(t: LatestResp) {
                mView.getLatest(t)
            }
        },lifeProvider)
    }
}