package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.gank.Gank
import com.clw.ext.excute
import com.clw.presenter.view.GankView
import com.clw.service.impl.GankServiceImpl


class GankPresenter:BasePresenter<GankView>() {

    private var gankService = GankServiceImpl()

    fun getGank(unit:Int,page:Int){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        gankService.getGank(unit,page).excute(object : BaseObserver<Gank>(mView){
            override fun onNext(t: Gank) {
                mView.onGetGank(t.results)
            }
        },lifeProvider)
    }

}