package com.clw.data.protocol.weather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Update(
        val loc:String,
        val utc:String
): Parcelable