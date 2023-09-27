/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong

import com.shengyu.tainlong.network.ApiManager
import com.shengyu.tainlong.network.DataResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * ApiManager unit test.
 */
class ApiManagerUnitTest {

    @Test
    fun getAttractionsIsCorrect() {
        var mApiManager = ApiManager()
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