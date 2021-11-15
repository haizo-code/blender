package com.haizo.poc.util

import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.R
import com.haizo.poc.callbacks.StoryActionCallback
import com.haizo.poc.callbacks.UserActionCallback
import com.haizo.poc.ui.viewholder.list.GridStoriesViewHolder
import com.haizo.poc.ui.viewholder.list.HorizontalStoriesViewHolder
import com.haizo.poc.ui.viewholder.row.StoryViewHolder
import com.haizo.poc.ui.viewholder.row.UserViewHolder

object ListItemTypes {

    // Row
    val ITEM_USER_CARD = ListItemType(
        viewHolderClass = UserViewHolder::class.java,
        layoutResId = R.layout.row_user_card,
        itemName = "ITEM_USER_CARD",
        UserActionCallback::class.java)

    val ITEM_STORY = ListItemType(
        viewHolderClass = StoryViewHolder::class.java,
        layoutResId = R.layout.row_story,
        itemName = "ITEM_STORY",
        StoryActionCallback::class.java)

    // List
    val LIST_STORIES_HORIZONTAL = ListItemType(
        viewHolderClass = HorizontalStoriesViewHolder::class.java,
        layoutResId = R.layout.row_stories_rv,
        itemName = "LIST_IMAGES_HORIZONTAL",
        StoryActionCallback::class.java)

    // List
    val LIST_STORIES_GRID = ListItemType(
        viewHolderClass = GridStoriesViewHolder::class.java,
        layoutResId = R.layout.row_stories_rv,
        itemName = "LIST_STORIES_GRID",
        StoryActionCallback::class.java)
}