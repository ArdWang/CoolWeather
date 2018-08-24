package com.clw.presenter.view


import com.clw.base.presenter.view.BaseView
import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.weather.AirNowCity
import com.clw.data.protocol.weather.HeWeather6

interface WeatherView:BaseView {

    fun onGetProvinces(result: MutableList<Provinces>)

    fun onGetCities(result: MutableList<Cities>)

    fun onGetCounties(result: MutableList<Counties>)

    fun onGetWeather(result: MutableList<HeWeather6>)

    fun onGetWeatherAir(result: AirNowCity)

}