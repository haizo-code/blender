/*
 * Copyright 2020 Farouq Afghani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haizo.generaladapter.model

/**
 * Should be used instead of ListItemWrapper/ListItem in case the object is holding a list of ListItems, such as inner recyclerview
 */
interface ListItemsContainer<T : ListItem> : ListItem {

    fun getInnerList(): MutableList<T>

    fun updateItemInList(updatedListItem: T) {
        getInnerList()
            .indexOfFirst { it.itemUniqueIdentifier() == updatedListItem.itemUniqueIdentifier() }
            .takeIf { it != -1 }?.let { getInnerList()[it] = updatedListItem }
    }
}