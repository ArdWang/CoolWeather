package com.clw.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.clw.R


/*
    Glide工具类
 */
object GlideUtils {
    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    /*
        当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    fun loadUrlImage(context: Context, url: String, imageView: ImageView){
        Glide.with(context).load(url).into(imageView)
    }
}
