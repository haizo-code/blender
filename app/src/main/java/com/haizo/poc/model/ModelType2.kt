package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes

class ModelType2(val imageUrl: String?) : ListItem {
    override var listItemType: ListItemType? = ListItemTypes.ITEM_TYPE_2
}