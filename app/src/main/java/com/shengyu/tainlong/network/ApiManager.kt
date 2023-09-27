/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.network

import com.shengyu.tainlong.network.model.Attractions
import com.shengyu.tainlong.util.Logger
import com.shengyu.tainlong.util.TAG
import com.shengyu.tainlong.util.getDataResult
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
        const val TAINLONG_BASE_URL = "https://www.travel.taipei/open-api/"
        const val API_TIMEOUT_LIMIT = 30L
    }

    private val mApiService: ApiService

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
            .baseUrl(TAINLONG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        mApiService = retrofit.create(ApiService::class.java)
    }

    val API: ApiService
        get() = mApiService

    suspend fun getAttractions(lang: String, page: Int): DataResult<Attractions> {
        return getDataResult(mApiService.getAttractions(lang, page))
    }
}