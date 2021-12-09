package com.haizo.poc.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haizo.generaladapter.model.ListItem
import com.haizo.poc.model.StoriesListItem
import com.haizo.poc.model.Story
import com.haizo.poc.model.User
import com.haizo.poc.util.ListItemTypes
import com.haizo.poc.util.sampleBackgrounds
import java.util.UUID

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<MutableList<ListItem>>()

    val items: LiveData<MutableList<ListItem>> = _items

    init {
        _items.value = getDummyItems()
    }

    private fun getDummyItems(): MutableList<ListItem> {
        return mutableListOf(
            getDummyHorizontalItem(),
            getRandomUser(),
            getRandomUser(),
            getDummyGridItem(),
            getRandomUser(),
            getRandomStory(),
            getRandomUser(),
            getRandomUser(),
            getRandomUser(),
            getRandomUser(),
        )
    }

    private fun getDummyHorizontalItem(): StoriesListItem {
        return StoriesListItem(listItemType = ListItemTypes.LIST_STORIES_HORIZONTAL).apply {
            list.let { for (i in 1..10) it.add(getRandomStory()) }
        }
    }

    private fun getDummyGridItem(): StoriesListItem {
        return StoriesListItem(listItemType = ListItemTypes.LIST_STORIES_GRID).apply {
            list.let { for (i in 1..4) it.add(getRandomStory()) }
        }
    }

    companion object {
        /*For testing only*/
        var dummyUserNumber = 0
        fun getRandomUser(): User {
            return User(
                id = UUID.randomUUID().toString(),
                name = "First Last $dummyUserNumber",
                phoneNumber = "00000000${dummyUserNumber++}",
                location = "Amman - Jordan",
                imageUrl = sampleBackgrounds.random()
            )
        }

        /*For testing only*/
        fun getRandomStory(): Story {
            return Story(
                id = UUID.randomUUID().toString(),
                imageUrl = sampleBackgrounds.random()
            )
        }
    }
}