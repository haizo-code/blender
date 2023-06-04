package com.haizo.generaladapter.listadapter

import android.content.Context
import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.generaladapter.listitems.MockListItem
import com.haizo.generaladapter.model.ListItem

abstract class SectionBlenderListAdapter constructor(
    context: Context,
    vararg actionCallbacks: BaseActionCallback,
) : BlenderListAdapter(context, *actionCallbacks) {

    abstract val sectionsList: List<ListItem>

    private fun getSectionList(): List<ListItem> {
        return sectionsList
    }

    init {
        submitList(getSectionList())
    }

    fun getItemsUnderSection(section: MockListItem, includeSection: Boolean = false): List<ListItem> {
        val diff = if (includeSection) 0 else 1
        val remainingSections = sectionsList.let { it.subList(it.indexOf(section) + diff, it.size) }
        val endIndex = remainingSections.firstOrNull()?.let { indexOf(it) } ?: itemCount
        return currentList.subList(indexOf(section) + 1, endIndex)
    }

    fun submitListItemsUnderSection(
        list: List<ListItem>?,
        section: MockListItem,
        commitCallback: Runnable? = null
    ) {
        val mItems = currentList.toMutableList()
        val startIndexForSection = indexOf(section) + 1

        val remainingSections = sectionsList.let { it.subList(it.indexOf(section) + 1, it.size) }
        val endIndex = remainingSections.firstOrNull()?.let { indexOf(it) } ?: -1

        if (startIndexForSection != endIndex && startIndexForSection != -1 && endIndex != -1) {
            // Remove the range of items
            mItems.subList(startIndexForSection, endIndex).clear()
        }
        // Add the new items at the specified index
        list?.let { mItems.addAll(startIndexForSection, it) }
        submitListItems(mItems) {
            commitCallback?.run()
            removeLoadingItem()
        }
    }
}