package com.clw.ui.adapter.news

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.news.Data
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import com.clw.ext.setVisible
import kotlinx.android.synthetic.main.layout_news_item.view.*
import org.jetbrains.anko.dip



@Suppress("SENSELESS_COMPARISON")
class NewsImgAdapter(context: Context):BaseRecyclerViewAdapter<Data,NewsImgAdapter.ViewHolder>(context) {

    //ItemClick事件
    var mOnClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_news_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val data = dataList[position]
        holder.itemView.mNewsTitle.text = data.title
        //DateUtil.getLongToString(data.publishDate,"yyyy/MM/dd HH:mm:ss")
        holder.itemView.mNewsTime.text = data.publishDateStr
        holder.itemView.mNewsContent.text = data.content

        holder.itemView.mMultiNewsView.setVisible(true)
        holder.itemView.mMultiNewsView.removeAllViews()

        holder.itemView.mMultiNewsView.onClick {
            mOnClickListener?.onLdViewClick(data,position)
        }

        //动态的添加
        if(data.imageUrls!=null&&data.imageUrls.size>0) {
            var i=0
            for (url in data.imageUrls) {
                val imageView = ImageView(mContext)
                val lp = ViewGroup.MarginLayoutParams(mContext.dip(100.0f), mContext.dip(100.0f))

                lp.marginStart = mContext.dip(10f)
                lp.marginEnd = mContext.dip(10f)
                lp.topMargin = mContext.dip(10f)
                lp.bottomMargin = mContext.dip(10f)

                imageView.layoutParams = lp
                imageView.loadUrl(url)
                //添加子View
                holder.itemView.mMultiNewsView.addView(imageView)
                i++
                if(i>=3){
                    return
                }
            }
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)


    interface OnClickListener{
        fun onLdViewClick(item:Data,position: Int)
    }
}