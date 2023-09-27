/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.shengyu.tainlong.common.LoadingFragment

/**
 * Base Fragment: define view binding & common loading screen
 */
abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    lateinit var mBinding: VDB
    private val mLoadingFragment: LoadingFragment by lazy {
        LoadingFragment.newInstance()
    }

    /**
     * assign layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutId(), container, false)
        mBinding = DataBindingUtil.bind(rootView)!!

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * Hide loading
     */
    fun hideLoadingDialog() {
        if (mLoadingFragment.dialog?.isShowing == true) {
            try {
                mLoadingFragment.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Show loading
     */
    fun showLoadingDialog() {
        if (mLoadingFragment.dialog?.isShowing == true || mLoadingFragment.isVisible || mLoadingFragment.isAdded) {
            return
        }
        mLoadingFragment.show(childFragmentManager, "childDialog")
    }

    /**
     * setup loading observer
     */
    protected fun setLoadingObserver(viewModel: BaseViewModel) {
        viewModel.loading.observe(this) {
            if (it) {
                showLoadingDialog()
            } else {
                hideLoadingDialog()
            }
        }
    }
}