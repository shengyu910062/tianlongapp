/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.base

import com.shengyu.tianlong.network.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * AppModule for ApiManager inject
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiManager(): ApiManager {
        return ApiManager()
    }
}