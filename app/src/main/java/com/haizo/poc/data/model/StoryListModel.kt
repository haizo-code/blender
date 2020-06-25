package com.haizo.poc.data.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.poc.utils.ListItemTypes

class StoryListModel : ListItem(ListItemTypes.LIST_HORIZONTAL_STORIES) {

    var list: ArrayList<ModelType2>? = ArrayList()
}
