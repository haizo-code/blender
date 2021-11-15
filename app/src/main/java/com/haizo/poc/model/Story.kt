package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.util.ListItemTypes

class Story(
    val id: String,
    val imageUrl: String
) : ListItem {
    override var listItemType: ListItemType = ListItemTypes.ITEM_STORY
}