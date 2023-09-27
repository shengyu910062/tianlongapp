/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shengyu.tainlong.util.dp
import com.shengyu.tainlong.util.px

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Extension Instrumented test
 */
@RunWith(AndroidJUnit4::class)
class ExtensionInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.shengyu.tainlong", appContext.packageName)
    }

    @Test
    fun floatDotPxIsCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(10f.px, 10f * appContext.resources.displayMetrics.density)

    }

    @Test
    fun intDotDpIsCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals(10.dp, 10f / appContext.resources.displayMetrics.density)
    }
}