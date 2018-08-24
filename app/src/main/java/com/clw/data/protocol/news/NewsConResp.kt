package com.clw.data.protocol.news

data class NewsConResp(
        val body:String,
        val image_source:String,
        val title:String,
        val image:String,
        val share_url:String,
        val ga_prefix:String,
        val images:MutableList<String>,
        val type:Int,
        val id:Long
)