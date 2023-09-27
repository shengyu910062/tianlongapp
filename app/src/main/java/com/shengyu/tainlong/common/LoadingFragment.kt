/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.shengyu.tainlong.R
import com.shengyu.tainlong.databinding.FragmentLoadingBinding

/**
 * Common component for display loading
 */
class LoadingFragment : DialogFragment() {
    private lateinit var mBinding: FragmentLoadingBinding

    companion object {
        fun newInstance(): LoadingFragment {
            return LoadingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_loading, container, false)
        mBinding = DataBindingUtil.bind(rootView)!!
        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return rootView
    }

    override fun onResume() {
        super.onResume()
        mBinding.lavLoading.playAnimation()
    }

    override fun onPause() {
        super.onPause()
        mBinding.lavLoading.clearAnimation()
    }
}