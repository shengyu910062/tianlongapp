/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.util

import android.content.res.Resources
import android.os.Looper
import android.widget.Toast
import com.shengyu.tainlong.R
import com.shengyu.tainlong.network.DataResult
import retrofit2.Response

const val TAG = "Extension"

val Any.TAG: String
    get() = this::class.java.simpleName

/**
 * Get Data Result
 * @param T: response class
 */
inline fun <reified T> getDataResult(response: Response<T>): DataResult<T> {
    return try {
        if (response.isSuccessful) {
            DataResult.Success<T>(response.body()!!)
        } else {
            DataResult.Error(response.errorBody()!!.toString())
        }
    } catch (e: Exception) {
        DataResult.Error(e.toString())
    }.apply {
        if (this is DataResult.Error) {
            Logger.d(TAG, "API error - ${this.error}")
        }
        Logger.d(
            TAG,
            "----------------------- 💛 api data start 💛 -----------------------\n${this}\n ------------------- 💛 data end 💛 -------------------"
        )
    }
}

/**
 * Handle response from API
 */
fun <T : Any> apiDo(
    data: DataResult<T?>,
    successDo: ((successObj: T) -> Unit)? = null,
    failDo: ((error: String) -> Unit)? = null
) {
    when (data) {
        is DataResult.Error -> {
            failDo?.let { it(data.error) } ?: run {
                Looper.prepare()
                Toast.makeText(
                    ResourceProvider.getContext(),
                    R.string.api_error,
                    Toast.LENGTH_SHORT
                ).show()
                Looper.loop()
            }
        }

        is DataResult.Success -> {
            successDo?.let { data.response?.let { response -> it(response) } }
        }
    }
}

/**
 * integer value to dp
 */
val Int.dp: Float get() = (this / Resources.getSystem().displayMetrics.density)

/**
 * float value to px
 */
val Float.px: Float get() = (this * Resources.getSystem().displayMetrics.density)

val screenWidth: Int get() = Resources.getSystem().displayMetrics.widthPixels

val screenHeight: Int get() = Resources.getSystem().displayMetrics.heightPixels