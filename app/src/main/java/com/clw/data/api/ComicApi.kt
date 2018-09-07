package com.clw.data.api

import com.clw.data.protocol.comic.banner.BannerResp
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface ComicApi {

    /**
     * 获取动漫Banner的接口
     */
    @Headers("UrlName:comic")
    @GET("comic/getBanner")
    fun getComicBanner():Observable<BannerResp>

    /**
     * 获取无良推荐接口
     */
    @Headers("UrlName:comic")
    @GET("comic/getRecommend")
    fun getRecommend():Observable<RecommendResp>


    /**
     * 获取每日一推接口
     */
    @Headers("UrlName:comic")
    @GET("comic/getDayRecommend")
    fun getDayComic():Observable<DayComicResp>


    /**
     * 获取今日我更新的接口
     */
    @Headers("UrlName:comic")
    @GET("comic/getDayUpdate")
    fun getDayUpdate():Observable<UpdateComicResp>


    /**
     * 获取最新上线作品
     */
    @Headers("UrlName:comic")
    @GET("comic/getNewComic")
    fun getNewComic():Observable<NewComicResp>

    /**
     * 获取经典日漫
     */
    @Headers("UrlName:comic")
    @GET("comic/getJapanComic")
    fun getJapanComic():Observable<JapanComicResp>


}