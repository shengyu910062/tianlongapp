/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.browse.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.shengyu.tianlong.R
import com.shengyu.tianlong.base.BaseFragment
import com.shengyu.tianlong.browse.adapter.AttractionAdapter
import com.shengyu.tianlong.browse.adapter.AttractionComparator
import com.shengyu.tianlong.databinding.FragmentAttractionListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Fragment for view all attractions
 */
@AndroidEntryPoint
class AttractionListFragment : BaseFragment<FragmentAttractionListBinding>() {

    companion object {
        fun newInstance() = AttractionListFragment()
    }

    private val mViewModel: AttractionListViewModel by activityViewModels()

    private val mAdapter by lazy {
        AttractionAdapter({
            mViewModel.attractionDetail.postValue(it)
        }, AttractionComparator)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_attraction_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(mBinding) {
            viewModel = mViewModel
            rvAttractionList.adapter = mAdapter
        }

        with(mViewModel) {
            setLoadingObserver(this)

            // api observer
            fetchAttractions {
                // Fetch paging attractions data
                flow.collectLatest { pagingData ->
                    viewLifecycleOwner.lifecycleScope.launch {
                        mAdapter.loadStateFlow.collectLatest { loadStates ->
                            if (loadStates.refresh is LoadState.Loading) {
                                loading.postValue(true)
                            } else {
                                loading.postValue(false)
                            }
                        }
                    }

                    mAdapter.submitData(pagingData)
                    attractionDataEmpty.value = mAdapter.itemCount == 0
                }
            }
        }
    }
}