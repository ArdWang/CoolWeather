package com.clw.widgets

import android.content.Context
import android.util.AttributeSet
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.clw.R

class BottomNavBar @JvmOverloads constructor(context:Context, attrs:AttributeSet?=null,defStyleAttr:Int=0)
    :BottomNavigationBar(context,attrs,defStyleAttr){

    //购物车Tab 标签
    // private val mCartBadge:TextBadgeItem
    //private val mMsgBadge:ShapeBadgeItem

    init {
        //discover
        val wifiItem = BottomNavigationItem(R.drawable.search_p,resources.getString(R.string.bnb_news))
                .setInactiveIconResource(R.drawable.search)
                .setActiveColorResource(R.color.colorBlue)
                .setInActiveColorResource(R.color.colorQBlue)

        //weather
        val bleItem = BottomNavigationItem(R.drawable.weather_p,resources.getString(R.string.bnb_weather))
                .setInactiveIconResource(R.drawable.weather)
                .setActiveColorResource(R.color.colorBlue)
                .setInActiveColorResource(R.color.colorQBlue)

        //福利
        val meItem = BottomNavigationItem(R.drawable.like_p,resources.getString(R.string.bnb_welfare))
                .setInactiveIconResource(R.drawable.like)
                .setActiveColorResource(R.color.colorBlue)
                .setInActiveColorResource(R.color.colorQBlue)


        //mCartBadge = TextBadgeItem()

        //设置底部导航栏模式以及样式
        setMode(BottomNavigationBar.MODE_FIXED)
        setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        setBarBackgroundColor(R.color.colorBTM)

        //添加Tab
        addItem(wifiItem)
                .addItem(bleItem)
                .addItem(meItem)
                .setFirstSelectedPosition(0)
                .initialise()
    }
}