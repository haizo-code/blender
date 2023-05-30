package com.haizo.sample.ui.screen

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
            getDummyHorizontalItem("A"),
            getMockUser(),
            getMockUser(),
            getDummyHorizontalItem("B"),
            getMockUser(),
            getMockUser(),
            getDummyHorizontalItem("C"),
            getMockUser(),
            getMockUser(),
            getMockUser(),
            getMockUser(),
        )
    }

    private fun getDummyHorizontalItem(uniqueIdentifier: String): StoriesList {
        val list: MutableList<Story> = ArrayList()
        list.let { for (i in 1..10) it.add(getMockStory(uniqueIdentifier)) }
        return StoriesList(uniqueIdentifier, list)
    }

    companion object {
        /*For testing only*/
        private var dummyUserNumber = 0
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
        fun getMockStory(containerID: String? = null): Story {
            return Story(
                containerId = containerID,
                id = UUID.randomUUID().toString(),
                imageUrl = sampleBackgrounds.random()
            )
        }
    }
}