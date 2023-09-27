/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.browse.adapter

import androidx.recyclerview.widget.DiffUtil
import com.shengyu.tainlong.network.model.Data

/**
 * Comparator for attraction and attraction
 */
object AttractionComparator : DiffUtil.ItemCallback<Data>() {
    override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
        return oldItem == newItem
    }
}