package com.haizo.sample.ui.viewholder

import com.haizo.generaladapter.model.ViewHolderContract
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder
import com.haizo.sample.BR
import com.haizo.sample.R
import com.haizo.sample.callbacks.StoryActionCallback
import com.haizo.sample.databinding.RowStoryBinding
import com.haizo.sample.model.Story

//####################################################//
//#################### Contract ######################//
//####################################################//

val STORY_VIEW_HOLDER_CONTRACT = ViewHolderContract(
    viewHolderClass = StoryViewHolder::class.java,
    layoutResId = R.layout.row_story,
    callbackClass = StoryActionCallback::class.java
)

//####################################################//
//################## ViewHolder ######################//
//####################################################//

class StoryViewHolder constructor(
    private val binding: RowStoryBinding,
    actionCallback: StoryActionCallback?
) : BaseBindingViewHolder<Story>(binding, actionCallback) {

    init {
        binding.imageView.setOnClickListener { actionCallback?.onStoryClicked(listItem) }
    }

    override fun onBind(listItem: Story) {
        binding.setVariable(BR.model, listItem)
    }
}