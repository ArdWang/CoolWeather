package com.clw.service

import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.gank.Gank
import com.clw.data.protocol.weather.Weather
import io.reactivex.Observable
import retrofit2.Call

interface WeatherService {

    //获取省份列表
    fun getProvince(): Call<MutableList<Provinces>>

    //获取市级列表
    fun getCities(pid:Int):Call<MutableList<Cities>>

    //获取县级列表
    fun getCounties(pid:Int,cid:Int):Call<MutableList<Counties>>

    //获取实时天气
    fun getWeather(params:MutableMap<String,String>):Observable<Weather>

    //获取空气质量
    fun getWeatherAir(params:MutableMap<String,String>):Observable<Weather>

    //获取干活福利数据
    fun getGank(unit:Int,page:Int):Observable<Gank>
}