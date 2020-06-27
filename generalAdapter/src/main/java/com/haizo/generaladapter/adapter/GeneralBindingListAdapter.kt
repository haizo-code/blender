package com.haizo.generaladapter.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ItemTypesPool
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import kotlin.math.roundToInt

class GeneralBindingListAdapter(context: Context?, var listItemCallback: ListItemCallback? = null)
    : BaseRecyclerAdapter<ListItem, BaseBindingViewHolder<ListItem>>(context) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder<ListItem> {
        val listItemType = ItemTypesPool.getItemType(viewType)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(mInflater, listItemType.layoutResId, parent, false)
        if (itemWidth != null) binding.root.layoutParams.width = (itemWidth!!).roundToInt()
        try {
            val types = arrayOf(ViewDataBinding::class.java, ListItemCallback::class.java)
            val cons = listItemType.viewHolderClass.getConstructor(*types)
            return cons.newInstance(binding, listItemCallback) as BaseBindingViewHolder<ListItem>
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return BaseBindingViewHolder(binding, listItemCallback)
    }

    override fun onBindViewHolder(bindingViewHolder: BaseBindingViewHolder<ListItem>, position: Int) {
        bindingViewHolder.draw(mItems[position])
    }

    override fun onViewAttachedToWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        super.onViewAttachedToWindow(holderBinding)
        holderBinding.onViewAttachedToWindow()
    }

    override fun onViewDetachedFromWindow(holderBinding: BaseBindingViewHolder<ListItem>) {
        super.onViewDetachedFromWindow(holderBinding)
        holderBinding.onViewDetachedFromWindow()
    }
}