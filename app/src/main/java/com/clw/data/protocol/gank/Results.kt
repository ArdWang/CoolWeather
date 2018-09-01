package com.clw.data.protocol.gank

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Results(
        val _id:String,
        val createdAt:String,
        val desc:String,
        val publishedAt:String,
        val source:String,
        val type:String,
        val url:String,
        val used:Boolean,
        val who:String
): Parcelable