/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import com.shengyu.tianlong.common.LoadingFragment
import com.shengyu.tianlong.util.LocaleHelper
import com.shengyu.tianlong.util.SharePreference
import com.shengyu.tianlong.util.TAG

/**
 * Base Activity: define view binding & common loading screen
 */
abstract class BaseActivity<VDB : ViewDataBinding> : FragmentActivity() {

    lateinit var mBinding: VDB
    private val mLoadingFragment: LoadingFragment by lazy {
        LoadingFragment.newInstance()
    }

    /**
     * assign layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mBinding.lifecycleOwner = this
    }

    /**
     * Hide loading
     */
    protected fun hideLoadingDialog() {
        if (mLoadingFragment.dialog?.isShowing == true) {
            try {
                mLoadingFragment.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * show loading
     */
    protected fun showLoadingDialog(): Boolean {
        if (mLoadingFragment.dialog?.isShowing == true || mLoadingFragment.isVisible || mLoadingFragment.isAdded) {
            return true
        }
        supportFragmentManager.beginTransaction().remove(mLoadingFragment).commit()
        mLoadingFragment.show(supportFragmentManager, TAG)
        return false
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

    /**
     * setup UI language
     */
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase!!, SharePreference.language))
    }
}