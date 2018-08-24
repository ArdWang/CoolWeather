package com.clw.ui.adapter.weather

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.clw.R


open class CityDialogAdapter constructor(val context:Context,private val citylist:MutableList<String>):BaseAdapter(){

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = View.inflate(context, R.layout.layout_city_dialog_item, null)
        val tvCarId = view.findViewById(R.id.mCityTv) as TextView
        tvCarId.text = citylist[position]
        return view
    }

    override fun getItem(position: Int): Any {
        return citylist[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return citylist.size
    }

}