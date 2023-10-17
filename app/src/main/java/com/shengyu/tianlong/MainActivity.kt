/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.shengyu.tianlong.base.BaseActivity
import com.shengyu.tianlong.browse.fragment.AttractionDetailFragment
import com.shengyu.tianlong.browse.fragment.AttractionListFragment
import com.shengyu.tianlong.browse.fragment.AttractionListViewModel
import com.shengyu.tianlong.databinding.ActivityMainBinding
import com.shengyu.tianlong.language.fragment.LanguageChooseDialogFragment
import com.shengyu.tianlong.util.Logger
import com.shengyu.tianlong.util.TAG
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main page
 * Loads [AttractionListFragment], [AttractionDetailFragment], [LanguageChooseDialogFragment]
 **/
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mViewModel: AttractionListViewModel by viewModels()
    private lateinit var mChildFragmentManager: FragmentManager
    private lateinit var mAttractionListFragment: AttractionListFragment

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mChildFragmentManager = supportFragmentManager
        mBinding.viewModel = mViewModel
        mAttractionListFragment = AttractionListFragment.newInstance()

        with(mViewModel) {
            setLoadingObserver(this)

            // data observer
            attractionDetail.observe(this@MainActivity) { attraction ->
                if (attraction == null) {
                    Logger.d(TAG, "Enter Attraction List Mode")
                    mChildFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.slide_out,
                        R.anim.slide_in,
                        R.anim.fade_out
                    ).replace(R.id.main_browse_fragment, mAttractionListFragment).commit()
                } else {
                    Logger.d(TAG, "Enter Attraction Detail, id : " + attraction.id)
                    mChildFragmentManager.beginTransaction().setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    ).replace(R.id.main_browse_fragment, AttractionDetailFragment.newInstance())
                        .commit()
                }
            }

            // click observer
            clickLiveData.observe(this@MainActivity) {
                when (it) {
                    R.id.ib_language_chooser -> {
                        LanguageChooseDialogFragment().apply {
                            show(mChildFragmentManager, TAG)
                        }
                    }

                    R.id.ib_toolbar_back -> {
                        attractionDetail.postValue(null)
                    }
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mViewModel.attractionDetail.value != null) {
            mViewModel.attractionDetail.postValue(null)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}