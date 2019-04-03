package com.clw.ui.fragment.news

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clw.R
import com.clw.base.ui.adapter.RecycleViewItemData
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.data.protocol.news.LatestResp
import com.clw.data.protocol.news.StoriesInfo
import com.clw.data.protocol.news.TopStoriesInfo
import com.clw.presenter.LatestPresenter
import com.clw.presenter.view.LatestView
import com.clw.ui.activity.NewsConActivity
import com.clw.ui.adapter.news.LatestAdapter
import kotlinx.android.synthetic.main.fragment_news_latest.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.ArrayList

@Suppress("SENSELESS_COMPARISON")
/**
 * 最新新闻
 */
class NewsLatestFragment :BaseMvpFragment<LatestPresenter>(),LatestView, SwipeRefreshLayout.OnRefreshListener{

    private lateinit var mAdapter:LatestAdapter

    private val dataList = ArrayList<RecycleViewItemData<*>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_news_latest,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     * 初始化界面
     */
    private fun initView() {

        mSwipeLateLayout.setOnRefreshListener(this)
        mSwipeLateLayout.setColorSchemeResources(R.color.colorPrimary)

        mLatestRv.layoutManager =  LinearLayoutManager(context)
        mAdapter = LatestAdapter(context!!)

        mLatestRv.adapter = mAdapter

        mAdapter.mOnClickListener = object :LatestAdapter.OnClickListener{
            override fun onItemClick(item: StoriesInfo, position: Int) {
                startActivity<NewsConActivity>("newsId" to item.id!!)
            }
        }

        mAdapter.mBannerClickListener = object : LatestAdapter.OnBannerClickListener{
            override fun onBannerClick(item: TopStoriesInfo, position: Int) {
                startActivity<NewsConActivity>("newsId" to item.topStories!![position].id)
            }
        }

    }

    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this
        mPresenter.getLatest()
    }


    /**
     * 返回结果的处理
     */
    override fun getLatest(result: LatestResp) {
        //如果不为空的时候先要清空
        if(dataList.size>0){
            dataList.clear()
        }

        if(result!=null){
            if(result.top_stories.size>0){
                val info = TopStoriesInfo()
                info.topStories = result.top_stories
                val data = RecycleViewItemData<TopStoriesInfo>()
                data.t = info
                data.dataType = "NEWS_BANNER"
                dataList.add(data)
            }

            if(result.stories.size>0){
                for(i:Int in result.stories.indices){
                    val info = StoriesInfo()
                    info.id = result.stories[i].id
                    info.title = result.stories[i].title
                    info.images = result.stories[i].images
                    info.ga_prefix = result.stories[i].ga_prefix
                    info.type = result.stories[i].type
                    info.time = result.date

                    val data = RecycleViewItemData<StoriesInfo>()
                    data.t = info
                    data.dataType = "NEWS_CONTENT"
                    dataList.add(data)
                }
            }
        }

        mAdapter.setData(dataList)
    }

    override fun onRefresh() {
        Handler().postDelayed({
            mPresenter.getLatest()
            mSwipeLateLayout.isRefreshing = false
        },2000)
    }

}