package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemsContainer
import com.haizo.sample.ui.viewholder.HORIZONTAL_STORIES_VIEW_HOLDER_CONTRACT

data class StoriesList constructor(
    val id: String,
    var list: MutableList<Story>
) : ListItemsContainer<Story> {

    override fun getInnerList() = list
    override val viewHolderContract = HORIZONTAL_STORIES_VIEW_HOLDER_CONTRACT
    override fun areContentsTheSame(newItem: ListItem) = this == newItem
    override fun itemUniqueIdentifier() = id
}