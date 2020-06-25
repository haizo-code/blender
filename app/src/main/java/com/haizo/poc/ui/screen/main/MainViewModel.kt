package com.haizo.poc.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.model.ImagesListModel
import com.haizo.poc.model.ModelType1
import com.haizo.poc.model.ModelType2
import com.haizo.poc.util.sampleBackgrounds

class MainViewModel : ViewModel() {

    private val _items = MutableLiveData<MutableList<ListItem>>()

    val items: LiveData<MutableList<ListItem>> = _items

    init {
        _items.value = getDummyItems()
    }

    private fun getDummyItems(): MutableList<ListItem>? {
        return mutableListOf(
            getDummyHorizontalItem(),
            ModelType1(text = "Sample text here 1"),
            ModelType2(imageUrl = sampleBackgrounds.random()),

            getDummyHorizontalItem(),
            ModelType1(text = "Sample text here 2"),
            ModelType2(imageUrl = sampleBackgrounds.random()),

            getDummyHorizontalItem(),
            ModelType1(text = "Sample text here 3"),
            ModelType2(imageUrl = sampleBackgrounds.random())
        )
    }

    private fun getDummyHorizontalItem(): ImagesListModel {
        return ImagesListModel().apply {
            list?.let { for (i in 0..10) it.add(ModelType2(imageUrl = sampleBackgrounds.random())) }
        }
    }
}

fun MutableList<ListItem>.setType(listItemType: ListItemType) {
    forEach { it.listItemType = listItemType }
}

