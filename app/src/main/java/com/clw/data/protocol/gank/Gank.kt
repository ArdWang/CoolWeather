package com.clw.data.protocol.gank

data class Gank(
        val error:Boolean,
        val results:MutableList<Results>
)