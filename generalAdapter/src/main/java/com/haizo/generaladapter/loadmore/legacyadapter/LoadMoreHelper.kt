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
import com.haizo.generaladapter.loadmore.LoadingObj
import com.haizo.generaladapter.model.ListItem

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
    override fun addLoadMoreView() {
        if (!isLoadingItemAdded()) {
            mItems.add(loadingListItem)
            adapter.notifyItemInserted(mItems.size - 1)
        }
    }

    /**
     * Remove the loadMore view if exists / Trigger onLoadMoreFinished()
     */
    override fun removeLoadMoreIfExists() {
        if (mItems.isNotEmpty()) {
            val lastIndex = mItems.lastIndex
            if (mItems[lastIndex] is LoadingObj) {
                mItems.removeAt(lastIndex)
                adapter.notifyItemRemoved(lastIndex)
            }
        }
        loadMoreListener?.onLoadMoreFinished()
    }

    /**
     * Add more items to the main listItem for the adapter
     */
    fun addMoreItems(collection: Collection<ListItem>, nextPageUrl: String?) {
        removeLoadMoreIfExists()
        val positionStart = mItems.size + 1
        mItems.addAll(collection)
        adapter.notifyItemRangeInserted(positionStart, mItems.size)
        this.nextPageUrl = nextPageUrl
    }
}