package com.clw.service.impl

import com.clw.data.protocol.news.LatestResp
import com.clw.data.protocol.news.NewsConResp
import com.clw.data.protocol.news.NewsResp
import com.clw.data.respository.NewsRepository
import com.clw.ext.convert
import com.clw.service.NewsService
import io.reactivex.Observable
import java.util.*

class NewsServiceImpl :NewsService{

    private var repository = NewsRepository()

    /**
     * 获取新闻
     */
    override fun getNews(params:MutableMap<String,String>): Observable<NewsResp> {
        return repository.getNews(params).convert()
    }

    /**
     * 获取最新新闻
     */
    override fun getLatest(): Observable<LatestResp> {
        return repository.getLatest().convert()
    }

    /**
     * 获取新闻内容
     */
    override fun getNewsCon(newsId: Long): Observable<NewsConResp> {
        return repository.getNewsCon(newsId).convert()
    }

}