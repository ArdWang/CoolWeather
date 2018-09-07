package com.clw.ui.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.fragment.BaseFragment
import com.clw.ui.adapter.news.NewsVpAdapter
import com.clw.ui.fragment.news.NewsImgFragment
import com.clw.ui.fragment.news.NewsLatestFragment
import kotlinx.android.synthetic.main.fragment_all_news.*


@Suppress("DEPRECATION")
class NewsAllFragment :BaseFragment(){

    private val mLatestFragment by lazy { NewsLatestFragment() }

    private val mImgFragment by lazy { NewsImgFragment() }

    //Fragment栈管理
    private val mFragmentList:MutableList<Fragment> = mutableListOf()

    //标题添加到List里面
    private val mTitleList:MutableList<String> = mutableListOf()

    private lateinit var mAdapter:NewsVpAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_all_news,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        //添加页卡标题
        mTitleList.add("最新新闻")
        mTitleList.add("图片新闻")
        //添加Fragment
        mFragmentList.add(mLatestFragment)
        mFragmentList.add(mImgFragment)

        mNewsTab.tabMode = TabLayout.MODE_FIXED
        mAdapter = NewsVpAdapter(childFragmentManager, mFragmentList, mTitleList)
        mNewsVp.adapter = mAdapter
        mNewsTab.setupWithViewPager(mNewsVp)

    }
}