package com.clw.service.impl

import com.clw.data.protocol.gank.Gank
import com.clw.data.respository.GankRepository
import com.clw.ext.convert
import com.clw.service.GankService
import io.reactivex.Observable

class GankServiceImpl:GankService {

    private var repository = GankRepository()

    override fun getGank(unit: Int, page: Int): Observable<Gank> {
        return repository.getGank(unit,page).convert()
    }
}