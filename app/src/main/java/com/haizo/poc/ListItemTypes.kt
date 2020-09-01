package com.haizo.poc

import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ui.viewholder.list.GridStoriesViewHolder
import com.haizo.poc.ui.viewholder.list.HorizontalStoriesViewHolder
import com.haizo.poc.ui.viewholder.row.StoryViewHolder
import com.haizo.poc.ui.viewholder.row.UserCardViewHolder

object ListItemTypes {

    // Row
    val ITEM_USER_CARD = ListItemType(
        viewHolderClass = UserCardViewHolder::class.java,
        layoutResId = R.layout.row_user_card,
        itemName = "ITEM_USER_CARD")

    val ITEM_STORY = ListItemType(
        viewHolderClass = StoryViewHolder::class.java,
        layoutResId = R.layout.row_story,
        itemName = "ITEM_STORY")

    // List
    val LIST_IMAGES_HORIZONTAL = ListItemType(
        viewHolderClass = HorizontalStoriesViewHolder::class.java,
        layoutResId = R.layout.row_rv_images,
        itemName = "LIST_IMAGES_HORIZONTAL")

    val LIST_IMAGES_GRID = ListItemType(
        viewHolderClass = GridStoriesViewHolder::class.java,
        layoutResId = R.layout.row_rv_images,
        itemName = "LIST_IMAGES_GRID")
}