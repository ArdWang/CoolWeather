package com.clw.test

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.weather.LifeStyle
import kotlinx.android.synthetic.main.layout_weather_suggest_item.view.*

class SuggestAdapter(context: Context) :BaseRecyclerViewAdapter<LifeStyle, SuggestAdapter.ViewHolder>(context){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_weather_suggest_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        when{
            model.type=="comf"->holder.itemView.mLifeType.text="舒适度指数"
            model.type=="cw"->holder.itemView.mLifeType.text="洗车指数"
            model.type=="drsg"->holder.itemView.mLifeType.text="穿衣指数"
            model.type=="flu"->holder.itemView.mLifeType.text="感冒指数"
            model.type=="sport"->holder.itemView.mLifeType.text="运动指数"
            model.type=="trav"->holder.itemView.mLifeType.text="旅游指数"
            model.type=="uv"->holder.itemView.mLifeType.text="紫外线指数"
            model.type=="air"->holder.itemView.mLifeType.text="空气污染扩散条件指数"
            model.type=="ac"->holder.itemView.mLifeType.text="空调开启指数"
            model.type=="ag"->holder.itemView.mLifeType.text="过敏指数"
            model.type=="gi"->holder.itemView.mLifeType.text="太阳镜指数"
            model.type=="mu"->holder.itemView.mLifeType.text="化妆指数"
            model.type=="airc"->holder.itemView.mLifeType.text="晾晒指数"
            model.type=="ptfc"->holder.itemView.mLifeType.text="交通指数"
            model.type=="fisin"->holder.itemView.mLifeType.text="钓鱼指数"
            model.type=="spi"->holder.itemView.mLifeType.text="防嗮指数"
        }

        holder.itemView.mLifeBrf.text = model.brf
        holder.itemView.mLifeTxt.text = model.txt

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}