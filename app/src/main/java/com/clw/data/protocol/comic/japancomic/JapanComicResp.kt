package com.clw.data.protocol.comic.japancomic

data class JapanComicResp(
        val code:String,
        val message:String,
        val more:String,
        val japanComicList:MutableList<JapanComic>
)