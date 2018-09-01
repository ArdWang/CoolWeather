package com.clw.service.impl

import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.gank.Gank
import com.clw.data.protocol.weather.Weather
import com.clw.data.respository.WeatherRepository
import com.clw.ext.convert
import com.clw.service.WeatherService
import io.reactivex.Observable
import retrofit2.Call

class WeatherServiceImpl:WeatherService {

    private var repository:WeatherRepository = WeatherRepository()


    override fun getProvince(): Call<MutableList<Provinces>> {

        return repository.getProvince()
    }

    override fun getCities(pid:Int): Call<MutableList<Cities>> {

        return repository.getCities(pid)
    }

    override fun getCounties(pid: Int, cid: Int): Call<MutableList<Counties>> {

        return repository.getCounties(pid,cid)
    }

    override fun getWeather(params: MutableMap<String, String>): Observable<Weather> {

        return repository.getWeather(params).convert()
    }

    override fun getWeatherAir(params: MutableMap<String, String>): Observable<Weather> {
        return repository.getWeatherAir(params).convert()
    }

    override fun getGank(unit: Int, page: Int): Observable<Gank> {
        return repository.getGank(unit,page)
    }
}