/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.base

import android.app.Application
import com.shengyu.tianlong.util.ResourceProvider
import com.shengyu.tianlong.util.SharePreference
import dagger.hilt.android.HiltAndroidApp

/**
 * MainApplication for Hilt
 */
@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ResourceProvider.createInstance(this)
        SharePreference.createInstance(this)
    }
}