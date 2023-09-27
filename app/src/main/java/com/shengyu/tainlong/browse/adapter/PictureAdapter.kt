/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.browse.adapter

import com.shengyu.tainlong.R
import com.shengyu.tainlong.base.BaseBindingAdapter
import com.shengyu.tainlong.databinding.ItemPictureBinding
import com.shengyu.tainlong.network.model.Image

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