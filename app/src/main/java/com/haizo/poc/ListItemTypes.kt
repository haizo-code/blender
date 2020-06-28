package com.haizo.poc

import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ui.viewholder.list.GridImagesViewHolder
import com.haizo.poc.ui.viewholder.list.HorizontalImagesViewHolder
import com.haizo.poc.ui.viewholder.row.Type1ViewHolder
import com.haizo.poc.ui.viewholder.row.Type2ViewHolder

object ListItemTypes {

    // Row
    val ITEM_TYPE_1 = ListItemType(Type1ViewHolder::class.java, R.layout.row_type_1, "ITEM_TYPE_1")
    val ITEM_TYPE_2 = ListItemType(Type2ViewHolder::class.java, R.layout.row_type_2, "ITEM_TYPE_2")

    // List
    val LIST_HORIZONTAL_IMAGES= ListItemType(HorizontalImagesViewHolder::class.java, R.layout.row_rv_images, "LIST_HORIZONTAL_IMAGES")
    val LIST_GRID_IMAGES= ListItemType(GridImagesViewHolder::class.java, R.layout.row_rv_images, "LIST_GRID_IMAGES")
}