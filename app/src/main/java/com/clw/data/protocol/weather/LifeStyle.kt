package com.clw.data.protocol.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class LifeStyle(
        val type:String,
        val brf:String,
        val txt:String,
        var tip:String
): Parcelable