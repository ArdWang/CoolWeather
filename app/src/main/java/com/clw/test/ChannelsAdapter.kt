package com.clw.test

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Dangelo on 2016/9/24.
 */
class ChannelsAdapter(fm: FragmentManager, private val titles: List<String>, private val newsFragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return newsFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return newsFragmentList.size
    }
}
