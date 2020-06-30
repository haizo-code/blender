package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes

class ImagesListModel : ListItem {
    var list: ArrayList<StoryModel>? = ArrayList()

    override var listItemType: ListItemType? = ListItemTypes.LIST_IMAGES_HORIZONTAL
}
