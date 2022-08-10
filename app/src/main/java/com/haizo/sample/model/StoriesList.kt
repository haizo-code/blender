package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.sample.ui.viewholder.HORIZONTAL_STORIES_VIEW_HOLDER_CONTRACT

data class StoriesList constructor(
    var list: MutableList<Story> = ArrayList()
) : ListItem {
    override val viewHolderContract = HORIZONTAL_STORIES_VIEW_HOLDER_CONTRACT
    override fun areContentsTheSame(newItem: ListItem) = true
}