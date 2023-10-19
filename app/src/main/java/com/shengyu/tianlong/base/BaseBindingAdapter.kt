/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Base Binding Adapter: define view binding for adapter
 */
abstract class BaseBindingAdapter<M, VDB : ViewDataBinding> :
    RecyclerView.Adapter<BaseBindingAdapter.BaseBindingViewHolder>() {
    var adapterList: ArrayList<M> = arrayListOf()

    /**
     * Append items to adapter list
     */
    open fun addList(list: ArrayList<M>) {
        val startPosition = adapterList.size
        adapterList.addAll(list)
        notifyItemRangeInserted(startPosition, adapterList.size)
    }

    /**
     * Setup adapter list by list
     */
    open fun setList(list: List<M>) {
        clearList()
        adapterList.addAll(list)
        notifyItemRangeChanged(0, adapterList.count())
    }

    /**
     * Clear all items from adapter list
     */
    open fun clearList() {
        val previousSize = adapterList.size
        adapterList.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    /**
     * Remove specific items from adapter list
     */
    open fun remove(position: Int) {
        if (adapterList.size > position) {
            adapterList.removeAt(position)
        }
    }

    /**
     * Fetch adapter list
     */
    fun getList(): ArrayList<M> {
        return adapterList
    }

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
        this.onBindItem(binding, adapterList[position], position)
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    protected abstract fun onBindItem(binding: VDB?, item: M, position: Int)
}