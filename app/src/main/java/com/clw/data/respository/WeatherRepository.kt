package com.clw.data.respository

import com.clw.base.data.net.RetrofitFactory
import com.clw.data.api.GankApi
import com.clw.data.api.WeatherApi
import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.gank.Gank
import com.clw.data.protocol.weather.Weather
import io.reactivex.Observable
import retrofit2.Call

class WeatherRepository{

    /**
     * 获取省份
     */
    fun getProvince():Call<MutableList<Provinces>>{
        return RetrofitFactory.instance.create(WeatherApi::class.java).getProvince()
    }


    /**
     * 获取市级
     */
    fun getCities(pid:Int):Call<MutableList<Cities>>{
        return RetrofitFactory.instance.create(WeatherApi::class.java).getCities(pid)
    }

    /**
     * 获取县级
     */
    fun getCounties(pid:Int,cid:Int):Call<MutableList<Counties>>{
        return RetrofitFactory.instance.create(WeatherApi::class.java).getCounties(pid,cid)
    }

    /**
     * 获取实时的天气
     */
    fun getWeather(params:MutableMap<String,String>):Observable<Weather>{
        return RetrofitFactory.instance.create(WeatherApi::class.java).getWeather(params)
    }

    /**
     * 获取实时天气的质量
     */
    fun getWeatherAir(params:MutableMap<String,String>):Observable<Weather>{
        return RetrofitFactory.instance.create(WeatherApi::class.java).getWeatherAir(params)
    }

    /**
     * 获取干活福利数据
     */
    fun getGank(unit:Int,page:Int):Observable<Gank>{
        return RetrofitFactory.instance.create(GankApi::class.java).getGank(unit,page)
    }

}