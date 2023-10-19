/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.network

import com.shengyu.tianlong.network.model.Attractions
import com.shengyu.tianlong.util.Logger
import com.shengyu.tianlong.util.TAG
import com.shengyu.tianlong.util.getDataResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * For API operation
 */
class ApiManager {

    companion object {
        const val TIANLONG_BASE_URL = "https://www.travel.taipei/open-api/"
        const val API_TIMEOUT_LIMIT = 30L
    }

    private val apiService: ApiService

    init {
        var logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Logger.i(TAG, message)
            }
        })
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(API_TIMEOUT_LIMIT, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(TIANLONG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    val API: ApiService
        get() = apiService

    suspend fun getAttractions(lang: String, page: Int): DataResult<Attractions> {
        return getDataResult(apiService.getAttractions(lang, page))
    }
}