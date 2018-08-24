package com.clw.ui.adapter.news

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


/**
 * Created by rnd on 2018/1/30.
 *
 */
class NewsVpAdapter(fm: FragmentManager, private val mFragmentList:MutableList<Fragment>,
                    private val mTitleList: MutableList<String>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        //Log.i("Class Nmae is:",mFragmentList[position].childFragmentManager.toString())
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    /**
     * 这个用来显示当前的标题 如果不加 就没有标题的显示
     * @param position
     * @return
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }
}
