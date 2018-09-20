package com.clw.ui.fragment.sideslip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.fragment.BaseFragment
import com.clw.ext.onClick
import kotlinx.android.synthetic.main.fragment_feedback.*

/**
 * 反馈消息
 */
class FeedBackFragment :BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_feedback,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
    }

    private fun initData(){

        mFeedbackBtn.onClick {
            //发送反馈

        }
    }
}