package com.clw.ui.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder
import cn.bingoogolapple.refreshlayout.BGARefreshLayout
import com.clw.R
import com.clw.base.ui.fragment.BaseMvpFragment
import com.clw.data.protocol.gank.Results
import com.clw.presenter.GankPresenter
import com.clw.presenter.view.GankView
import com.clw.ui.activity.PhotoViewActivity
import com.clw.ui.adapter.like.GankAdapter
import kotlinx.android.synthetic.main.fragment_like.*
import org.jetbrains.anko.support.v4.startActivity

@Suppress("SENSELESS_COMPARISON")
class LikeFragment :BaseMvpFragment<GankPresenter>(),GankView, BGARefreshLayout.BGARefreshLayoutDelegate{

    private lateinit var mAdapter:GankAdapter

    private var mCurrentPage: Int = 1

    private val mImgList = ArrayList<String>()

    //private var mMaxPage: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_like,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRefreshLayout()
        initData()
    }


    private fun initView(){
        mLikeRv.layoutManager = GridLayoutManager(context!!,2)
        mAdapter = GankAdapter(context!!)
        mLikeRv.adapter = mAdapter

        mAdapter.mOnClickListener = object :GankAdapter.OnClickListener<Results>{
            override fun onCardViewClick(item:Results, position: Int) {
                //传所有的list过去
                startActivity<PhotoViewActivity>("imgUrls" to mImgList,"position" to position)
            }

        }
    }

    /**
        初始化刷新视图
     */
    private fun initRefreshLayout() {
        mLikeRefreshLayout.setDelegate(this)
        val viewHolder = BGANormalRefreshViewHolder(context, true)
        viewHolder.setLoadMoreBackgroundColorRes(R.color.colorBDK)
        viewHolder.setRefreshViewBackgroundColorRes(R.color.colorBDK)
        mLikeRefreshLayout.setRefreshViewHolder(viewHolder)
    }

    private fun initData(){
        mPresenter.lifeProvider = this
        mPresenter.mView = this

        //加载图片数据
        loadData()
    }


    private fun loadData() {
        mPresenter.getGank(10,mCurrentPage)
    }

    override fun onGetGank(result: MutableList<Results>) {
        mLikeRefreshLayout.endLoadingMore()
        mLikeRefreshLayout.endRefreshing()
        if(result.size>0&&result!=null){
            if(mCurrentPage==1){
                mAdapter.setData(result)
                for(img in result) {
                    mImgList.add(img.url)
                }
            }else{
                mAdapter.dataList.addAll(result)
                mAdapter.notifyDataSetChanged()
                for(img in result) {
                    mImgList.add(img.url)
                }
            }

        }
    }

    /**
     * 上拉加载更多
     */
    override fun onBGARefreshLayoutBeginLoadingMore(refreshLayout: BGARefreshLayout?): Boolean {
        mCurrentPage++
        loadData()
        return true
    }

    /**
     * 下拉刷新
     */
    override fun onBGARefreshLayoutBeginRefreshing(refreshLayout: BGARefreshLayout?) {
        //mCurrentPage = 1
        loadData()
    }
}