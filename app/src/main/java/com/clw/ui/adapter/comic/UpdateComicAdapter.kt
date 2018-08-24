package com.clw.ui.adapter.comic

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.comic.updatecomic.UpdateComic
import com.clw.ext.loadUrl
import kotlinx.android.synthetic.main.layout_comic_update_item.view.*

class UpdateComicAdapter(context: Context):BaseRecyclerViewAdapter<UpdateComic,UpdateComicAdapter.ViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_comic_update_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = dataList[position]

        holder.itemView.mUpdateImg.loadUrl(model.cover)
        holder.itemView.mUpdateTitle.text = model.title
        holder.itemView.mUpdateAuthor.text = model.author
        holder.itemView.mUpdateMuch.text = model.collect
        holder.itemView.mUpdateStar.text = "评分："+model.star
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}