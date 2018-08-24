package com.clw.presenter.view

import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.comic.banner.Banner
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp


interface ComicView :BaseView{

    fun onGetComicBanner(result:MutableList<Banner>)

    fun onGetRecommend(result: RecommendResp)

    fun onGetDayComic(result: DayComicResp)

    fun onGetUpdateComic(result: UpdateComicResp)

    fun onGetNewComic(result: NewComicResp)

    fun onGetJapanComic(result: JapanComicResp)



}