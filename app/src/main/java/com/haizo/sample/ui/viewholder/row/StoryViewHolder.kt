package com.haizo.sample.ui.viewholder.row

import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.BR
import com.haizo.sample.callbacks.StoryActionCallback
import com.haizo.sample.databinding.RowStoryBinding
import com.haizo.sample.model.Story

class StoryViewHolder constructor(
    private val binding: RowStoryBinding,
    actionCallback: StoryActionCallback?
) : BaseBindingViewHolder<Story>(binding, actionCallback) {

    init {
        binding.imageView.setOnClickListener { actionCallback?.onStoryClicked(listItem) }
    }

    override fun onBind(listItem: Story) {
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}