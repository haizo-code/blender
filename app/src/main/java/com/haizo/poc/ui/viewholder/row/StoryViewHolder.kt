package com.haizo.poc.ui.viewholder.row

import com.haizo.generaladapter.callbacks.BaseActionCallback
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.databinding.RowStoryBinding
import com.haizo.poc.model.StoryModel

class StoryViewHolder(private val viewDataBinding: RowStoryBinding, actionCallback: BaseActionCallback?) :
    BaseBindingViewHolder<StoryModel>(viewDataBinding, actionCallback) {

    init {
        attachClickListener(itemView)
    }

    override fun onBind(listItem: StoryModel) {
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}