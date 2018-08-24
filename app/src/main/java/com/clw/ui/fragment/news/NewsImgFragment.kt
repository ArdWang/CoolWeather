package com.clw.ui.fragment.news

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.clw.R
import com.clw.base.ui.adapter.BaseRecyclerViewAdapter
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.common.BaseConstant
import com.clw.data.protocol.news.Data
import com.clw.presenter.NewsPresenter
import com.clw.presenter.view.NewsView
import com.clw.ui.activity.NewsViewActivity
import com.clw.ui.adapter.news.NewsImgAdapter
import kotlinx.android.synthetic.main.fragment_news_img.*
import org.jetbrains.anko.support.v4.startActivity

@Suppress("SENSELESS_COMPARISON")
class NewsImgFragment : BaseMvpFragment<NewsPresenter>(), NewsView,BGARefreshLayout.BGARefreshLayoutDelegate{
    private lateinit var mImgAdapter: NewsImgAdapter

    private var mCurrentPage: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_news_img,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initData()
    }

    private fun initView(){

        mNewsImgRv.layoutManager = LinearLayoutManager(activity)
        mImgAdapter = NewsImgAdapter(activity!!)
        mNewsImgRv.adapter = mImgAdapter

        mImgAdapter.mOnClickListener = object : NewsImgAdapter.OnClickListener{
            override fun onLdViewClick(item: Data, position: Int) {
                startActivity<NewsViewActivity>("title" to item.title,"content" to item.content,"imgList" to item.imageUrls)
            }
        }

        mImgAdapter.setOnItemClickListener(object : BaseRecyclerViewAdapter.OnItemClickListener<Data>{
            override fun onItemClick(item: Data, position: Int) {
                startActivity<NewsViewActivity>("title" to item.title,"content" to item.content,"imgList" to item.imageUrls)
            }
        })
    }

    /**
    初始化刷新视图
     */
    private fun initRefreshLayout() {
        mNewsImgRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(context, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.colorBDK)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.colorBDK)
        mNewsImgRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

        //加载新闻数据
        loadData()
    }

    private fun loadData() {
        val params = mutableMapOf<String,String>()
        params["kw"] = "白"
        params["site"] = "qq.com"
        params["apikey"] = BaseConstant.NEWS_KEY
        params["pageToken"] = mCurrentPage.toString()
        mPresenter.getNews(params)
    }


    override fun getNews(result: MutableList<Data>) {
        mNewsImgRefreshLayout.endLoadingMore()
        mNewsImgRefreshLayout.endRefreshing()
        if(result!=null){
            if(result.size>0){
                if(mCurrentPage==1){
                    mImgAdapter.setData(result)
                    //mDataList = result

                }else{
                    mImgAdapter.dataList.addAll(result)
                    mImgAdapter.notifyDataSetChanged()
                    //mDataList = result
                }
            }
        }
    }

    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        mCurrentPage++
        loadData()
        return true
    }

    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        loadData()
    }
}