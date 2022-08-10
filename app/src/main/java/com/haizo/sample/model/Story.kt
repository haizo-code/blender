package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.sample.ui.viewholder.STORY_VIEW_HOLDER_CONTRACT

data class Story constructor(
    val id: String,
    val imageUrl: String
) : ListItem {
    override var viewHolderContract = STORY_VIEW_HOLDER_CONTRACT

    override fun itemUniqueIdentifier(): String {
        return id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return this == (newItem as? Story)
    }
}