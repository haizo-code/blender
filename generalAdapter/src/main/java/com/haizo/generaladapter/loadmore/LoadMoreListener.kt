package com.haizo.generaladapter.loadmore

/**
 * Created by Farouq Afghani on 2019-02-21.
 */
interface LoadMoreListener {
    fun onLoadMore(pageToLoad: Int)
    fun onLoadMoreFinished(){}
}