package com.haizo.poc.model

import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.ListItemType
import com.haizo.poc.ListItemTypes
import com.haizo.poc.util.sampleBackgrounds

class UserCardModel(
    val name: String,
    val phoneNumber: String,
    val location: String,
    val imageUrl: String

) : ListItem {
    override var listItemType: ListItemType? = ListItemTypes.ITEM_USER_CARD

    companion object {
        var number = 0
        fun getRandomUser(): UserCardModel {
            return UserCardModel(
                name = "First Last $number",
                phoneNumber = "00000000${number++}",
                location = "Amman - Jordan",
                imageUrl = sampleBackgrounds[number % sampleBackgrounds.size]
            )
        }
    }
}