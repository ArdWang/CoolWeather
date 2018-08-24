package com.clw.ui.adapter.weather

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.clw.R
import com.clw.base.ui.adapter.RecycleViewItemData
import com.clw.data.protocol.weather.*
import com.clw.ext.onClick
import kotlinx.android.synthetic.main.layout_weather_aqi_item.view.*
import kotlinx.android.synthetic.main.layout_weather_forecast_item.view.*
import kotlinx.android.synthetic.main.layout_weather_forecast_title.view.*
import kotlinx.android.synthetic.main.layout_weather_suggest_item.view.*
import kotlinx.android.synthetic.main.layout_weather_suggest_title.view.*
import kotlinx.android.synthetic.main.layout_weather_title_item.view.*


/**
 * 此处有多个布局 必须要注意
 */
class WeatherAdapter constructor(var context:Context):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //数据集合
    private var dataList: MutableList<RecycleViewItemData<*>> = mutableListOf()

    //ItemClick事件
    var mOnClickListener: OnClickListener? = null

    companion object {
        const val EMPTY_VIEW = 0
        const val WEATHER_VIEW = 1
        const val FORECAST_TITLE_VIEW = 2
        const val FORECAST_VIEW = 3
        const val AQI_VIEW = 4
        const val SUGGEST_TITLE_VIEW = 5
        const val SUGGEST_VIEW = 6
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
        val view :View
        return when (viewType) {
            EMPTY_VIEW -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_empty_item, parent, false)
                EmptyViewHolder(view)
            }

            WEATHER_VIEW -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_title_item, parent, false)
                WeatherViewHolder(view)
            }

            FORECAST_TITLE_VIEW ->{
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_forecast_title, parent, false)
                ForecastTitleViewHolder(view)
            }

            FORECAST_VIEW -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_forecast_item, parent, false)
                ForecastViewHolder(view)
            }


            AQI_VIEW -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_aqi_item, parent, false)
                AqiViewHolder(view)
            }

            SUGGEST_TITLE_VIEW ->{
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_suggest_title, parent, false)
                SuggestTitleViewHolder(view)
            }

            else -> {
                view = LayoutInflater.from(context).inflate(R.layout.layout_weather_suggest_item, parent, false)
                SuggestViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            dataList.size==0 -> EMPTY_VIEW
            dataList[position].dataType=="WEATHER_INFO" -> WEATHER_VIEW
            dataList[position].dataType == "FORECAST_TITLE" -> FORECAST_TITLE_VIEW
            dataList[position].dataType=="FORECAST" -> FORECAST_VIEW
            dataList[position].dataType=="AQI_DATA"-> AQI_VIEW
            dataList[position].dataType == "SUGGEST_TITLE" -> SUGGEST_TITLE_VIEW
            dataList[position].dataType=="SUGGEST" -> SUGGEST_VIEW
            else -> super.getItemViewType(position)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position].t
        when (holder) {
            is WeatherViewHolder -> {
                val model = data as WeatherInfo
                holder.itemView.mUpdatesTime.text = model.updatetime
                holder.itemView.mTitlesCity.text = model.location
                holder.itemView.mWeathersTmp.text = model.tmp
                holder.itemView.mWeathersInfo.text = "相对湿度: "+model.hum+"%"

                holder.itemView.mTitlesCity.onClick {
                    mOnClickListener!!.onTextClick(holder.itemView,position)
                }
            }

            is ForecastTitleViewHolder ->{
                val model = data as DailyTitle
                holder.itemView.mForecastTitle.text = model.title
            }

            is ForecastViewHolder -> {
                val model = data as DailyForecast
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

            is AqiViewHolder ->{
                val model = data as AirNowCity
                holder.itemView.mAirsPm.text = model.pm25
                holder.itemView.mAirsAqi.text = model.aqi
            }

            is SuggestTitleViewHolder ->{
                val model = data as SuggestTitle
                holder.itemView.mSuggestTitle.text = model.title
            }

            is SuggestViewHolder -> {
                val model = data as LifeStyle
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
        }
    }




    class EmptyViewHolder(view:View):RecyclerView.ViewHolder(view)

    class WeatherViewHolder(view:View):RecyclerView.ViewHolder(view)

    class ForecastTitleViewHolder(view: View):RecyclerView.ViewHolder(view)

    class ForecastViewHolder(view:View) :RecyclerView.ViewHolder(view)

    class AqiViewHolder(view: View):RecyclerView.ViewHolder(view)

    class SuggestTitleViewHolder(view: View):RecyclerView.ViewHolder(view)

    class SuggestViewHolder(view:View) :RecyclerView.ViewHolder(view)


    /**
        ItemClick事件声明
     */
    interface OnClickListener{
        //fun onItemClick(view:View,position: Int)
        fun onTextClick(view:View,position: Int)
    }

    /**
     * 当需要的时候再加上去
     */
    /*fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mOnClickListener = listener
    }*/

}