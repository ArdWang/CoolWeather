package com.clw.data.respository

import com.clw.base.data.net.RetrofitFactory
import com.clw.data.api.GankApi
import com.clw.data.protocol.gank.Gank
import io.reactivex.Observable


class GankRepository {

    /**
     * 获取干活福利数据
     */
    fun getGank(unit:Int,page:Int):Observable<Gank>{
        return RetrofitFactory.instance.create(GankApi::class.java).getGank(unit,page)
    }

}