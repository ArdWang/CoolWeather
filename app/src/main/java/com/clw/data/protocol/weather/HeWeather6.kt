package com.clw.data.protocol.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class HeWeather6(
        val basic:Basic,
        val now:Now,
        val status:String,
        val update:Update,
        val daily_forecast:MutableList<DailyForecast>,
        val lifestyle:MutableList<LifeStyle>,
        val air_now_city:AirNowCity
)