package com.haizo.generaladapter.model

import java.util.ArrayList

/**
 * This could be used as a wrapper class for the model that will be used inside the ViewHolder
 * This may need this when you do not want to let your model implements the ListItem,
 * such as in Clean-Architecture when the model is separated in a differance package as in Domain-Package
 */
abstract class ListItemWrapper : ListItem

inline fun <reified T : ListItemWrapper> List<*>.getListItemWrapper(): List<T> {
    return ArrayList<T>().also { list ->
        forEach { item ->
            val listItemInstance = T::class.java.constructors.first().newInstance(item) as T
            list.add(listItemInstance)
        }
    }
}