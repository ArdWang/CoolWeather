package com.clw.data.protocol.comic.recommend

data class RecommendResp(
        val code:String,
        val message:String,
        val more:String,
        val comicList:MutableList<Recommend>
)