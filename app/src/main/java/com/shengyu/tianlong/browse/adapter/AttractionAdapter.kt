/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.browse.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shengyu.tianlong.R
import com.shengyu.tianlong.base.BaseBindingPagedAdapter
import com.shengyu.tianlong.databinding.ItemAttractionBinding
import com.shengyu.tianlong.network.model.Data

/**
 * Adapter for keep attractions
 *
 * @param onItemClick callback when click attraction on list
 * @param diffCallback Callback for calculating the diff between two non-null items in a list.
 */
class AttractionAdapter(
    val onItemClick: (data: Data) -> Unit,
    diffCallback: DiffUtil.ItemCallback<Data>
) :
    BaseBindingPagedAdapter<Data, ItemAttractionBinding>(diffCallback) {

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_attraction
    }

    override fun onBindItem(binding: ItemAttractionBinding?, item: Data, position: Int) {
        binding?.apply {
            model = item
            root.setOnClickListener { onItemClick(item) }
        }
    }
}