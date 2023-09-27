/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.common

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shengyu.tainlong.R
import com.shengyu.tainlong.base.BaseActivity
import com.shengyu.tainlong.databinding.ActivityViewLinkBinding
import com.shengyu.tainlong.util.ResourceProvider
import dagger.hilt.android.AndroidEntryPoint

/**
 * Common component for view URL content by webview
 * * parameters
 * * KEY_LINK_URL -> For get url from intent
 */
@AndroidEntryPoint
class ViewLinkActivity : BaseActivity<ActivityViewLinkBinding>() {

    companion object {
        const val KEY_LINK_URL = "key_link_url"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_view_link
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog()
        with(mBinding.webView) {
            setBackgroundColor(ResourceProvider.getColor(R.color.tv_bg))

            with(settings) {
                javaScriptEnabled = true
                domStorageEnabled = true
                setNeedInitialFocus(true)
            }

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(aView: WebView?, aUrl: String?) {
                    hideLoadingDialog()
                    super.onPageFinished(aView, aUrl)
                }

                override fun onPageStarted(
                    aView: WebView?,
                    aUrl: String?,
                    aFavicon: Bitmap?
                ) {
                    super.onPageStarted(aView, aUrl, aFavicon)
                    aView?.scrollTo(0, 0)
                }
            }

            webChromeClient = WebChromeClient()
            textAlignment = View.TEXT_ALIGNMENT_TEXT_START

            intent.getStringExtra(KEY_LINK_URL)?.let {
                loadUrl(it)
            }
        }

        with(mBinding) {
            ibToolbarBack.setOnClickListener {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    finish()
                }
            }
        }
    }

    // Trigger webview previous page
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}