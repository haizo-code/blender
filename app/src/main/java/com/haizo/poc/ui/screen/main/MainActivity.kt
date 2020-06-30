package com.haizo.poc.ui.screen.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.adapter.GeneralBindingListAdapter
import com.haizo.generaladapter.loadmore.LoadMoreListener
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.poc.R
import com.haizo.poc.databinding.ActivityMainBinding
import com.haizo.poc.model.StoryModel
import com.haizo.poc.model.UserCardModel
import com.haizo.poc.util.showAlert

class MainActivity : AppCompatActivity(), ListItemCallback, LoadMoreListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val adapter: GeneralBindingListAdapter by lazy {
        GeneralBindingListAdapter(context = this, listItemCallback = this)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(this, Observer {
            adapter.updateList(it)
        })
    }

    override fun onItemClicked(view: View, listItem: ListItem, position: Int, actionId: Int) {
        when (listItem) {
            is UserCardModel -> showAlert(message = listItem.name)
            is StoryModel -> showAlert(message = listItem.imageUrl)
        }
    }

    override fun onLoadMore(pageToLoad: Int) {
        demoLoadMore(pageToLoad)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.addItemDecoration(ItemPaddingDecoration(startEndOffset = 5, topBottomOffset = 10, startingIndex = 1))
            adapter.setupLoadMore(loadMoreListener = this, pageSize = 9)
        }
    }

    private fun demoLoadMore(pageToLoad: Int) {
        Handler().postDelayed({
            // lets say the last page is 3 and the next page returned null list
            val list: List<UserCardModel>? =
                if (pageToLoad <= 3) {
                    ArrayList<UserCardModel>().also {
                        for (i in 1..10) {
                            it.add(UserCardModel.getRandomUser())
                        }
                    }
                } else null
            adapter.addMoreItems(list)
        }, 500)
    }
}