package com.clw.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.clw.R
import kotlinx.android.synthetic.main.activity_demo.*
import android.webkit.WebSettings



class DemoActivity :AppCompatActivity(){

    private var webUrl:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        initData()

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initData(){
        webUrl = intent.getStringExtra("webUrl")

        mWebView.loadUrl(webUrl)
        //mWebView.addJavascriptInterface(this, "android")//添加js监听 这样html就能调用客户端
        //mWebView.setWebChromeClient(webChromeClient)
        //mWebView.setWebViewClient(webViewClient)

        val webSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)//允许使用js

        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true)
        webSettings.setBuiltInZoomControls(true)
    }

}