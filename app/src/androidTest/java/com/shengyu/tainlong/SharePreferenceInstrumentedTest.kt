/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shengyu.tainlong.language.adapter.LanguageCode
import com.shengyu.tainlong.util.SharePreference
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * SharePreference Instrumented test
 */
@RunWith(AndroidJUnit4::class)
class SharePreferenceInstrumentedTest {
    @Test
    fun getLanguageIsCorrect() {
        var lang = SharePreference.language
        LanguageCode.values().forEach {
            if (it.code == lang) {
                assert(true)
                return
            }
        }
        assert(false)
    }
}