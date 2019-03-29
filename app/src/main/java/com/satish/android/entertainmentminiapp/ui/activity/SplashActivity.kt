package com.satish.android.entertainmentminiapp.ui.activity

/**
 *
 * @author satish
 * 27/03/2019
 *
 * **/

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTopHeadingNewsActivity()
    }

    private fun startTopHeadingNewsActivity() {
        HomeActivity.startActivity(this@SplashActivity)
        finish()
    }
}