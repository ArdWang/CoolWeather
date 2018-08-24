package com.clw.data.respository

import com.clw.base.data.net.RetrofitFactory
import com.clw.data.api.NewsApi
import com.clw.data.protocol.news.LatestResp
import com.clw.data.protocol.news.NewsConResp
import com.clw.data.protocol.news.NewsResp
import io.reactivex.Observable
import retrofit2.http.Path


class NewsRepository {

    //获取新闻
    fun getNews(params:MutableMap<String,String>): Observable<NewsResp>{
        return RetrofitFactory.instance.create(NewsApi::class.java).getNews(params)
    }

    //获取最新闻
    fun getLatest():Observable<LatestResp>{
        return RetrofitFactory.instance.create(NewsApi::class.java).getLatest()
    }

    //获取新闻内容
    fun getNewsCon(newsId:Long):Observable<NewsConResp>{
        return RetrofitFactory.instance.create(NewsApi::class.java).getNewsCon(newsId)
    }
}