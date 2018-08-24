package com.clw.data.protocol.comic.newcomic

data class NewComicResp(
        val code:String,
        val message:String,
        val more:String,
        val newComicList:MutableList<NewComic>
)