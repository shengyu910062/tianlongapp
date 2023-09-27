/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.splash

import com.shengyu.tainlong.base.BaseViewModel
import com.shengyu.tainlong.network.ApiManager
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