/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.shengyu.tainlong.MainActivity
import com.shengyu.tainlong.R
import com.shengyu.tainlong.base.BaseActivity
import com.shengyu.tainlong.databinding.ActivitySplashBinding
import com.shengyu.tainlong.util.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint

/**
 * Splash screen for APP
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    // TODO call mViewModel to do API config in the future
    private val mViewModel: SplashViewModel by viewModels()

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ResourceProvider.refreshLanguage().apply {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
            finish()
        }
    }
}