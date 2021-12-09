package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.util.ListItemTypes

data class Story(
    val id: String,
    val imageUrl: String
) : ListItem {
    override var listItemType: ListItemType = ListItemTypes.ITEM_STORY

    override fun itemUniqueIdentifier(): String {
        return id
    }

    override fun areContentsTheSame(newItem: ListItem): Boolean {
        return if (newItem is Story) {
            this == newItem
        } else false
    }
}

/*
- First impression: Feel Strange the UI not that friendly
- Founded arabic influencers and arabic content as expected
- Expected to find content once opened the app
- the icon of the explore is not indicating for discover people, its indicates for search
- not interested to click on hashtags even if it has a high number
- in add post, i think we should add the word 'also' to 'Share it to my story'
 */