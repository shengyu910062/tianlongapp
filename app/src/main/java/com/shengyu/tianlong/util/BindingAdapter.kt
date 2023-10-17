/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.shengyu.tianlong.R

/**
 * Define view Binding common function
 */
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imgUrlRound")
    fun bindImgUrlRound(iv: ImageView, url: String?) {
        GlideApp.with(iv.context)
            .load(url ?: "")
            .placeholder(R.drawable.image_loading_icon)
            .transform(CenterCrop(), GranularRoundedCorners(7.5f.px, 7.5f.px, 7.5f.px, 7.5f.px))
            .error(R.drawable.no_image_icon)
            .into(iv)
    }

    @JvmStatic
    @BindingAdapter("imgUrlRoundLeft")
    fun bindImgUrlRoundLeft(iv: ImageView, url: String?) {
        GlideApp.with(iv.context)
            .load(url ?: "")
            .placeholder(R.drawable.image_loading_icon)
            .transform(CenterCrop(), GranularRoundedCorners(7.5f.px, 0f, 0f, 7.5f.px))
            .error(R.drawable.no_image_icon)
            .into(iv)
    }

    @JvmStatic
    @BindingAdapter("bigImgUrl")
    fun bindBigImgUrl(iv: ImageView, url: String?) {
        GlideApp.with(iv.context)
            .load(url ?: "")
            .placeholder(R.drawable.image_loading_icon)
            .transform(CenterCrop())
            .error(R.drawable.big_no_image_icon)
            .into(iv)
    }
}
