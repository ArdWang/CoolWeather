package com.clw.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import com.clw.R
import com.clw.base.ui.activity.BaseMvpActivity
import com.clw.common.ProviderConstant
import com.clw.data.protocol.news.NewsConResp
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import com.clw.presenter.NewsConPresenter
import com.clw.presenter.view.NewsConView
import com.clw.utils.AppSPrefsUtils
import kotlinx.android.synthetic.main.activity_news_content.*


@Suppress("DEPRECATION")
class NewsConActivity :BaseMvpActivity<NewsConPresenter>(),NewsConView{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }


    override fun getLayoutResID(): Int {
        return R.layout.activity_news_content
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        mNewsConWeb.settings.defaultTextEncodingName = "UTF-8"
        //支持javascript
        mNewsConWeb.settings.javaScriptEnabled = true
        //自适应屏幕
        mNewsConWeb.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        mNewsConWeb.settings.loadWithOverviewMode = true
        mNewsConWeb.settings.domStorageEnabled = true


        mNewsConBack.onClick {
            finish()
        }
    }


    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

        val newsId = intent.getLongExtra("newsId",0)
        mPresenter.getNewsCon(newsId)

        val theme = AppSPrefsUtils.getString("theme")

        if(theme.isEmpty()){
            mNewsConWeb.setBackgroundColor(resources.getColor(R.color.colorWhite))
        }

        if(theme==ProviderConstant.NIGHT_THEME){
            mNewsConWeb.setBackgroundColor(resources.getColor(R.color.colorNight))
        }else{
            mNewsConWeb.setBackgroundColor(resources.getColor(R.color.colorWhite))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getNewsCon(result: NewsConResp) {

        if(result.title.length>5){
            mNewsConTle.text = result.title.substring(0,5)+"..."
        }else{
            mNewsConTle.text = result.title
        }

        mNewsConImg.loadUrl(result.image)

        mNewsConWeb.loadDataWithBaseURL(null, result.body, "text/html", "utf-8", null)

    }

}