package com.clw.ui.adapter.like

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import java.lang.IllegalArgumentException

class PhotoViewPager(context: Context, attrs: AttributeSet) :ViewPager(context,attrs){
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return try {
            super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            false
        }

    }
}