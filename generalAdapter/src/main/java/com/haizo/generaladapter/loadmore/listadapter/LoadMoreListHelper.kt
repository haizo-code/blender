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
package com.haizo.generaladapter.loadmore.listadapter

import androidx.recyclerview.widget.ListAdapter
import com.haizo.generaladapter.loadmore.BaseLoadMoreHelper
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem
import com.haizo.generaladapter.viewholders.BaseBindingViewHolder

/**
 * This class will handle the loadMore process
 * @param [adapter] recyclerview adapter
 */
internal class LoadMoreListHelper constructor(
    private val adapter: ListAdapter<ListItem, BaseBindingViewHolder<ListItem>>,
) : BaseLoadMoreHelper() {

    /*** Clear and reset the page number, then update the list with the new list */
    fun updateListItems(collection: Collection<ListItem>?) {
        removeLoadMoreIfExists()
        resetPage()
        updateMainList(collection)
    }

    fun updateMainList(collection: Collection<ListItem>?) {
        mItems.clear()
        collection?.let { mItems.addAll(it) }
    }

    /*** Add more items to the main listItem for the adapter */
    fun addMoreItems(collection: Collection<ListItem>, commitCallback: Runnable?) {
        removeLoadMoreIfExists()
        mItems.addAll(collection)
        adapter.submitList(mItems.toList(), commitCallback)
    }

    override fun addLoadMoreView(commitCallback: Runnable?) {
        if (isLoadingItemAdded()) return
        mItems.add(loadingListItem)
        adapter.submitList(mItems.toList(), commitCallback)
    }

    override fun removeLoadMoreIfExists(commitCallback: Runnable?) {
        val index = mItems.indexOfFirst { it is LoadingListItem }
        if (index != -1) {
            mItems.removeAt(index)
            adapter.submitList(mItems.toList(), commitCallback)
        } else {
            commitCallback?.run()
        }
        isLoadingInProgress = false
        loadMoreListener?.onLoadMoreFinished()
    }
}