package com.clw.service

import com.clw.data.protocol.gank.Gank
import io.reactivex.Observable

interface GankService {
    //获取干活福利数据
    fun getGank(unit:Int,page:Int):Observable<Gank>
}