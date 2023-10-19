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

    lateinit var binding: VDB
    private val loadingFragment: LoadingFragment by lazy {
        LoadingFragment.newInstance()
    }

    /**
     * assign layout
     */
    @LayoutRes
    protected abstract fun getLayoutId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
    }

    /**
     * Hide loading
     */
    protected fun hideLoadingDialog() {
        if (loadingFragment.dialog?.isShowing == true) {
            try {
                loadingFragment.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * show loading
     */
    protected fun showLoadingDialog(): Boolean {
        if (loadingFragment.dialog?.isShowing == true || loadingFragment.isVisible || loadingFragment.isAdded) {
            return true
        }
        supportFragmentManager.beginTransaction().remove(loadingFragment).commit()
        loadingFragment.show(supportFragmentManager, TAG)
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