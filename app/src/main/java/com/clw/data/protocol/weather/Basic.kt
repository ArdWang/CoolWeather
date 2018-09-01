package com.clw.data.protocol.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Basic(
        val cid:String,
        val location:String,
        val parent_city:String,
        val admin_area:String,
        val cnty:String,
        val lat:String,
        val lon:String,
        val tz:String
        ): Parcelable