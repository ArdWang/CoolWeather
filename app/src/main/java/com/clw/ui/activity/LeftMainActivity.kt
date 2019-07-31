package com.clw.ui.activity

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import com.clw.R
import com.clw.base.ui.activity.BaseActivity
import com.clw.common.AppManager
import com.clw.ext.onClick
import com.clw.ui.fragment.sideslip.FeedBackFragment
import com.clw.utils.CacheUtil
import com.clw.widgets.CustomDialog
import kotlinx.android.synthetic.main.head_common_layout.*
import org.jetbrains.anko.toast

class LeftMainActivity :BaseActivity(){

    private var titleName: String? = null

    private var titleValue: Int = 0

    private var dialog:CustomDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leftmain)

        initView()
        initData()
    }

    private fun initView(){
        mBarBack.onClick {
            finish()
        }
    }

    private fun initData(){
        titleValue = intent.getIntExtra("titleValue",0)
        titleName = intent.getStringExtra("titleName")

        if(titleName!=null) {
            mTitleName.text =titleName
        }
        showCurrentFragment(titleValue)
    }

    private fun showCurrentFragment(index: Int) {
        val fragment: Fragment
        when (index) {
            1 -> {
                //执行夜间模式操作
                toast("夜间模式操作")
            }
            //清理手机存储空间
            2 -> {
                //直接进行清理
                //fragment = SpaceFragment()
                //mTitleName.text = titleName
                //supportFragmentManager.beginTransaction().replace(R.id.currentSet, fragment).commit()

                //创建对话框
                val total = CacheUtil.getTotalCacheSize(this)

                val builder = CustomDialog.Builder(this)
                builder.setTips("Tips")
                builder.setContent("Do you want to delete  $total  the data?")

                builder.setPositiveButton(R.string.confirm, DialogInterface.OnClickListener { dialogInterface, _ ->
                    //执行操作
                    dialogInterface.dismiss()
                    //确定删除的时候
                    CacheUtil.clearAllCache(this)

                    finish()

                }).setnegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialogInterface, _ ->
                    //执行操作
                    dialogInterface.dismiss()
                    finish()
                })

                dialog = builder.create()
                dialog!!.show()
                dialog!!.setCanceledOnTouchOutside(true)

            }

            //意见反馈
            3 -> {
                fragment = FeedBackFragment()
                mTitleName.text = titleName
                supportFragmentManager.beginTransaction().replace(R.id.currentSet, fragment).commit()
            }

            //退出所有界面
            4 -> {
               AppManager.instance.finishAllActivity()
            }
        }
    }
}