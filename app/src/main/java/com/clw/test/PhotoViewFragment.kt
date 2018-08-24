package com.clw.test

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.ext.loadUrl
import com.clw.ui.activity.PhotoViewActivity
import kotlinx.android.synthetic.main.fragment_photoview.*

class PhotoViewFragment :Fragment(){

    private var pva:PhotoViewActivity?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_photoview,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /**
     * 创建view
     */
    private fun initView() {
        pva = activity as PhotoViewActivity
        val qq = arguments!!.getInt("position",-1)
        mPhotoView.loadUrl(pva!!.result[qq])
    }
}