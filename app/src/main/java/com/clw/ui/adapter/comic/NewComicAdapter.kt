package com.clw.ui.adapter.comic

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.comic.newcomic.NewComic
import com.clw.ext.loadUrl
import kotlinx.android.synthetic.main.layout_comic_new_item.view.*

class NewComicAdapter(context: Context):BaseRecyclerViewAdapter<NewComic,NewComicAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_comic_new_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]

        holder.itemView.mNewComicImg.loadUrl(model.cover)
        holder.itemView.mNewComicTitle.text = model.title
        holder.itemView.mNewComicDes.text = model.category
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}