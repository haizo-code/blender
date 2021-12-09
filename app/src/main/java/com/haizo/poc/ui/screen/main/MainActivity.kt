package com.haizo.poc.ui.screen.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.listadapter.BlenderListAdapter
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.poc.R
import com.haizo.poc.callbacks.StoryActionCallback
import com.haizo.poc.callbacks.UserActionCallback
import com.haizo.poc.databinding.ActivityMainBinding
import com.haizo.poc.model.Story
import com.haizo.poc.model.User

class MainActivity : AppCompatActivity(), UserActionCallback, StoryActionCallback {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter: BlenderListAdapter by lazy {
        BlenderListAdapter(context = this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
        setupLoadMore()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.addItemDecoration(ItemPaddingDecoration(startEndOffset = 5, topBottomOffset = 10, startingIndex = 1))
        }
    }

    private fun setupLoadMore() {
        adapter.setupLoadMore(pageSize = 10, loadMoreListener = object : LoadMoreListener {
            override fun onLoadMore(pageToLoad: Int) {
                demoLoadMore(pageToLoad)
            }
        })
    }

    private fun demoLoadMore(pageToLoad: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            // assuming last page is 3 and the next page returned null list
            val list: List<ListItem> =
                if (pageToLoad <= 3) {
                    ArrayList<ListItem>().also {
                        for (i in 1..10) {
                            it.add(MainViewModel.getRandomUser())
                        }
                        it.add(MainViewModel.getRandomStory())
                    }
                } else emptyList()
            adapter.submitMoreList(pageToLoad, list)
        }, 1000)
    }

    override fun onStart() {
        super.onStart()
        viewModel.items.observe(this, {
            adapter.submitMoreList(page = 1, list = it)
        })
    }

    override fun onStoryClicked(story: Story) {
        Toast.makeText(this@MainActivity, "Story clicked id: ${story.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onAvatarClicked(user: User) {
        Toast.makeText(this@MainActivity, "User Avatar clicked: ${user.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onCallClicked(user: User) {
        Toast.makeText(this@MainActivity, "User Call clicked: ${user.name}", Toast.LENGTH_SHORT).show()
    }
}