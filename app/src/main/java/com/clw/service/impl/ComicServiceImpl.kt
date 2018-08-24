package com.clw.service.impl

import com.clw.data.protocol.comic.banner.BannerResp
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import com.clw.data.respository.ComicRepository
import com.clw.ext.convert
import com.clw.service.ComicService
import io.reactivex.Observable

class ComicServiceImpl:ComicService {

    private var repository = ComicRepository()

    override fun getComicBanner(): Observable<BannerResp> {
        return repository.getComicBanner().convert()
    }

    override fun getRecommend(): Observable<RecommendResp> {
        return repository.getRecommend().convert()
    }

    override fun getDayComic(): Observable<DayComicResp> {
        return repository.getDayComic().convert()
    }

    override fun getDayUpdate(): Observable<UpdateComicResp> {
        return repository.getDayUpdate().convert()
    }

    override fun getNewComic(): Observable<NewComicResp> {
        return repository.getNewComic().convert()
    }

    override fun getJapanComic(): Observable<JapanComicResp> {
        return repository.getJapanComic().convert()
    }




}