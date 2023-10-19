/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.browse.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.shengyu.tianlong.base.BaseViewModel
import com.shengyu.tianlong.browse.adapter.AttractionPagingSource
import com.shengyu.tianlong.network.ApiManager
import com.shengyu.tianlong.network.model.Data
import com.shengyu.tianlong.util.SharePreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for attractions, using by [AttractionListFragment], [AttractionDetailFragment], [MainActivity], [LanguageChooseDialogFragment]
 *
 * @param apiManager For get backend data
 */
@HiltViewModel
class AttractionListViewModel @Inject constructor(
    private val apiManager: ApiManager
) : BaseViewModel() {
    val currentLanguage = MutableLiveData(SharePreference.language)

    val attractionDataEmpty = MutableLiveData<Boolean?>(null)
    val attractionDetail = MutableLiveData<Data?>(null)
    val attractionDetailCanScroll = MutableLiveData(false)

    val flow = Pager(
        PagingConfig(pageSize = 30, enablePlaceholders = true)
    ) {
        AttractionPagingSource(apiManager, toApiUnderline(SharePreference.language))
    }.flow.cachedIn(viewModelScope)

    fun fetchAttractions(callback: suspend () -> Unit) {
        requestScope {
            callback()
        }
    }

    /*
     * Due to Open-Api language code style is not match Android system, need replace underline
     * eq: Open-APi => zh-tw  ,    Android system => zh_tw
     */
    private fun toApiUnderline(localeText: String): String {
        return localeText.replace("_", "-")
    }
}