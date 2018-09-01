package com.clw.base.retrofit

import com.clw.base.presenter.view.BaseView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BaseCallback<T>(private val baseView: BaseView):Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        baseView.hideLoading()
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {

    }

}