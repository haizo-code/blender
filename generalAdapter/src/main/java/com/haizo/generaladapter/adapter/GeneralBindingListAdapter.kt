/*
 * Copyright 2020 Farouq Afghani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haizo.generaladapter.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ItemTypesPool
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.generaladapter.viewholders.BlankViewHolder
import kotlin.math.roundToInt

@Suppress("UNCHECKED_CAST")
open class GeneralBindingListAdapter constructor(
    val context: Context?,
    var actionCallback: BaseActionCallback? = null
) : BaseRecyclerAdapter<ListItem, BaseBindingViewHolder<ListItem>>(context) {

    private var extraParams = ArrayList<Any?>()

    fun setExtraParams(vararg extraParams: Any) {
        this.extraParams.clear()
        this.extraParams.addAll(extraParams)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ListItem> {
        val listItemType = ItemTypesPool.getItemType(viewType)
        val binding = getViewDataBinding(listItemType, parent)
        setupViewWidth(binding)
        try {
            val mainConstructor = listItemType.viewHolderClass.constructors[0]
            return when (mainConstructor.parameterTypes.size) {
                1 -> mainConstructor.newInstance(binding) as BaseBindingViewHolder<ListItem>
                2 -> mainConstructor.newInstance(binding, actionCallback) as BaseBindingViewHolder<ListItem>
                else -> mainConstructor.newInstance(binding, actionCallback,
                    *extraParams.toTypedArray()) as BaseBindingViewHolder<ListItem>
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return BlankViewHolder(binding, actionCallback)
    }

    private fun setupViewWidth(binding: ViewDataBinding) {
        if (mItemWidth != null) binding.root.layoutParams.width = (mItemWidth!!).roundToInt()
    }

    private fun getViewDataBinding(listItemType: ListItemType, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate(mInflater, listItemType.layoutResId, parent, false)
    }

    override fun onBindViewHolder(bindingViewHolder: BaseBindingViewHolder<ListItem>, position: Int) {
        bindingViewHolder.draw(mItems[position])
    }

    override fun onViewAttachedToWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        super.onViewAttachedToWindow(holderBinding)
        holderBinding.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        holderBinding.onViewDetachedFromWindow()
        super.onViewDetachedFromWindow(holderBinding)
    }

    override fun onViewRecycled(holder: BaseBindingViewHolder<ListItem>) {
        holder.onViewRecycled()
        super.onViewRecycled(holder)
    }
}