package com.clw.data.respository

import com.clw.base.data.net.RetrofitFactory
import com.clw.data.api.ComicApi
import com.clw.data.protocol.comic.banner.BannerResp
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import io.reactivex.Observable

class ComicRepository {

    //获取动漫Banner
    fun getComicBanner(): Observable<BannerResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getComicBanner()
    }

    //获取动漫Recommend
    fun getRecommend():Observable<RecommendResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getRecommend()
    }

    //获取每日推荐的动漫
    fun getDayComic():Observable<DayComicResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getDayComic()
    }

    //获取当日更新
    fun getDayUpdate():Observable<UpdateComicResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getDayUpdate()
    }

    //获取最新上线的作品
    fun getNewComic():Observable<NewComicResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getNewComic()
    }

    //获取日漫作品
    fun getJapanComic():Observable<JapanComicResp>{
        return RetrofitFactory.instance.create(ComicApi::class.java).getJapanComic()
    }
}