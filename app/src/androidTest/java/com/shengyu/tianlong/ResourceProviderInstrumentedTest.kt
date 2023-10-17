/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shengyu.tianlong.util.ResourceProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ResourceProvider Instrumented test
 */
@RunWith(AndroidJUnit4::class)
class ResourceProviderInstrumentedTest {
    @Test
    fun getContextIsCorrect() {
        assertNotNull(ResourceProvider.getContext())
    }

    @Test
    fun getColorIsCorrect() {
        assertNotNull(ResourceProvider.getColor(R.color.tv_white))
    }

    @Test
    fun getDrawableIsCorrect() {
        assertNotNull(ResourceProvider.getDrawable(R.drawable.back_left_icon))
    }
}