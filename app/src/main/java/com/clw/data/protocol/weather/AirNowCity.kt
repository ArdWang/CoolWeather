package com.clw.data.protocol.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AirNowCity(
        val aqi:String,
        val qlty:String,
        val main:String,
        val pm25:String,
        val pm10:String,
        val no2:String,
        val so2:String,
        val co:String,
        val o3:String,
        val pub_time:String

): Parcelable