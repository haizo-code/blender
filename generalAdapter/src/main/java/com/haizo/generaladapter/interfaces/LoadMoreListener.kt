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
package com.haizo.generaladapter.interfaces

interface LoadMoreListener {

    /**
     * Triggered after the current adapter position satisfy the threshold and before the [onLoadMore] is triggered
     * @param nextPageNumber: The next page to load
     * @param nextPageUrl: The next page url, you need to pass it when you submit the listItems in
     * these methods: submitListItems(..) | submitMoreListItems(..)
     */
    fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPageUrl: String?): Boolean {
        return true
    }

    /**
     * Triggered when the current adapter position satisfy the threshold logic
     * @param nextPageNumber: The next page to load
     * @param nextPageUrl: The next page url, you need to pass it when you submit the listItems in
     * these methods: submitListItems(..) | submitMoreListItems(..)
     */
    fun onLoadMore(nextPageNumber: Int, nextPageUrl: String?)

    /**
     * Triggered when loading finished (when removing the load-more item from the list)
     */
    fun onLoadMoreFinished() {}
}