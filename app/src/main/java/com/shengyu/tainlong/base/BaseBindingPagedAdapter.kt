/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tainlong.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Binding PagedAdapter: define view binding for paging adapter
 */
abstract class BaseBindingPagedAdapter<M : Any, VDB : ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<M>) :
    PagingDataAdapter<M, BaseBindingPagedAdapter.BaseBindingViewHolder>(diffCallback) {

    class BaseBindingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val binding: VDB =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                this.getLayoutResId(viewType),
                parent,
                false
            )
        return BaseBindingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val binding: VDB? = DataBindingUtil.getBinding(holder.itemView)
        this.onBindItem(binding, getItem(position)!!, position)
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    protected abstract fun onBindItem(binding: VDB?, item: M, position: Int)
}