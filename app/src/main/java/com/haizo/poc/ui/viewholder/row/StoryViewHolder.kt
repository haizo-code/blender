package com.haizo.poc.ui.viewholder.row

import androidx.databinding.ViewDataBinding
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.model.StoryModel

class StoryViewHolder(private val viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<StoryModel>(viewDataBinding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: StoryModel) {
        super.draw(listItem)
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}