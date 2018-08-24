package com.clw.test

import com.clw.common.BaseConstant
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactorym private constructor(){
    /**
        单例实现
     */
    companion object {
        val instance: RetrofitFactorym by lazy { RetrofitFactorym() }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit

    //初始化
    init {
        //通用拦截
        interceptor = Interceptor {
            chain -> val request = chain.request()
                .newBuilder()
                .addHeader("Content_Type","application/json")
                .addHeader("charset","UTF-8")
                .addHeader("token","111")
                .build()

            //获取原始的originalRequest
            val originalRequest = chain.request()
            //获取老的url
            val oldUrl = originalRequest.url()
            //获取originalRequest的创建者builder
            val builder = originalRequest.newBuilder()
            //获取头信息的集合如：manage,mdffx
            //val urlnameList = originalRequest.headers("urlname")

            //从request中获取headers，通过给定的键url_name
            val headerValues = request.headers("UrlName")
            if (headerValues != null && headerValues.size > 0) {
                //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
                builder.removeHeader("UrlName")
                //匹配获得新的BaseUrl
                val headerValue = headerValues[0]
                val newBaseUrl: HttpUrl?
                newBaseUrl = when (headerValue) {
                    "city" -> HttpUrl.parse(BaseConstant.APP_CITY_SERVICE_ADDRE)
                    "weather" -> HttpUrl.parse(BaseConstant.APP_WEATHER_SERVICE_ADDRE)
                    else -> oldUrl
                }

                //从request中获取原有的HttpUrl实例oldHttpUrl
                val oldHttpUrl = request.url()
                //重建新的HttpUrl，修改需要修改的url部分
                val newFullUrl = oldHttpUrl
                        .newBuilder()
                        .scheme(newBaseUrl!!.scheme())
                        .host(newBaseUrl.host())
                        .port(newBaseUrl.port())
                        .build()
                //重建这个request，通过builder.url(newFullUrl).build()；
                //然后返回一个response至此结束修改
                chain.proceed(builder.url(newFullUrl).build())
            } else {
                chain.proceed(request)
            }
        }

        //Retrofit实例化
        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.APP_CITY_SERVICE_ADDRE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                .build()
    }


    /**
        OKHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(initLogInterceptor())
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
    }

    /**
        日志拦截器
     */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    /**
        具体服务实例化
     */
    fun <T> create(service:Class<T>):T{
        return retrofit.create(service)
    }
}