package com.clw.ui.adapter.like

import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.clw.ext.loadUrl
import uk.co.senab.photoview.PhotoView

class ImageVpAdapter(private val imageUrls:MutableList<String>?,private val activity:AppCompatActivity) :PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val url = imageUrls!![position]
        val photoView = PhotoView(activity)

        photoView.loadUrl(url)
        container.addView(photoView)
        photoView.setOnClickListener{
            activity.finish()
        }
        return photoView
    }


    override fun isViewFromObject(view: View, any: Any): Boolean {
        return view === any
    }

    override fun getCount(): Int {
       return imageUrls!!.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
        container.removeView(any as View)
    }

    override fun getItemPosition(any: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}