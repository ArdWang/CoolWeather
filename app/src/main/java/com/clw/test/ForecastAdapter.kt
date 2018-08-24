package com.clw.test

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.data.protocol.weather.DailyForecast
import kotlinx.android.synthetic.main.layout_weather_forecast_item.view.*

class ForecastAdapter(context: Context) :BaseRecyclerViewAdapter<DailyForecast, ForecastAdapter.ViewHolder>(context){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_weather_forecast_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val model = dataList[position]

        //显示当前日期
        holder.itemView.mDateTime.text = model.date

        when {
            model.cond_code_d=="100" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_01)
            model.cond_code_d=="101" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_04)
            model.cond_code_d=="102" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_02)
            model.cond_code_d=="103" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_03)
            model.cond_code_d=="104" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_23)
            model.cond_code_d=="200" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_01)
            model.cond_code_d=="201" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_01)
            model.cond_code_d=="202" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_03)
            model.cond_code_d=="300" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_08)
            model.cond_code_d=="301" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="302" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="303" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="304" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="305" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="306" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="307" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="308" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="309" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="310" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="311" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="312" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="313" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_10)
            model.cond_code_d=="314" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_08)
            model.cond_code_d=="315" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_08)
            model.cond_code_d=="316" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_08)
            model.cond_code_d=="317" -> holder.itemView.mForecastImg.setImageResource(R.drawable.weathy_08)
            ///后续可以自己添加
        }

        //文字描述
        holder.itemView.mForecastTxt.text = model.cond_txt_d
        //最大温度
        holder.itemView.mMaxTemp.text = "最高温度: "+model.tmp_max+"℃"
        //最小温度
        holder.itemView.mMinTemp.text = "最低温度: "+model.tmp_min+"℃"
        //湿度
        holder.itemView.mForecastHum.text = "相对湿度: "+model.hum+"%"
        //风向
        holder.itemView.mForecastDir.text = "风向: "+model.wind_dir
        //风速
        holder.itemView.mForecastSc.text = "风力强度: "+model.wind_spd
        //紫外线强度
        holder.itemView.mForecastUv.text = "紫外线强度: "+model.uv_index
        //能见度
        holder.itemView.mForecastVis.text = "能见度: "+model.vis+"公里"

    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view)
}