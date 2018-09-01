package com.clw.data.api


import com.clw.data.protocol.gank.Gank
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GankApi {
    /**
     * 获取干活福利数据
     */
    @Headers("UrlName:gank")
    @GET("api/data/福利/{unit}/{page}")
    fun getGank(@Path("unit") unit:Int,@Path("page") page:Int): Observable<Gank>


}