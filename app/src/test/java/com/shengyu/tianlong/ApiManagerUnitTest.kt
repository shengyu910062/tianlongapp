/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong

import com.shengyu.tianlong.network.ApiManager
import com.shengyu.tianlong.network.DataResult
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * ApiManager unit test.
 */
class ApiManagerUnitTest {

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun getAttractionsIsCorrect() {
        val mApiManager = ApiManager()
        GlobalScope.launch {
            mApiManager.getAttractions("zh-tw", 1).apply {
                when (this) {
                    is DataResult.Success -> {
                        assert(true)
                        assertNotNull(this.response.data)
                    }

                    is DataResult.Error -> {
                        assert(false)
                    }
                }
            }
        }
    }
}