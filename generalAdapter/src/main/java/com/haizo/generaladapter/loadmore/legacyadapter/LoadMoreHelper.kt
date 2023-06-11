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
package com.haizo.generaladapter.loadmore.legacyadapter

import androidx.recyclerview.widget.RecyclerView
import com.haizo.generaladapter.loadmore.BaseLoadMoreHelper
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.model.LoadingListItem

/**
 * This class will handle the loadMore process
 * @param [adapter] recyclerview adapter
 */
internal class LoadMoreHelper constructor(
    private val adapter: RecyclerView.Adapter<*>
) : BaseLoadMoreHelper() {

    /**
     * Add loadMore view to the recyclerView
     */
    override fun addLoadMoreView(commitCallback: Runnable?) {
        if (isLoadingItemAdded()) return
        mItems.add(loadingListItem)
        adapter.notifyItemInserted(mItems.size - 1)
        commitCallback?.run()
    }

    /**
     * Remove the loadMore view if exists / Trigger onLoadMoreFinished()
     */
    override fun removeLoadMoreIfExists(commitCallback: Runnable?) {
        val index = mItems.indexOfFirst { it is LoadingListItem }
        if (index != -1) {
            mItems.removeAt(index)
            adapter.notifyItemRemoved(index)
        }
        commitCallback?.run()
        isLoadingInProgress = false
        loadMoreListener?.onLoadMoreFinished()
    }

    /**
     * Add more items to the main listItem for the adapter
     */
    fun addMoreItems(collection: Collection<ListItem>, nextPagePayload: String?) {
        removeLoadMoreIfExists()
        val positionStart = mItems.size + 1
        mItems.addAll(collection)
        adapter.notifyItemRangeInserted(positionStart, mItems.size)
        this.nextPagePayload = nextPagePayload
    }
}