package com.clw.data.protocol.news

data class LatestResp(
    val date:String,
    val stories:MutableList<Stories>,
    val top_stories:MutableList<TopStories>
)
