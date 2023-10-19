/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong

import com.shengyu.tianlong.network.ApiManager
import com.shengyu.tianlong.network.ApiService
import com.shengyu.tianlong.util.Logger
import com.shengyu.tianlong.util.TAG
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ApiService unit test.
 */
class ApiServiceUnitTest {

    @OptIn(DelicateCoroutinesApi::class)
    @Test
    fun getAttractionsIsCorrect() {
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.i(TAG, message)
            }
        })
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(ApiManager.API_TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiManager.TIANLONG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val mApiService = retrofit.create(ApiService::class.java)
        GlobalScope.launch {
            assertNotNull(mApiService.getAttractions("zh-tw", 1))
        }
    }
}