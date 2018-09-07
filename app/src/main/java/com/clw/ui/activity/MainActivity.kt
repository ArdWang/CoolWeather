package com.clw.ui.activity


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SwitchCompat
import android.view.KeyEvent
import android.widget.ImageView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.clw.R
import com.clw.base.ui.activity.BaseMvpActivity
import com.clw.common.AppManager
import com.clw.common.ProviderConstant
import com.clw.data.protocol.gank.Results
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import com.clw.presenter.GankPresenter
import com.clw.presenter.view.GankView
import com.clw.ui.fragment.*
import com.clw.utils.AppSPrefsUtils
import com.clw.widgets.CustomDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_main_layout.*
import org.jetbrains.anko.startActivity
import java.util.*
import com.gyf.barlibrary.ImmersionBar
import org.jetbrains.anko.browse
import org.jetbrains.anko.toast
import android.content.Context
import android.util.Log
import android.widget.TextView
import com.clw.common.MainApplication.Companion.context


@Suppress("DEPRECATION", "SENSELESS_COMPARISON")
class MainActivity : BaseMvpActivity<GankPresenter>(), GankView{

    //Fragment栈管理
    private val mStack = Stack<Fragment>()

    //天气
    private val mWeatherFragment by lazy { WeatherFragment() }

    //发现
    //private val mDiscoverFragment by lazy { ComicFragment() }

    //喜欢
    private val mLikeFragment by lazy { LikeFragment() }

    //新闻
    private val mNewsFragemnt by lazy { NewsAllFragment() }

    private var bgUrl:String=""

    //创建对话框
    private var dialog:CustomDialog?=null

    //导航栏图片
    private var mNavHeaderImg:ImageView?=null

    //获取当前版本号
    private var mDepartName:TextView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this).init()
        //mImmersionBar = ImmersionBar.with(this)

       // mImmersionBar!!.init()   //所有子类都将继承这些相同的属性
        initFragment()
        initBottomNav()

        //默认第一个显示
        changeFragment(0)
        onViewClick()
        initData()
    }

    override fun onResume() {
        super.onResume()
        bgUrl = AppSPrefsUtils.getString(ProviderConstant.KEY_BGIMAGE_URL)

        if(bgUrl!=null&&bgUrl.isNotEmpty()) {
            mBackViewImg.loadUrl(bgUrl)
            mMainImage.loadUrl(bgUrl)
            mNavHeaderImg!!.loadUrl(bgUrl)
        }else{
            //获取最新的第一张图片做为背景图片
            mPresenter.getGank(1, 1)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

        mDepartName!!.text = "v "+getAppVersionName(this@MainActivity)

        /*bgUrl = AppSPrefsUtils.getString(ProviderConstant.KEY_BGIMAGE_URL)

        if(bgUrl!=null&&bgUrl.isNotEmpty()) {
            mBackViewImg.loadUrl(bgUrl)
        }else{
            //获取最新的第一张图片做为背景图片
            mPresenter.getGank(1, 1)
        }*/
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    /**
     * 初始化 Fragment栈管理
     */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        //manager.add(R.id.mContaier,mDiscoverFragment)
        manager.add(R.id.mContaier,mNewsFragemnt)
        manager.add(R.id.mContaier,mWeatherFragment)
        manager.add(R.id.mContaier,mLikeFragment)
        manager.commit()

        //mStack.add(mDiscoverFragment)
        mStack.add(mNewsFragemnt)
        mStack.add(mWeatherFragment)
        mStack.add(mLikeFragment)
    }

    /**
     * 初始化底部导航栏
     */
    private fun initBottomNav() {
        //默认头部color为蓝色
        mHeadTitle.text = "新闻"
        //mHeadBgColor.visibility = View.GONE
        mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
                when (position) {
                    1 -> {
                        mHeadTitle.text = ""
                        mStatusBar.setBackgroundColor(resources.getColor(R.color.colorTuoM))
                        mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorTuoM))
                    }
                    2 -> {
                        mHeadTitle.text = "福利"
                        mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
                        mStatusBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    }
                    else -> {
                        mHeadTitle.text = "新闻"
                        mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
                        mStatusBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    }
                }
            }
        })
    }

    /**
     * 切换Tab,对应的Fragment
     */
    private fun changeFragment(position: Int){
        val manager = supportFragmentManager.beginTransaction()
        for (fragment in mStack){
            manager.hide(fragment)
        }

        manager.show(mStack[position])
        manager.commit()
    }

    override fun initView(){
        val view  = mNavView.inflateHeaderView(R.layout.nav_header)
        mNavHeaderImg =view.findViewById(R.id.mNavHeaderImg)
        mDepartName = view.findViewById(R.id.mDepartName)
        mNavView.setNavigationItemSelectedListener { item ->
            mDrawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_star ->
                    browse("https://github.com/ArdWang/CoolWeather")
                R.id.nav_space ->
                    startActivity<LeftMainActivity>("titleName" to "缓存清理","titleValue" to ProviderConstant.LEFT_SPACE)
                R.id.nav_feedback ->
                    startActivity<LeftMainActivity>("titleName" to "发送反馈","titleValue" to ProviderConstant.LEFT_BACK)

                R.id.nav_about ->{
                    browse("https://github.com/ArdWang")
                }
                R.id.nav_siginout ->
                    onCreateDialog()
            }
            true
        }

        //创建夜间模式
        val menuItem = mNavView.menu.findItem(R.id.night_theme)
        val switchCompat = MenuItemCompat.getActionView(menuItem) as SwitchCompat

        val theme = AppSPrefsUtils.getString("theme")

        if(theme.isEmpty()){
            switchCompat.isChecked = false
        }

        switchCompat.isChecked = theme==ProviderConstant.NIGHT_THEME

        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppSPrefsUtils.putString("theme",ProviderConstant.NIGHT_THEME)
            } else {
                AppSPrefsUtils.putString("theme",ProviderConstant.DAY_THEME)
            }
        }
    }

    /**
     * 创建对话框
     */
    private fun onCreateDialog(){
        val builder = CustomDialog.Builder(this@MainActivity)
        builder.setTips("退出程序")
        builder.setContent("你确定退出酷酷天气程序吗?")

        builder.setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialogInterface, _ ->
            //退出App的操作
            AppManager.instance.exitApp(this@MainActivity)
            //执行操作
            dialogInterface.dismiss()

        }).setnegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialogInterface, _ ->
            //执行操作
            dialogInterface.dismiss()
        })

        dialog = builder.create()
        dialog!!.show()
        dialog!!.setCanceledOnTouchOutside(true)
    }

    private fun onViewClick(){
        mMainImage.onClick {
            mDrawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onGetGank(result: MutableList<Results>) {
        if(result.size>0) {
            val model = result[0]
            mBackViewImg.loadUrl(model.url)
            mMainImage.loadUrl(model.url)
            mNavHeaderImg!!.loadUrl(model.url)
        }
    }

    /**
     * 返回当前程序版本名
     */
    private fun getAppVersionName(context: Context): String {
        var versionName: String? = ""
        try {
            // ---get the package info---
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
            //versioncode = pi.versionCode
            if (versionName == null || versionName.isEmpty()) {
                return ""
            }
        } catch (e: Exception) {
            Log.e("VersionInfo", "Exception", e)
        }
        return versionName!!
    }

    /**
     * 退出程序显示提示
     */
    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN
                && event.repeatCount == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                toast( "再按一次退出程序")
                mExitTime = System.currentTimeMillis()
            } else {
                onCreateDialog()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy() //必须调用该方法，防止内存泄漏
    }

}
