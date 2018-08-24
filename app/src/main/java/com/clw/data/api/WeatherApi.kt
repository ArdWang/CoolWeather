package com.clw.data.api

import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.weather.Weather
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap


interface WeatherApi {
    /**
     * 获取省级列表
     */
    @Headers("UrlName:city")
    @GET("api/china")
    fun getProvince():Call<MutableList<Provinces>>

    /**
     * 获取市级列表
     */
    @Headers("UrlName:city")
    @GET("api/china/{pid}")
    fun getCities(@Path("pid") pid:Int):Call<MutableList<Cities>>

    /**
     * 获取县级列表
     */
    @Headers("UrlName:city")
    @GET("api/china/{pid}/{cid}")
    fun getCounties(@Path("pid") pid:Int, @Path("cid") cid:Int):Call<MutableList<Counties>>


    /**
     * 获取实时天气
     */
    @Headers("UrlName:weather")
    @GET("s6/weather")
    fun getWeather(@QueryMap params:MutableMap<String,String>):Observable<Weather>

    /**
     * 获取天气指数
     */
    @Headers("UrlName:weather")
    @GET("s6/air")
    fun getWeatherAir(@QueryMap params:MutableMap<String,String>):Observable<Weather>


}