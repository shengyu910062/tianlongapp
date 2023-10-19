/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.shengyu.tianlong.common.LoadingFragment

/**
 * Base Fragment: define view binding & common loading screen
 */
abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    lateinit var binding: VDB
    private val loadingFragment: LoadingFragment by lazy {
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
        binding = DataBindingUtil.bind(rootView)!!

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * Hide loading
     */
    fun hideLoadingDialog() {
        if (loadingFragment.dialog?.isShowing == true) {
            try {
                loadingFragment.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Show loading
     */
    fun showLoadingDialog() {
        if (loadingFragment.dialog?.isShowing == true || loadingFragment.isVisible || loadingFragment.isAdded) {
            return
        }
        loadingFragment.show(childFragmentManager, "childDialog")
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