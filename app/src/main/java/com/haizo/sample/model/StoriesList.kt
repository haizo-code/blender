package com.haizo.sample.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.sample.util.ListItemTypes

data class StoriesList constructor(
    var list: MutableList<Story> = ArrayList()
) : ListItem {
    override val listItemType: ListItemType = ListItemTypes.LIST_STORIES_HORIZONTAL
    override fun areContentsTheSame(newItem: ListItem) = true
}