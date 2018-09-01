package com.clw.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
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
import com.clw.ui.fragment.ComicFragment
import com.clw.ui.fragment.LikeFragment
import com.clw.ui.fragment.WeatherFragment
import com.clw.utils.AppSPrefsUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_main_layout.*
import org.jetbrains.anko.startActivity
import java.util.*
import com.gyf.barlibrary.ImmersionBar
import org.jetbrains.anko.toast

@Suppress("DEPRECATION", "SENSELESS_COMPARISON")
class MainActivity : BaseMvpActivity<GankPresenter>(), GankView{

    //Fragment栈管理
    private val mStack = Stack<Fragment>()

    //天气
    private val mWeatherFragment by lazy { WeatherFragment() }

    //发现
    private val mDiscoverFragment by lazy { ComicFragment() }

    //喜欢
    private val mLikeFragment by lazy { LikeFragment() }

    private var bgUrl:String=""


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
        }else{
            //获取最新的第一张图片做为背景图片
            mPresenter.getGank(1, 1)
        }
    }

    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

        bgUrl = AppSPrefsUtils.getString(ProviderConstant.KEY_BGIMAGE_URL)

        if(bgUrl!=null&&bgUrl.isNotEmpty()) {
            mBackViewImg.loadUrl(bgUrl)
        }else{
            //获取最新的第一张图片做为背景图片
            mPresenter.getGank(1, 1)
        }
    }

    override fun getLayoutResID(): Int {
        return R.layout.activity_main
    }

    /**
     * 初始化 Fragment栈管理
     */
    private fun initFragment() {
        val manager = supportFragmentManager.beginTransaction()
        manager.add(R.id.mContaier,mDiscoverFragment)
        manager.add(R.id.mContaier,mWeatherFragment)
        manager.add(R.id.mContaier,mLikeFragment)
        manager.commit()

        mStack.add(mDiscoverFragment)
        mStack.add(mWeatherFragment)
        mStack.add(mLikeFragment)
    }

    /**
     * 初始化底部导航栏
     */
    private fun initBottomNav() {
        //默认头部color为蓝色
        mHeadTitle.text = "动漫"
        //mHeadBgColor.visibility = View.GONE
        mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
        mBottomNavBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener{
            override fun onTabReselected(position: Int) {

            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                changeFragment(position)
                if(position==1){
                    mHeadTitle.text = ""
                    mStatusBar.setBackgroundColor(resources.getColor(R.color.colorTuoM))
                    mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorTuoM))
                }else if(position==2){
                    mHeadTitle.text = "福利"
                    mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
                    mStatusBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                }else{
                    mHeadTitle.text = "动漫"
                    mHeadBgColor.setBackgroundColor(resources.getColor(R.color.colorBlue))
                    mStatusBar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
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
        mNavView.inflateHeaderView(R.layout.nav_header)
        mNavView.setNavigationItemSelectedListener { item ->
            mDrawerLayout.closeDrawers()
            when (item.itemId) {
                R.id.nav_night ->
                    toast("夜间模式")
                    //startActivity<LeftMainActivity>("titleName" to "夜间模式","titleValue" to ProviderConstant.LEFT_NINGHT)
                R.id.nav_space ->
                    startActivity<LeftMainActivity>("titleName" to "缓存清理","titleValue" to ProviderConstant.LEFT_SPACE)
                R.id.nav_feedback ->
                    startActivity<LeftMainActivity>("titleName" to "发送反馈","titleValue" to ProviderConstant.LEFT_BACK)
                R.id.nav_siginout ->
                    AppManager.instance.finishAllActivity()
            }
            true
        }
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy() //必须调用该方法，防止内存泄漏
    }

}
