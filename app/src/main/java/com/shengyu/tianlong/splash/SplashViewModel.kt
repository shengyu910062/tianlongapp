/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.splash

import com.shengyu.tianlong.base.BaseViewModel
import com.shengyu.tianlong.network.ApiManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Splash screen view model
 *
 * @param apiManager For get backend data
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val apiManager: ApiManager
) : BaseViewModel() {

    init {
        initConfig()
    }

    private fun initConfig() {
        // Todo: setup init config
    }
}