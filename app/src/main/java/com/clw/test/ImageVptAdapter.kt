package com.clw.test

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.clw.test.PhotoViewFragment

class ImageVptAdapter(var datalist:MutableList<String>, fm: FragmentManager, context: Context):FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = PhotoViewFragment()
        val bundle = Bundle()
        bundle.putInt("position",position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return  datalist.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return datalist[position]
    }

}