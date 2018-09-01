package com.clw.presenter

import com.clw.base.presenter.BasePresenter
import com.clw.base.retrofit.BaseCallback
import com.clw.base.rx.BaseObserver
import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.weather.Weather
import com.clw.ext.excute
import com.clw.presenter.view.WeatherView
import com.clw.service.impl.WeatherServiceImpl
import retrofit2.Call
import retrofit2.Response

@Suppress("SENSELESS_COMPARISON")
class WeatherPresenter :BasePresenter<WeatherView>(){

    private  var weatherService=WeatherServiceImpl()

    /**
     * 获取省级列表
     */
    fun getProvince(){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        weatherService.getProvince().enqueue(object : BaseCallback<MutableList<Provinces>>(mView) {
            override fun onResponse(call: Call<MutableList<Provinces>>, response: Response<MutableList<Provinces>>) {
                mView.hideLoading()
                mView.onGetProvinces(response.body()!!)
            }
        })
    }

    /**
     * 获取市级列表
     */
    fun getCities(pid:Int){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        weatherService.getCities(pid).enqueue(object :BaseCallback<MutableList<Cities>>(mView){
            override fun onResponse(call: Call<MutableList<Cities>>, response: Response<MutableList<Cities>>) {
                mView.hideLoading()
                mView.onGetCities(response.body()!!)
            }
        })
    }

    /**
     * 获取县级列表
     */
    fun getCounties(pid:Int,cid:Int){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        weatherService.getCounties(pid,cid).enqueue(object :BaseCallback<MutableList<Counties>>(mView){
            override fun onResponse(call: Call<MutableList<Counties>>, response: Response<MutableList<Counties>>) {
                mView.hideLoading()
                mView.onGetCounties(response.body()!!)
            }
        })
    }


    /**
     * 获取实时的天气
     */
    fun getWeather(params: MutableMap<String,String>){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        weatherService.getWeather(params).excute(object :BaseObserver<Weather>(mView){
            override fun onNext(t: Weather) {
                mView.onGetWeather(t.HeWeather6)
            }
        },lifeProvider)

    }

    /**
     * 获取天气的指数
     */
    fun getWeatherAir(params: MutableMap<String, String>){
        if(!checkNetWork()){
            return
        }

        mView.showLoading()

        weatherService.getWeatherAir(params).excute(object :BaseObserver<Weather>(mView){
            override fun onNext(t: Weather) {
                if(t.HeWeather6[0].air_now_city!=null) {
                    mView.onGetWeatherAir(t.HeWeather6[0].air_now_city)
                }
            }
        },lifeProvider)

    }



}



