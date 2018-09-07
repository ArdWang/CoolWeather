package com.clw.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.clw.R
import com.clw.base.ui.activity.BaseActivity
import com.clw.common.AppManager
import com.clw.ext.onClick
import com.clw.ui.adapter.like.ImageVpAdapter
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_newsview.*


class NewsViewActivity :BaseActivity(){

    private lateinit var mAdapter: ImageVpAdapter

    private var pos:Int = 0

    private var content:String?=null

    private var title:String?=null

    private var imgList:MutableList<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this).barColor(R.color.colorBlack).fitsSystemWindows(true) .init()
        setContentView(R.layout.activity_newsview)

        initData()
        initView()

    }


    private fun initData() {
        content = intent.getStringExtra("content")
        title = intent.getStringExtra("title")
        imgList = intent.getStringArrayListExtra("imgList")
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        if(imgList!!.size>0&&imgList!=null){
            mAdapter = ImageVpAdapter(imgList,this)
            mNewsViewPager!!.adapter = mAdapter
            mNewsViewPager!!.setCurrentItem(pos,false)

            mNewsPage.text = (pos+1).toString() + "/" + imgList!!.size

            if(title!=null){
                mNewsTle.text =title
            }

            if(content!=null){
                mNewsCon.text = content
            }

            mNewsViewPager!!.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pos = position
                    mNewsPage.text = (pos+1).toString() + "/" + imgList!!.size
                }
            })
        }

        mNewsIv.onClick {
            AppManager.instance.finishActivity(this)
        }

    }



    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy() //必须调用该方法，防止内存泄漏
    }


}