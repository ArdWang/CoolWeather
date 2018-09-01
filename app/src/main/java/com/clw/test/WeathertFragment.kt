package com.clw.test

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import com.clw.R
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.common.BaseConstant
import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.weather.AirNowCity
import com.clw.data.protocol.weather.HeWeather6
import com.clw.ext.onClick
import com.clw.presenter.WeatherPresenter
import com.clw.presenter.view.WeatherView
import kotlinx.android.synthetic.main.layout_weather_aqi.*
import kotlinx.android.synthetic.main.layout_weather_forecast.*
import kotlinx.android.synthetic.main.layout_weather_now.*
import kotlinx.android.synthetic.main.layout_weather_suggest.*
import kotlinx.android.synthetic.main.layout_weather_title.*
import java.util.ArrayList
import android.widget.ListView
import com.clw.common.ProviderConstant
import com.clw.db.City
import com.clw.db.County
import com.clw.db.DBSupport
import com.clw.db.Province
import com.clw.ui.adapter.weather.CityDialogAdapter
import com.clw.ui.adapter.weather.WeatherAdapter
import com.clw.utils.AppSPrefsUtils
import com.clw.widgets.CityDialog
import kotlinx.android.synthetic.main.fragment_weather.*


@Suppress("SENSELESS_COMPARISON")
/**
 * 注意到乡村的时候就不要本地存储了直接获取网络存储
 * 一定记得
 * 放弃使用LitePal存储数据 由于数据量不多直接采用SQLLite
 */
class WeathertFragment :BaseMvpFragment<WeatherPresenter>(),WeatherView, SwipeRefreshLayout.OnRefreshListener{

    private var weatherId:String = ""

    private lateinit var mAdapter: ForecastAdapter

    private lateinit var mAdapterAir: SuggestAdapter

    //新的Adapter
    private lateinit var mWeatherAdapter:WeatherAdapter

    private val dataList = ArrayList<String>()

    private var provincesList: MutableList<Provinces>? = null

    private var cityList:MutableList<Cities>?=null

    private var countyList:MutableList<Counties>?=null

    //查询数据库中的列表
    private var provinceMList:MutableList<Province>?=null

    private var cityMList:MutableList<City>?=null

    //private var countyMList:MutableList<County>?=null

    private var currentLevel: Int = 0

    private var ctadapter:CityDialogAdapter?=null

    private var ctlistView:ListView?=null

    private var ctButton:Button?=null

    //对话框
    private var dialog: CityDialog? = null

    //存储数据
    private var weatherShared: SharedPreferences? = null

    private val dbSupport = DBSupport()

    private var selectProvince: Province? = null

    private var selectCity: City? = null


    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
        //默认的值 查询北京的天气
        const val DEFAULT_CODE = "CN101010100"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_weather,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mSwipeLayout.setOnRefreshListener(this)
        mSwipeLayout.setColorSchemeResources(R.color.colorPrimary)
        //mWeatherAdapter = WeatherAdapter(context!!)

        //mWeatherRv.adapter = mWeatherAdapter
        mForecastRv.layoutManager = LinearLayoutManager(context!!)
        mSuggestRv.layoutManager = LinearLayoutManager(context!!)

        mAdapter = ForecastAdapter(context!!)
        mForecastRv.adapter = mAdapter

        mAdapterAir = SuggestAdapter(context!!)
        mSuggestRv.adapter = mAdapterAir
        mTitleCity.onClick {
            val builder = CityDialog.Builder(context!!)
            builder.setTitle("选择城市")
            dialog = builder.create()
            dialog!!.show()
            //不能取消必须按按钮
            //dialog.setCancelable(true);
            dialog!!.setCanceledOnTouchOutside(true)
            ctlistView = builder.getMCityList()
            ctButton = builder.getMCityBtn()
            ctadapter = CityDialogAdapter(context!!,dataList)
            ctlistView!!.adapter = ctadapter//ListView设置适配器

            ctlistView!!.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ ->
                when (currentLevel) {
                    LEVEL_PROVINCE -> {
                        selectProvince = provinceMList!![i]
                        queryCity()
                        ctButton!!.visibility = View.VISIBLE
                    }
                    LEVEL_CITY ->{
                        selectCity = cityMList!![i]
                        queryCountry()
                        ctButton!!.visibility = View.VISIBLE
                    }
                    LEVEL_COUNTY -> {
                        //获取天气id
                        val weatherId = countyList!![i].weather_id
                        this.weatherId = weatherId
                        getWeather(weatherId)
                        //存储当前的天气weatherId
                        AppSPrefsUtils.putString(ProviderConstant.KEY_WEATHER_ID,weatherId)
                        dialog!!.dismiss()
                    }
                }
            }

            //返回按钮点击事件
            ctButton!!.onClick {
                if (currentLevel == LEVEL_COUNTY) {
                    queryCity()
                } else if (currentLevel == LEVEL_CITY) {
                    queryProvince()
                    ctButton!!.visibility = View.GONE
                }
            }

            //查询省级数据
            queryProvince()
        }
    }

    /**
     * 数据初始化
     */
    private fun initData(){
        mPresenter.lifeProvider = this

        mPresenter.mView = this

        weatherShared = context!!.getSharedPreferences(ProviderConstant.KEY_WEATHER, 0)

        weatherId = AppSPrefsUtils.getString(ProviderConstant.KEY_WEATHER_ID)

        if(weatherId==null||weatherId==""){
            weatherId = DEFAULT_CODE
        }
        //执行加载天气网络数据
        getWeather(weatherId)
    }

    /**
     * 查询省份数据
     */
    private fun queryProvince(){
        provinceMList = dbSupport.findProvince()
        if(provinceMList!!.size>0){
            dataList.clear()
            for(provice in provinceMList!!){
                dataList.add(provice.provinceName!!)
            }
            ctadapter!!.notifyDataSetChanged()
            ctlistView!!.setSelection(0)
            currentLevel = LEVEL_PROVINCE
        }else{
            //加载网络数据
            mPresenter.getProvince()
        }
    }

    /**
     * 查询市级数据
     */
   private fun queryCity(){
        cityMList = dbSupport.findCity(selectProvince!!.id)
        if(cityMList!!.size>0){
            dataList.clear()
            for(city in cityMList!!){
                dataList.add(city.cityName!!)
            }
            ctadapter!!.notifyDataSetChanged()
            ctlistView!!.setSelection(0)
            currentLevel = LEVEL_CITY
        }else{
            val provinceCode = selectProvince!!.provinceCode
            //加载市级网络数据
            mPresenter.getCities(provinceCode)
        }
    }

    /**
     * 查询县级数据
     */
    private fun queryCountry(){
        /*countyMList = dbSupport.findCounty(selectCity!!.id)
        if(countyMList!!.size>0){
            dataList.clear()
            for(coutry in countyMList!!){
                dataList.add(coutry.countyName!!)
            }
            ctadapter!!.notifyDataSetChanged()
            ctlistView!!.setSelection(0)
            currentLevel = LEVEL_COUNTY
        }else{*/
            val provinceCode = selectProvince!!.provinceCode
            val cityCode = selectCity!!.cityCode
            //加载县级网络数据
            mPresenter.getCounties(provinceCode,cityCode)
        //}
    }


    /**
     * 存储省份的网络数据
     */
    private fun savaProvince(provinces: MutableList<Provinces>){
        dataList.clear()
        for(provice in provinces){
            val po = Province()
            po.provinceName = provice.name
            po.provinceCode = provice.id
            //存储数据
            provinceMList!!.add(po)
            dbSupport.saveProvince(po)
            dataList.add(provice.name)
        }
        provincesList = provinces
        ctadapter!!.notifyDataSetChanged()
        ctlistView!!.setSelection(0)
        currentLevel = LEVEL_PROVINCE
    }

    /**
     * 存储市级的网络数据
     */
    private fun saveCity(cities:MutableList<Cities>){
        dataList.clear()
        for(city in cities){
            val ct = City()
            ct.provinceId = selectProvince!!.id
            ct.cityCode = city.id
            ct.cityName = city.name
            //存储网络数据
            cityMList!!.add(ct)
            dbSupport.saveCity(ct)
            dataList.add(city.name)
        }

        cityList = cities
        ctadapter!!.notifyDataSetChanged()
        ctlistView!!.setSelection(0)
        currentLevel = LEVEL_CITY
    }

    /**
     * 存储县级的网络数据 并刷新数据
     */
    private fun saveCounties(countys: MutableList<Counties>){
        dataList.clear()
        for(county in countys){
            val cou = County()
            cou.cityId = selectCity!!.id
            cou.countyName = county.name
            cou.weatherId = county.weather_id
            //存储乡村数据
            //countyMList!!.add(cou)
            //dbSupport.saveCounty(cou)
            dataList.add(county.name)
        }
        countyList = countys
        ctadapter!!.notifyDataSetChanged()
        ctlistView!!.setSelection(0)
        currentLevel = LEVEL_COUNTY
    }

    override fun onGetProvinces(result: MutableList<Provinces>) {
        savaProvince(result)
    }

    override fun onGetCities(result: MutableList<Cities>) {
        saveCity(result)
    }

    override fun onGetCounties(result: MutableList<Counties>) {
        saveCounties(result)
    }

    @SuppressLint("SetTextI18n")
    override fun onGetWeather(result: MutableList<HeWeather6>) {
        if(result.size>0) {
            //执行控件显示
            val model = result[0]
            mTitleCity.text = model.basic.location
            mUpdateTime.text = model.update.loc
            mWeatherDeg.text = model.now.tmp
            mWeatherInfo.text = "相对湿度: "+model.now.hum+"%"

            //添加到适配器中去
           // mAdapter.setData(model.daily_forecast)
            //添加到介意的适配中去
            mAdapterAir.setData(model.lifestyle)

            // mWeatherAdapter.setData(result)

        }
    }

    override fun onGetWeatherAir(result: AirNowCity) {
        if(result!=null) {
            mAirAqi.text = result.aqi
            mAirPm.text = result.pm25
        }
    }


    private fun getWeather(location:String){
        val params = mutableMapOf<String,String>()
        params["location"] = location
        params["key"] = BaseConstant.WEATHER_HEFENG_KEY
        mPresenter.getWeather(params)
        mPresenter.getWeatherAir(params)
    }


    /**
     * 刷新控件
     */
    override fun onRefresh() {
        Handler().postDelayed({
            if(weatherId.isNotEmpty()){
                getWeather(weatherId)
            }
            mSwipeLayout.isRefreshing = false
        },2000)

    }
}

/**
 * 抛弃的代码块
 *
 *  private fun queryProvinces(){
provincesList = LitePal.findAll(Province::class.java)
if(provincesList!!.size>0){
dataList.clear()

for (province in provincesList!!) {
dataList.add(province.provinceName!!)
}

ctadapter!!.notifyDataSetChanged()
ctlistView!!.setSelection(0)
currentLevel = LEVEL_PROVINCE

}else{
//加载网络数据 并存储数据
mPresenter.getProvince()
}
}

private fun queryCities(){
cityList = LitePal.where("provinceid=?", selectProvince!!.id.toString()).find(City::class.java)
if(cityList!!.size>0){
dataList.clear()
for(city in cityList!!){
dataList.add(city.cityName!!)
}
ctadapter!!.notifyDataSetChanged()
ctlistView!!.setSelection(0)
currentLevel = LEVEL_CITY
}else{
val provinceCode = selectProvince!!.provinceCode
provinceId = provinceCode
mPresenter.getCities(provinceCode)
}
}

private fun queryCounties(){
countyList = LitePal.where("cityid=?",selectCity!!.id.toString()).find(County::class.java)
if(countyList!!.size>0){
dataList.clear()
for(county in countyList!!){
dataList.add(county.countyName!!)
}
ctadapter!!.notifyDataSetChanged()
ctlistView!!.setSelection(0)
currentLevel = LEVEL_COUNTY
}else{
val provinceCode = selectProvince!!.provinceCode
val cityCode = selectCity!!.cityCode
cityId = cityCode
//获取乡村的网络数据
mPresenter.getCounties(provinceCode,cityCode)

//查询之后要显示到列表中

}
}
 *
 *
 *
 */
