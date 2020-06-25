package com.haizo.poc.utils

import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.R
import com.haizo.poc.ui.viewholder.list.HorizontalStoriesVH
import com.haizo.poc.ui.viewholder.row.Type1ViewHolder
import com.haizo.poc.ui.viewholder.row.Type2ViewHolder
import com.haizo.poc.ui.viewholder.row.Type3ViewHolder

object ListItemTypes {
    // row
    val ITEM_TYPE_1 =
        ListItemType(Type1ViewHolder::class.java, R.layout.row_type_1,
            "ITEM_TYPE_1")
    val ITEM_TYPE_2 =
        ListItemType(Type2ViewHolder::class.java, R.layout.row_type_2,
            "ITEM_TYPE_2")
    val ITEM_TYPE_3 =
        ListItemType(Type3ViewHolder::class.java, R.layout.row_type_3,
            "ITEM_TYPE_3")

    // list
    val LIST_HORIZONTAL_STORIES =
        ListItemType(HorizontalStoriesVH::class.java,
            R.layout.row_rv_horizontal_stories, "HORIZONTAL_STORIES")
}
