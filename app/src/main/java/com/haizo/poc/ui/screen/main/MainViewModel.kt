package com.haizo.poc.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes
import com.haizo.poc.model.ImagesListModel
import com.haizo.poc.model.StoryModel
import com.haizo.poc.model.UserCardModel

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<MutableList<ListItem>>()

    val items: LiveData<MutableList<ListItem>> = _items

    init {
        _items.value = getDummyItems()
    }

    private fun getDummyItems(): MutableList<ListItem>? {
        return mutableListOf(

            getDummyHorizontalItem(),
            UserCardModel.getRandomUser(),
            getDummyGridItems(),
            UserCardModel.getRandomUser(),
            StoryModel.getRandomStory(),

            getDummyHorizontalItem(),
            UserCardModel.getRandomUser(),
            getDummyGridItems(),
            UserCardModel.getRandomUser(),
            StoryModel.getRandomStory(),

            getDummyHorizontalItem(),
            UserCardModel.getRandomUser(),
            getDummyGridItems(),
            UserCardModel.getRandomUser(),
            StoryModel.getRandomStory()
        )
    }

    private fun getDummyHorizontalItem(): ImagesListModel {
        return ImagesListModel().apply {
            list?.let { for (i in 1..10) it.add(StoryModel.getRandomStory()) }
        }
    }

    private fun getDummyGridItems(): ImagesListModel {
        return ImagesListModel().apply {
            list?.let { for (i in 1..4) it.add(StoryModel.getRandomStory()) }
            listItemType = ListItemTypes.LIST_IMAGES_GRID
        }
    }
}

fun ArrayList<ListItem>.setType(listItemType: ListItemType) {
    forEach { it.listItemType = listItemType }
}

