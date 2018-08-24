package com.clw.data.api

import com.clw.data.protocol.news.LatestResp
import com.clw.data.protocol.news.NewsConResp
import com.clw.data.protocol.news.NewsResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface NewsApi {
    /**
     * 获取新闻A接口
     */
    @Headers("UrlName:news")
    @GET("news/qihoo")
    fun getNews(@QueryMap params:MutableMap<String,String>):Observable<NewsResp>


    /**
     * 获取最新的新闻接口
     */
    @Headers("UrlName:latest")
    @GET("api/4/news/latest")
    fun getLatest():Observable<LatestResp>


    /**
     * 获取新闻内容接口
     */
    @Headers("UrlName:latest")
    @GET("api/4/news/{newsId}")
    fun getNewsCon(@Path("newsId") newsId:Long):Observable<NewsConResp>


}