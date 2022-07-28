package com.haizo.sample.util

import com.haizo.generaladapter.model.ListItemType
import com.haizo.sample.R
import com.haizo.sample.callbacks.StoryActionCallback
import com.haizo.sample.callbacks.UserActionCallback
import com.haizo.sample.ui.viewholder.list.HorizontalStoriesViewHolder
import com.haizo.sample.ui.viewholder.row.StoryViewHolder
import com.haizo.sample.ui.viewholder.row.UserViewHolder

object ListItemTypes {

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

    val LIST_STORIES_HORIZONTAL = ListItemType(
        viewHolderClass = HorizontalStoriesViewHolder::class.java,
        layoutResId = R.layout.row_stories_rv,
        itemName = "LIST_IMAGES_HORIZONTAL",
        StoryActionCallback::class.java)
}