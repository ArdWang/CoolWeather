package com.clw.ui.activity

/*
 *  Copyright © 2018 Radiance Instruments Ltd. All rights reserved.
 *  author ArdWang
 *  email 278161009@qq.com
 *  Created by ArdWang on 7/31/19.
 */


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.support.v7.app.AppCompatActivity

import com.clw.R

class WelcomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
            finish()
        }, TIMES.toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        private val TIMES = 5000
    }


}
