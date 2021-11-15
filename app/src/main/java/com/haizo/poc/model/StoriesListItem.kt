package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType

class StoriesListItem constructor(
    override val listItemType: ListItemType
) : ListItem {
    var list: MutableList<Story> = ArrayList()
}