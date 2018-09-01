package com.clw.ui.adapter.like

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.gank.Results
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import kotlinx.android.synthetic.main.layout_like_item.view.*


class GankAdapter(context:Context):BaseRecyclerViewAdapter<Results,GankAdapter.ViewHolder>(context) {

    //ItemClick事件
    var mOnClickListener: OnClickListener<Results>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_like_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]

        holder.itemView.mCardView.onClick {
            mOnClickListener!!.onCardViewClick(model,position)
        }

        holder.itemView.mCardImg.loadUrl(model.url)
        holder.itemView.mCardTxt.text = "发布人: "+model.who
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view)

    /**
    ItemClick事件声明
     */
    interface OnClickListener<in T>{
        fun onCardViewClick(item:T,position: Int)
    }
}