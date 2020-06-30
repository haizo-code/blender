package com.haizo.poc

import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ui.viewholder.list.GridStoriesViewHolder
import com.haizo.poc.ui.viewholder.list.HorizontalStoriesViewHolder
import com.haizo.poc.ui.viewholder.row.StoryViewHolder
import com.haizo.poc.ui.viewholder.row.UserCardViewHolder

object ListItemTypes {

    // Row
    val ITEM_USER_CARD = ListItemType(UserCardViewHolder::class.java, R.layout.row_user_card, "ITEM_USER_CARD")
    val ITEM_STORY = ListItemType(StoryViewHolder::class.java, R.layout.row_story, "ITEM_STORY")

    // List
    val LIST_IMAGES_HORIZONTAL= ListItemType(HorizontalStoriesViewHolder::class.java, R.layout.row_rv_images, "LIST_IMAGES_HORIZONTAL")
    val LIST_IMAGES_GRID= ListItemType(GridStoriesViewHolder::class.java, R.layout.row_rv_images, "LIST_IMAGES_GRID")
}