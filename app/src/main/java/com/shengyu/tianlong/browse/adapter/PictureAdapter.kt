/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.browse.adapter

import com.shengyu.tianlong.R
import com.shengyu.tianlong.base.BaseBindingAdapter
import com.shengyu.tianlong.databinding.ItemPictureBinding
import com.shengyu.tianlong.network.model.Image

/**
 * Adapter for attraction images
 */
class PictureAdapter : BaseBindingAdapter<Image, ItemPictureBinding>() {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_picture
    }

    override fun onBindItem(binding: ItemPictureBinding?, item: Image, position: Int) {
        binding?.apply {
            model = item
        }
    }
}