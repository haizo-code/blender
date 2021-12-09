package com.haizo.poc.ui.viewholder.row

import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.poc.BR
import com.haizo.poc.callbacks.StoryActionCallback
import com.haizo.poc.databinding.RowStoryBinding
import com.haizo.poc.model.Story

class StoryViewHolder constructor(
    private val binding: RowStoryBinding,
    actionCallback: StoryActionCallback?
) : BaseBindingViewHolder<Story>(binding, actionCallback) {

    init {
        binding.imageView.setOnClickListener {
            actionCallback?.onStoryClicked(listItem)
        }
    }

    override fun onBind(listItem: Story) {
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}