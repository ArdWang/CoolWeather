package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.comic.banner.BannerResp
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import com.clw.ext.excute
import com.clw.presenter.view.ComicView
import com.clw.service.impl.ComicServiceImpl

class ComicPresenter:BasePresenter<ComicView>() {

    private var comicService = ComicServiceImpl()

    /**
     * 获取动漫Banner
     */
    fun getComicBanner(){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        comicService.getComicBanner().excute(object :BaseObserver<BannerResp>(mView){
            override fun onNext(t: BannerResp) {
                mView.onGetComicBanner(t.bannerList)
            }
        },lifeProvider)
    }

    /**
     * 获取动漫推荐
     */
    fun getRecommend(){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        comicService.getRecommend().excute(object :BaseObserver<RecommendResp>(mView){
            override fun onNext(t: RecommendResp) {
                mView.onGetRecommend(t)
            }
        },lifeProvider)
    }


    /**
     * 获取每日推荐的动漫
     */
    fun getDayComic(){

        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        comicService.getDayComic().excute(object :BaseObserver<DayComicResp>(mView){
            override fun onNext(t: DayComicResp) {
                mView.onGetDayComic(t)
            }
        },lifeProvider)
    }

    /**
     * 获取每日更新
     */
    fun getDayUpdate(){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        comicService.getDayUpdate().excute(object :BaseObserver<UpdateComicResp>(mView){
            override fun onNext(t: UpdateComicResp) {
                mView.onGetUpdateComic(t)
            }
        },lifeProvider)

    }

    /**
     * 获取最新上线
     */
    fun getNewComic(){
        if(!checkNetWork()){
            return
        }
        mView.showLoading()

        comicService.getNewComic().excute(object :BaseObserver<NewComicResp>(mView){
            override fun onNext(t: NewComicResp) {
                mView.onGetNewComic(t)
            }
        },lifeProvider)
    }

    /**
     * 获取日漫推荐作品
     */
    fun getJapanComic(){
        if(!checkNetWork()){
            return
        }
        mView.showLoading()

        comicService.getJapanComic().excute(object :BaseObserver<JapanComicResp>(mView){
            override fun onNext(t: JapanComicResp) {
                mView.onGetJapanComic(t)
            }
        },lifeProvider)
    }



}