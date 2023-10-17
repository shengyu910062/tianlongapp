/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.language.fragment

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.shengyu.tianlong.R
import com.shengyu.tianlong.browse.fragment.AttractionListViewModel
import com.shengyu.tianlong.databinding.DialogLanguageChooseBinding
import com.shengyu.tianlong.language.adapter.LanguageAdapter
import com.shengyu.tianlong.splash.SplashActivity
import com.shengyu.tianlong.util.SharePreference

/**
 * DialogFragment for choose language
 */
class LanguageChooseDialogFragment : DialogFragment() {
    private lateinit var mBinding: DialogLanguageChooseBinding
    private val mParentViewModel: AttractionListViewModel by activityViewModels()
    private val mLanguageAdapter: LanguageAdapter by lazy {
        LanguageAdapter {
            mParentViewModel.mCurrentLanguage.postValue(it.id)
            if (SharePreference.USE_LOCAL_LANGUAGE_SETTING) {
                SharePreference.localLanguage = it.id
            } else {
                // TODO: if have server side setting, add here
            }
            dismiss()
            startActivity(Intent(requireContext(), SplashActivity::class.java))
        }
    }

    override fun onCreateView(
        aInflater: LayoutInflater,
        aContainer: ViewGroup?,
        aSavedInstanceState: Bundle?
    ): View {
        mBinding = DialogLanguageChooseBinding.inflate(aInflater, aContainer, false)
        mBinding.apply {
            languageList.scrollToPosition(mLanguageAdapter.getInitPosition(mParentViewModel.mCurrentLanguage.value!!))
            languageList.adapter = mLanguageAdapter
            clLanguageChooserLayout.setOnClickListener {
                dismiss()
            }
        }

        return mBinding.root
    }

    override fun onCreate(aSavedInstanceState: Bundle?) {
        super.onCreate(aSavedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.TranslucentDialog)
    }

    override fun onCreateDialog(aSavedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(aSavedInstanceState)

        if (aSavedInstanceState == null) {
            dialog.window?.setWindowAnimations(
                R.style.Theme_TianLong_CustomDialogFragment
            )
        } else {
            dialog.window?.setWindowAnimations(
                R.style.Theme_TianLong_CustomDialogFragment_Restore
            )
        }

        dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(dialog: DialogInterface, keyCode: Int, event: KeyEvent): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.action == KeyEvent.ACTION_UP) {
                        dismiss()
                    }
                    return true
                }
                return false
            }
        })
        return dialog
    }
}