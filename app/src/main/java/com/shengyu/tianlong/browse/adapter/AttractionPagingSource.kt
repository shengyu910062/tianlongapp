/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.browse.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shengyu.tianlong.network.ApiManager
import com.shengyu.tianlong.network.model.Data
import com.shengyu.tianlong.util.apiDo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Fetch attractions by paging
 *
 * @param apiManager For get backend data
 * @param lang language code to API
 */
class AttractionPagingSource(
    private val apiManager: ApiManager,
    val lang: String
) : PagingSource<Int, Data>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Data> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1

            return suspendCoroutine { loadResult ->
                CoroutineScope(Dispatchers.IO).launch {
                    apiManager.getAttractions(lang, nextPageNumber).apply {
                        apiDo(this, { response ->
                            loadResult.resume(
                                LoadResult.Page(
                                    data = response.data,
                                    prevKey = null, // Only paging forward.
                                    nextKey = nextPageNumber + 1
                                )
                            )
                        })
                    }
                }
            }
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}