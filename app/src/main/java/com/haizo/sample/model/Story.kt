package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.sample.util.ListItemTypes

data class Story constructor(
    val id: String,
    val imageUrl: String
) : ListItem {
    override var listItemType: ListItemType = ListItemTypes.ITEM_STORY

    override fun itemUniqueIdentifier(): String {
        return id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return this == (newItem as? Story)
    }
}