/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.base

import com.shengyu.tainlong.network.ApiManager
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