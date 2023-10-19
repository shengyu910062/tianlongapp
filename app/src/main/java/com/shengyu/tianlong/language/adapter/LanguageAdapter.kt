/*******************************************************************************
 *  Copyright (C) 2023 by shengyu.
 ******************************************************************************/

package com.shengyu.tianlong.language.adapter

import com.shengyu.tianlong.R
import com.shengyu.tianlong.base.BaseBindingAdapter
import com.shengyu.tianlong.databinding.ItemLanguageBinding
import com.shengyu.tianlong.network.model.BaseConfigItem
import com.shengyu.tianlong.util.Logger
import com.shengyu.tianlong.util.TAG

/**
 * Adapter for language list
 *
 * @param clickDo callback when click on list
 */
class LanguageAdapter(val clickDo: (model: BaseConfigItem) -> Unit) :
    BaseBindingAdapter<BaseConfigItem, ItemLanguageBinding>() {
    private var initPosition: Int = 0 // Record current language position

    init {
        setList(getLanguageList())
    }

    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.item_language
    }

    override fun onBindItem(
        binding: ItemLanguageBinding?,
        item: BaseConfigItem,
        position: Int
    ) {
        binding?.apply {
            language = item.name
            root.setOnFocusChangeListener { _, hasFocus ->
                focus = hasFocus
            }
            root.setOnClickListener { clickDo(item) }
        }
    }

    /* Focus on current language item */
    override fun onViewAttachedToWindow(aHolder: BaseBindingViewHolder) {
        super.onViewAttachedToWindow(aHolder)
        if (aHolder.bindingAdapterPosition == initPosition) {
            aHolder.itemView.requestFocus()
        }
    }

    /* Get current language position at list, and record it */
    fun getInitPosition(aSettingLanguage: String): Int {
        adapterList.forEachIndexed { i, language ->
            if (language.id == aSettingLanguage) {
                Logger.d(TAG, "Init language : $aSettingLanguage , index$i")
                initPosition = i
                return i
            }
        }
        return 0
    }

    private fun getLanguageList() = mutableListOf<BaseConfigItem>().apply {
        LanguageCode.values().forEach { language ->
            add(BaseConfigItem(language.code, language.textShow))
        }
    } as List<BaseConfigItem>
}