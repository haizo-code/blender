package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes
import com.haizo.poc.util.sampleBackgrounds

class StoryModel(
    val id: String,
    val imageUrl: String

) : ListItem {
    override var listItemType: ListItemType? = ListItemTypes.ITEM_STORY

    companion object {
        var number = 0
        fun getRandomStory(): StoryModel {
            return StoryModel(
                id = number++.toString(),
                imageUrl = sampleBackgrounds[number % sampleBackgrounds.size]
            )
        }
    }
}