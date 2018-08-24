package com.clw.widgets

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.clw.R

class CityDialog constructor(context: Context, themeResId: Int): Dialog(context,themeResId){

    open class Builder(val context: Context) {

        private var mCityTitle: TextView? = null

        private var mCityList: ListView? = null

        private var mCityBtn:Button?=null

        private var title: String? = null

        //返回按钮选择
        fun getMCityBtn():Button?{
            return mCityBtn
        }

        fun getMCityList():ListView?{
            return mCityList
        }

        private fun getTitle(): String? {
            return title
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }


        @SuppressLint("InflateParams")
        fun create() :CityDialog{
            val inflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            // instantiate the dialog with the custom Theme
            val dialog = CityDialog(context, R.style.Dialog)
            val layout = inflater.inflate(R.layout.layout_dialog_city, null)
            //设置布局
            dialog.addContentView(layout, ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            mCityTitle = layout.findViewById(R.id.mCityTitle)
            mCityBtn = layout.findViewById(R.id.mCityBtn)
            mCityList = layout.findViewById(R.id.mCityList)

            //动态设置ListView的高度
            val dm = context.resources.displayMetrics
            //val width = dm.widthPixels
            val height = dm.heightPixels
            val params = mCityList!!.layoutParams
            params.height = (height/1.5).toInt()
            mCityList!!.layoutParams = params

            if(getTitle()!!.isNotEmpty()){
                mCityTitle!!.text = getTitle()
            }

            dialog.setContentView(layout)
            return dialog
        }
    }
}