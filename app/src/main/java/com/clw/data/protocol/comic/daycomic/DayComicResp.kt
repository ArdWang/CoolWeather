package com.clw.data.protocol.comic.daycomic

data class DayComicResp(
       val code:String,
       val message:String,
       val more:String,
       val dayComic: DayComic
)