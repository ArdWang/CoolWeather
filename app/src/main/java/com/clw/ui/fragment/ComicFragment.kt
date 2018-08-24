package com.clw.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.common.BaseConstant
import com.clw.data.protocol.comic.banner.Banner
import com.clw.data.protocol.comic.daycomic.DayComicResp
import com.clw.data.protocol.comic.japancomic.JapanComicResp
import com.clw.data.protocol.comic.newcomic.NewComicResp
import com.clw.data.protocol.comic.recommend.RecommendResp
import com.clw.data.protocol.comic.updatecomic.UpdateComicResp
import com.clw.ext.loadUrl
import com.clw.ext.onClick
import com.clw.presenter.ComicPresenter
import com.clw.presenter.view.ComicView
import com.clw.test.DemoActivity
import com.clw.ui.adapter.comic.JapanComicAdapter
import com.clw.ui.adapter.comic.NewComicAdapter
import com.clw.ui.adapter.comic.RecommendAdapter
import com.clw.ui.adapter.comic.UpdateComicAdapter
import com.clw.widgets.BannerImageLoader
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_comic.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

@Suppress("SENSELESS_COMPARISON")
class ComicFragment :BaseMvpFragment<ComicPresenter>(),ComicView, SwipeRefreshLayout.OnRefreshListener{

    private val bannerList = ArrayList<String>()

    private var isOnce = true

    private lateinit var mRecommendAdapter: RecommendAdapter

    private lateinit var mUpdateAdapter: UpdateComicAdapter

    private lateinit var mNewAdapter: NewComicAdapter

    private lateinit var mJapanAdapter: JapanComicAdapter

    private var webUrl:String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_comic,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        initEvent()
    }

    private fun initView(){

        //获取推荐的动漫
        mComicRecommendRv.layoutManager = GridLayoutManager(context!!,3)
        mRecommendAdapter = RecommendAdapter(context!!)
        mComicRecommendRv.adapter = mRecommendAdapter

        //获取今日更新动漫
        mUpdateDayRv.layoutManager = LinearLayoutManager(context!!)
        mUpdateAdapter = UpdateComicAdapter(context!!)
        mUpdateDayRv.adapter = mUpdateAdapter

        //获取最新上线的动漫
        mNewComicRv.layoutManager = GridLayoutManager(context!!,3)
        mNewAdapter = NewComicAdapter(context!!)
        mNewComicRv.adapter = mNewAdapter

        //获取日漫经典作品
        mJapanComicRv.layoutManager = GridLayoutManager(context!!,3)
        mJapanAdapter = JapanComicAdapter(context!!)
        mJapanComicRv.adapter = mJapanAdapter

        //刷新
        mComicSwipe.setOnRefreshListener(this)
        mComicSwipe.setColorSchemeResources(R.color.colorPrimary)

    }

    private fun initData(){

        mPresenter.lifeProvider = this

        mPresenter.mView = this

        getData()

    }

    private fun initBanner(){
        if(bannerList.size>0 && isOnce) {
            mComicBanner.setImageLoader(BannerImageLoader())
            mComicBanner.setImages(bannerList)
            mComicBanner.setBannerAnimation(Transformer.Accordion)
            mComicBanner.setDelayTime(2000)
            //设置指示器位置（当banner模式中有指示器时）
            mComicBanner.setIndicatorGravity(BannerConfig.RIGHT)
            //banner设置方法全部调用完毕时最后调用
            mComicBanner.start()
            isOnce = false
        }
    }

    private fun getData(){
        //得到动漫Banner
        mPresenter.getComicBanner()

        //得到推荐动漫
        mPresenter.getRecommend()

        //得到每日一推
        mPresenter.getDayComic()

        //得到今日更新新动漫
        mPresenter.getDayUpdate()

        //获取最新上线
        mPresenter.getNewComic()

        //获取经典日漫
        mPresenter.getJapanComic()
    }

    private fun initEvent(){
        //获取推荐更多
        mComicRecommendMore.onClick {
            startActivity<DemoActivity>("webUrl" to webUrl!!)
        }

        mComicBanner.setOnBannerListener {
            position -> toast(position.toString())
        }
    }


    /**
     * 获取Banner栏目
     */
    override fun onGetComicBanner(result: MutableList<Banner>) {
        if(result!=null&&result.size>0){
            for(banner in result){
                bannerList.add(banner.cover)
            }
            initBanner()
        }
    }

    /**
     * 获取推荐
     */
    override fun onGetRecommend(result: RecommendResp) {
        if(result!=null) {
            webUrl = BaseConstant.APP_TECENT_ADDRE+result.more
            if (result.comicList != null && result.comicList.size > 0) {
                mRecommendAdapter.setData(result.comicList)
            }
        }
    }


    /**
     * 获取每日一更
     */
    override fun onGetDayComic(result: DayComicResp) {
        if(result!=null) {
            //显示数据
            mComicDayImg.loadUrl(result.dayComic.cover)
            mComicDayTitle.text = result.dayComic.title
            mComicDayAuthor.text = result.dayComic.author
            //点击事件单独放在一个大类型里面
        }
    }

    /**
     * 获取当前的更新的
     */
    override fun onGetUpdateComic(result: UpdateComicResp) {
        if(result!=null){
            if(result.updateComicList.size>0){
                mUpdateAdapter.setData(result.updateComicList)
            }
        }
    }

    /**
     * 获取最新上线的作品
     */
    override fun onGetNewComic(result: NewComicResp) {
        if(result!=null){
            if(result.newComicList.size>0){
                mNewAdapter.setData(result.newComicList)
            }
        }
    }

    /**
     * 获取日漫推荐
     */
    override fun onGetJapanComic(result: JapanComicResp) {
        if(result!=null){
            if(result.japanComicList.size>0){
                mJapanAdapter.setData(result.japanComicList)
            }
        }
    }

    override fun onRefresh() {
        Handler().postDelayed({
            getData()
            mComicSwipe.isRefreshing = false
        },2000)
    }


}