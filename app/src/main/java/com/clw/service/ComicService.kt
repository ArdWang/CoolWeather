package com.clw.service

import com.clw.data.protocol.comic.banner.BannerResp
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import io.reactivex.Observable

interface ComicService {
    //获取动漫Banner接口
    fun getComicBanner(): Observable<BannerResp>

    //获取动漫Recommend接口
    fun getRecommend():Observable<RecommendResp>

    //获取每日一推的动漫
    fun getDayComic():Observable<DayComicResp>

    //获取每日更新
    fun getDayUpdate():Observable<UpdateComicResp>

    //获取最新上线作品
    fun getNewComic():Observable<NewComicResp>

    //获取日漫作品
    fun getJapanComic():Observable<JapanComicResp>
}