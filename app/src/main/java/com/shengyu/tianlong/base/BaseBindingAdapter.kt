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
    var mList: ArrayList<M> = arrayListOf()

    /**
     * Append items to adapter list
     */
    open fun addList(list: ArrayList<M>) {
        val startPosition = mList.size
        mList.addAll(list)
        notifyItemRangeInserted(startPosition, mList.size)
    }

    /**
     * Setup adapter list by list
     */
    open fun setList(list: List<M>) {
        clearList()
        mList.addAll(list)
        notifyItemRangeChanged(0, mList.count())
    }

    /**
     * Clear all items from adapter list
     */
    open fun clearList() {
        val previousSize = mList.size
        mList.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    /**
     * Remove specific items from adapter list
     */
    open fun remove(position: Int) {
        if (mList.size > position) {
            mList.removeAt(position)
        }
    }

    /**
     * Fetch adapter list
     */
    fun getList(): ArrayList<M> {
        return mList
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
        this.onBindItem(binding, this.mList[position], position)
    }

    override fun getItemCount(): Int {
        return this.mList.size
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    protected abstract fun onBindItem(binding: VDB?, item: M, position: Int)
}