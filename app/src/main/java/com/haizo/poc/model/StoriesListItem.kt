package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType

data class StoriesListItem constructor(
    var list: MutableList<Story> = ArrayList(),
    override val listItemType: ListItemType
) : ListItem