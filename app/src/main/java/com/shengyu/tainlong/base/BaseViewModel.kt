/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.base

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * Base ViewModel: define common scope & loading
 */
open class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()

    // Observe the click event in the view
    val clickObservableField = ObservableField<View.OnClickListener>()
    val clickLiveData = MutableLiveData<Int>()
    private val onClickListener = View.OnClickListener {
        clickLiveData.value = it.id
    }
    private var singleJob: Job? = null

    init {
        clickObservableField.set(onClickListener)
    }

    /**
     * Scope with loading screen
     */
    protected fun scope(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loading.postValue(true)
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                loading.postValue(false)
            } finally {
                loading.postValue(false)
            }
        }
    }

    /**
     * If there is a next call, cancel the last call to avoid the screen that displays the last result.
     * It is usually used on the list page.
     */
    protected fun singleScope(showLoading: Boolean? = true, block: suspend () -> Unit) {
        singleJob?.cancel()
        singleJob = viewModelScope.launch(Dispatchers.IO) {
            try {
                if (showLoading == true) {
                    loading.postValue(true)
                }
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                loading.postValue(false)
            } finally {
                loading.postValue(false)
            }
        }
    }

    /**
     * requestScope for request api and do not response to ui.
     * example: upload workout recode.
     */
    protected fun requestScope(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
