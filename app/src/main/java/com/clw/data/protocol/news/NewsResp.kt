package com.clw.data.protocol.news

data class NewsResp(
        val dataType:String,
        val pageToken:String,
        val retcode:String,
        val hasNext:Boolean,
        val appCode:String,
        val data:MutableList<Data>
)