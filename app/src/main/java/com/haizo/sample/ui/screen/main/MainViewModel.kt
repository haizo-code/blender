package com.haizo.sample.ui.screen.main

import androidx.lifecycle.ViewModel
import com.haizo.generaladapter.model.ListItem
import com.haizo.sample.model.StoriesList
import com.haizo.sample.model.Story
import com.haizo.sample.model.User
import com.haizo.sample.util.sampleBackgrounds
import java.util.UUID

class MainViewModel : ViewModel() {

    fun getDummyItems(): MutableList<ListItem> {
        // Adding a mix of all the types of the viewHolders
        return mutableListOf(
            getDummyHorizontalItem(),
            getMockUser(),
            getMockUser(),
            getDummyHorizontalItem(),
            getMockUser(),
            getMockUser(),
            getDummyHorizontalItem(),
            getMockUser(),
            getMockUser(),
            getMockStory(),
            getMockUser(),
            getMockUser(),
        )
    }

    private fun getDummyHorizontalItem(): StoriesList {
        val list: MutableList<Story> = ArrayList()
        list.let { for (i in 1..10) it.add(getMockStory()) }
        return StoriesList(list)
    }

    companion object {
        /*For testing only*/
        var dummyUserNumber = 0
        fun getMockUser(): User {
            return User(
                id = UUID.randomUUID().toString(),
                name = "Name $dummyUserNumber",
                phoneNumber = "00000000${dummyUserNumber++}",
                location = "Amman - Jordan",
                imageUrl = sampleBackgrounds.random()
            )
        }

        /*For testing only*/
        fun getMockStory(): Story {
            return Story(
                id = UUID.randomUUID().toString(),
                imageUrl = sampleBackgrounds.random()
            )
        }
    }
}