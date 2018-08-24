package com.clw.data.protocol.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Data(
        val id:String,
        val likeCount:String,
        val url:String,
        val posterScreenName:String,
        val shareCount:String,
        val content:String,
        val imageUrls:MutableList<String>,
        val posterId:String,
        val tags:String,
        val commentCount:String,
        val publishDate:Long,
        val publishDateStr:String,
        val title:String
): Parcelable