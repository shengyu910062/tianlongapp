/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.browse.fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.activityViewModels
import com.shengyu.tainlong.R
import com.shengyu.tainlong.base.BaseFragment
import com.shengyu.tainlong.browse.adapter.PictureAdapter
import com.shengyu.tainlong.common.ViewLinkActivity
import com.shengyu.tainlong.databinding.FragmentAttractionDetailBinding
import com.shengyu.tainlong.util.Logger
import com.shengyu.tainlong.util.TAG
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment for view specific attraction detail
 */
@AndroidEntryPoint
class AttractionDetailFragment : BaseFragment<FragmentAttractionDetailBinding>() {

    companion object {
        fun newInstance() = AttractionDetailFragment()
        const val DETAIL_BOTTOM_HINT_DURATION = 1500L
    }

    private val mViewModel: AttractionListViewModel by activityViewModels()
    private val mPictureAdapter: PictureAdapter by lazy { PictureAdapter() }

    override fun getLayoutId(): Int {
        return R.layout.fragment_attraction_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(mBinding) {
            rvDetailPictures.adapter = mPictureAdapter

            viewModel = mViewModel
            setLoadingObserver(mViewModel)

            nsvDetail.viewTreeObserver.addOnDrawListener {
                updateCanScrollState()
            }

            val animator = ValueAnimator.ofInt(0, -25, 0)
            animator.apply {
                addUpdateListener {
                    ivDetailScrollHint.translationY = (animator.animatedValue as Int).toFloat()
                    ivDetailScrollHint.requestLayout()
                }
                repeatMode = ValueAnimator.RESTART
                repeatCount = ValueAnimator.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
                duration = DETAIL_BOTTOM_HINT_DURATION
                start()
            }
        }

        with(mViewModel) {
            // data observer
            attractionDetail.observe(viewLifecycleOwner) {
                it?.apply {
                    Logger.d(
                        TAG,
                        "attractionDetail UPDATE, setup images, list size" + it.images.size
                    )
                    mPictureAdapter.setList(
                        it.images
                    )
                }
            }

            // click listener
            clickLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    R.id.tv_detail_url_content -> {
                        startActivity(Intent(requireContext(), ViewLinkActivity::class.java).apply {
                            putExtra(
                                ViewLinkActivity.KEY_LINK_URL,
                                mViewModel.attractionDetail.value?.url
                            )
                        })
                    }
                }
            }
        }
    }

    /**
     * Calculate the height of the scrolled content
     * to determine whether the screen can be scrolled
     */
    private fun updateCanScrollState() {
        val contentHeight = mBinding.nsvDetail.getChildAt(0).height
        mViewModel.attractionDetailCanScroll.value =
            contentHeight > mBinding.nsvDetail.scrollY + mBinding.nsvDetail.height
    }
}