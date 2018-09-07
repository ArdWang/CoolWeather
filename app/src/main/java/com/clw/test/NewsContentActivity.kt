package com.clw.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import com.clw.R
import com.clw.base.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_news_content.*

class NewsContentActivity :BaseActivity(){

    private var url:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)


        initData()
        initView()
    }

    private fun initData(){
        url = intent.getStringExtra("url")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView() {
        mNewsConWeb.settings.defaultTextEncodingName = "UTF-8"
        //webView.getSettings().setP
        //支持javascript
        mNewsConWeb.settings.javaScriptEnabled = true
        //自适应屏幕
        mNewsConWeb.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mNewsConWeb.settings.loadWithOverviewMode = true
        mNewsConWeb.settings.domStorageEnabled = true
        mNewsConWeb.loadUrl(url)
    }
}