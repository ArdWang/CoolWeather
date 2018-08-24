package com.clw.ui.adapter.news

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.RecycleViewItemData
import com.clw.data.protocol.news.StoriesInfo
import com.clw.data.protocol.news.TopStoriesInfo
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import com.clw.ui.adapter.weather.WeatherAdapter
import com.clw.widgets.BannerImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.layout_latest_item_banner.view.*
import kotlinx.android.synthetic.main.layout_latest_item_con.view.*


class LatestAdapter constructor(var context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //数据集合
    private var dataList: MutableList<RecycleViewItemData<*>> = mutableListOf()

    //ItemClick事件
    var mOnClickListener: OnClickListener? = null

    var mBannerClickListener:OnBannerClickListener? = null



    companion object {
        const val EMPTY_VIEW = 0
        const val NEWS_BANNER = 1
        const val NEWS_CONTENT = 2
    }


    /**
    设置数据
    Presenter处理过为null的情况，所以为不会为Null
     */
    fun setData(sources: MutableList<RecycleViewItemData<*>>) {
        dataList = sources
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view : View
        return when (viewType) {

            EMPTY_VIEW -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_empty_item, parent, false)
                EmptyViewHolder(view)
            }

            NEWS_BANNER ->{
                view = LayoutInflater.from(context).inflate(R.layout.layout_latest_item_banner, parent, false)
                BannerViewHolder(view)
            }

            else->{
                view = LayoutInflater.from(context).inflate(R.layout.layout_latest_item_con, parent, false)
                ContentViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            dataList.size==0 -> WeatherAdapter.EMPTY_VIEW
            dataList[position].dataType=="NEWS_BANNER" -> NEWS_BANNER
            dataList[position].dataType == "NEWS_CONTENT" -> NEWS_CONTENT
            else -> super.getItemViewType(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position].t
        when (holder) {
            is BannerViewHolder ->{
                val model = data as TopStoriesInfo
                val titleList:MutableList<String> = mutableListOf()
                val imgList:MutableList<String> = mutableListOf()

                for(i:Int in model.topStories!!.indices){
                    titleList.add(model.topStories!![i].title)
                    imgList.add(model.topStories!![i].image)
                }

                //添加数据
                initBanner(holder,titleList,imgList)

                holder.itemView.mNewsBanner.setOnBannerListener {
                    mBannerClickListener!!.onBannerClick(model,it)
                }
            }

            is ContentViewHolder ->{
                val model = data as StoriesInfo
                holder.itemView.mNewsConImg.loadUrl(model.images!![0])
                holder.itemView.mConNewsTitle.text = model.title
                holder.itemView.mConNewsTime.text = model.time

                holder.itemView.onClick {
                    mOnClickListener!!.onItemClick(model,position)
                }
            }
        }
    }


    private fun initBanner(holder:BannerViewHolder,titleList:MutableList<String>,imgList:MutableList<String>){
        holder.itemView.mNewsBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)//设置圆形指示器与标题
        holder.itemView.mNewsBanner.setImageLoader(BannerImageLoader())
        holder.itemView.mNewsBanner.setImages(imgList)
        holder.itemView.mNewsBanner.setBannerTitles(titleList)
        holder.itemView.mNewsBanner.setBannerAnimation(Transformer.Accordion)
        holder.itemView.mNewsBanner.setDelayTime(5000)
        holder.itemView.mNewsBanner.setIndicatorGravity(BannerConfig.RIGHT)
        holder.itemView.mNewsBanner.start()
    }


    class EmptyViewHolder(view:View):RecyclerView.ViewHolder(view)
    class BannerViewHolder(view: View):RecyclerView.ViewHolder(view)
    class ContentViewHolder(view:View):RecyclerView.ViewHolder(view)

    /**
    ItemClick事件声明
     */
    interface OnClickListener{
        fun onItemClick(item:StoriesInfo,position: Int)
    }

    interface OnBannerClickListener{
        fun onBannerClick(item:TopStoriesInfo,position: Int)
    }




}