/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.base

import android.app.Application
import com.shengyu.tainlong.util.ResourceProvider
import com.shengyu.tainlong.util.SharePreference
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