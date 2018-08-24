package com.clw.data.protocol.comic.updatecomic

data class UpdateComicResp(
        val code:String,
        val message:String,
        val more:String,
        val updateComicList:MutableList<UpdateComic>
)