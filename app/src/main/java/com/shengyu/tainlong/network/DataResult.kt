/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.network

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
sealed class DataResult<out T> {

    data class Success<T>(val response: T) : DataResult<T>()
    data class Error(val error: String) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$response]"
            is Error -> "Error[exception=$error]"
        }
    }
}
