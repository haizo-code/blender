package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes

class ImagesListModel : ListItem {
    var list: ArrayList<ModelType2>? = ArrayList()

    override var listItemType: ListItemType? = ListItemTypes.LIST_HORIZONTAL_IMAGES
}
