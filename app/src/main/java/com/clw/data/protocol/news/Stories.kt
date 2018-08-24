package com.clw.data.protocol.news

data class Stories(
        val images: MutableList<String>,
        val type:Int,
        val id:Long,
        val ga_prefix:String,
        val title:String
)