package com.clw.data.protocol.comic.banner

data class BannerResp(
        val code:String,
        val message:String,
        val more:String,
        val bannerList:MutableList<Banner>
)