package com.clw.ui.activity


import android.os.Bundle
import android.support.v4.view.ViewPager
import com.clw.R
import com.clw.base.ui.activity.BaseActivity
import com.clw.common.AppManager
import com.clw.common.ProviderConstant
import com.clw.ext.onClick
import com.clw.ui.adapter.like.ImageVpAdapter
import com.clw.utils.AppSPrefsUtils
import com.gyf.barlibrary.ImmersionBar
import kotlinx.android.synthetic.main.activity_photoview.*
import org.jetbrains.anko.toast

/**
 * 照片查看界面
 */
@Suppress("SENSELESS_COMPARISON","SetTextI18n")
class PhotoViewActivity :BaseActivity(){

    //private var currentPosition: Int = 0

    private lateinit var mAdapter: ImageVpAdapter

    var result = ArrayList<String>()

    private var pos:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ImmersionBar.with(this).barColor(R.color.colorBlack).fitsSystemWindows(true) .init()

        setContentView(R.layout.activity_photoview)

        initData()

        initView()
    }


    private fun initData() {
        result = intent.getStringArrayListExtra("imgUrls")
        pos = intent.getIntExtra("position",0)

    }

    private fun initView(){
        if(result.size>0&&result!=null){
            mAdapter = ImageVpAdapter(result,this)
            mPhotoViewPager!!.adapter = mAdapter
            mPhotoViewPager!!.setCurrentItem(pos,false)

            mImgTitle.text = (pos+1).toString() + "/" + result.size

            mPhotoViewPager!!.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    pos = position
                    mImgTitle.text = (pos+1).toString() + "/" + result.size
                }
            })
        }

        mCrossIv.onClick {
            AppManager.instance.finishActivity(this)
        }


        mSaveBtn.onClick {
            if(result.size>0&&result!=null) {
                AppSPrefsUtils.putString(ProviderConstant.KEY_BGIMAGE_URL, result[pos])
                toast("设置成功")
                AppManager.instance.finishActivity(this)
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        ImmersionBar.with(this).destroy() //必须调用该方法，防止内存泄漏
    }


}