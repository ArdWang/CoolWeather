package com.clw.service

import com.clw.data.protocol.news.LatestResp
import com.clw.data.protocol.news.NewsConResp
import com.clw.data.protocol.news.NewsResp
import io.reactivex.Observable


interface NewsService {

    fun getNews(params:MutableMap<String,String>): Observable<NewsResp>

    fun getLatest():Observable<LatestResp>

    fun getNewsCon(newsId:Long):Observable<NewsConResp>
}