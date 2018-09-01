package com.clw.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import com.clw.R
import com.clw.base.ui.adapter.RecycleViewItemData
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.common.BaseConstant
import com.clw.common.ProviderConstant
import com.clw.data.protocol.Cities
import com.clw.data.protocol.Counties
import com.clw.data.protocol.Provinces
import com.clw.data.protocol.weather.*
import com.clw.db.City
import com.clw.db.County
import com.clw.db.DBSupport
import com.clw.db.Province
import com.clw.ext.onClick
import com.clw.presenter.WeatherPresenter
import com.clw.presenter.view.WeatherView
import com.clw.test.WeathertFragment
import com.clw.ui.adapter.weather.CityDialogAdapter
import com.clw.ui.adapter.weather.WeatherAdapter
import com.clw.utils.AppSPrefsUtils
import com.clw.widgets.CityDialog
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.fragment_weather_new.*
import java.util.ArrayList


@Suppress("SENSELESS_COMPARISON")
class WeatherFragment: BaseMvpFragment<WeatherPresenter>(), WeatherView, SwipeRefreshLayout.OnRefreshListener {

    private var weathersId:String = ""

    private lateinit var weatherAdapter:WeatherAdapter

    private val dataList = ArrayList<String>()

    private var provincesList: MutableList<Provinces>? = null

    private var cityList:MutableList<Cities>?=null

    private var countyList:MutableList<Counties>?=null

    //查询数据库中的列表
    private var provinceMList:MutableList<Province>?=null

    private var cityMList:MutableList<City>?=null

    private var currentLevel: Int = 0

    private var ctadapter: CityDialogAdapter?=null

    private var ctlistView: ListView?=null

    private var ctButton: Button?=null

    //对话框
    private var dialog: CityDialog? = null

    //存储数据
    //private var weatherShared: SharedPreferences? = null

    private val dbSupport = DBSupport()

    private var selectProvince: Province? = null

    private var selectCity: City? = null

    private val datasList = ArrayList<RecycleViewItemData<*>>()

    private var airCity:AirNowCity?=null

    //private var mImmersionBar: ImmersionBar? = null

    companion object {
        const val LEVEL_PROVINCE = 0
        const val LEVEL_CITY = 1
        const val LEVEL_COUNTY = 2
        //默认的值 查询北京的天气
        const val DEFAULT_CODE = "CN101010100"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_weather_new,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //mImmersionBar = ImmersionBar.with(this)
       // mImmersionBar!!.statusBarColor(R.color.colorPrimary).fitsSystemWindows(true).keyboardEnable(true).navigationBarWithKitkatEnable(false).init()
        initView()
        initData()
    }

    private fun initView() {
        mSwipesLayout.setOnRefreshListener(this)
        mSwipesLayout.setColorSchemeResources(R.color.colorPrimary)

        mWeatherRv.layoutManager = LinearLayoutManager(context!!)
        weatherAdapter = WeatherAdapter(context!!)

        mWeatherRv.adapter = weatherAdapter

        weatherAdapter.mOnClickListener = object :WeatherAdapter.OnClickListener{
            override fun onTextClick(view: View, position: Int) {
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
                            weathersId = weatherId
                            getWeather(weatherId)
                            //存储当前的天气weatherId
                            AppSPrefsUtils.putString(ProviderConstant.KEY_WEATHER_ID,weatherId)
                            dialog!!.dismiss()
                        }
                    }
                }

                //返回按钮点击事件
                ctButton!!.onClick {
                    if (currentLevel == WeathertFragment.LEVEL_COUNTY) {
                        queryCity()
                    } else if (currentLevel == WeathertFragment.LEVEL_CITY) {
                        queryProvince()
                        ctButton!!.visibility = View.GONE
                    }
                }

                //查询省级数据
                queryProvince()
            }
        }


    }

    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

       // weatherShared = context!!.getSharedPreferences(ProviderConstant.KEY_WEATHER, 0)

        weathersId = AppSPrefsUtils.getString(ProviderConstant.KEY_WEATHER_ID)

        if(weathersId==null||weathersId==""){
            weathersId = DEFAULT_CODE
        }
        //执行加载天气网络数据
        getWeather(weathersId)
    }

    private fun getWeather(location:String){
        val params = mutableMapOf<String,String>()
        params["location"] = location
        params["key"] = BaseConstant.WEATHER_HEFENG_KEY
        mPresenter.getWeatherAir(params)
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
        currentLevel = WeathertFragment.LEVEL_PROVINCE
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
        currentLevel = WeathertFragment.LEVEL_CITY
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
        currentLevel = WeathertFragment.LEVEL_COUNTY
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

    override fun onGetWeather(result: MutableList<HeWeather6>) {
        //val datasList = ArrayList<RecycleViewItemData<*>>()
        for(i:Int in result.indices){

            //添加头部天气数据
            if(result[i].basic!=null&&result[i].now!=null&&result[i].update!=null) {
                val info = WeatherInfo()
                info.location = result[i].basic.location
                info.updatetime = result[i].update.loc
                info.tmp = result[i].now.tmp
                info.hum = result[i].now.hum

                val data = RecycleViewItemData<WeatherInfo>()
                data.t = info
                data.dataType = "WEATHER_INFO"

                datasList.add(data)
            }

            //添加当前7天数据的头部
            if(result.size>0){
                val title = DailyTitle()
                title.title = "天气预报"
                val data = RecycleViewItemData<DailyTitle>()
                data.t = title
                data.dataType = "FORECAST_TITLE"
                datasList.add(data)
            }

            //添加当前7天数据
            for(forecast in result[i].daily_forecast){
                //forecast.tip = "FORECAST"
                val data = RecycleViewItemData<DailyForecast>()
                data.t = forecast
                data.dataType = "FORECAST"
                datasList.add(data)
            }

            //添加空气质量
            if(airCity!=null){
                val  data = RecycleViewItemData<AirNowCity>()
                data.t = airCity
                data.dataType = "AQI_DATA"
                datasList.add(data)
            }

            //添加生活建议头部
            if(result.size>0){
                val title = SuggestTitle()
                title.title = "生活建议"
                val data = RecycleViewItemData<SuggestTitle>()
                data.t = title
                data.dataType = "SUGGEST_TITLE"
                datasList.add(data)
            }

            //添加介意数据
            for(suggest in result[i].lifestyle){
                //suggest.tip = "SUGGEST"
                val data = RecycleViewItemData<LifeStyle>()
                data.t = suggest
                data.dataType = "SUGGEST"
                datasList.add(data)
            }
        }
        weatherAdapter.setData(datasList)
    }

    override fun onGetWeatherAir(result: AirNowCity) {
        if(datasList.size>0){
            datasList.clear()
        }

        //添加空气质量
        if(result!=null){
            airCity = result
        }

        val params = mutableMapOf<String,String>()
        params["location"] = weathersId
        params["key"] = BaseConstant.WEATHER_HEFENG_KEY
        mPresenter.getWeather(params)
    }

    override fun onRefresh() {
        Handler().postDelayed({
            if(weathersId.isNotEmpty()){
                getWeather(weathersId)
            }
            mSwipesLayout.isRefreshing = false
        },2000)
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }


}