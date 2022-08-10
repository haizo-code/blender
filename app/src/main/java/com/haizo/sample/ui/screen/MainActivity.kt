package com.haizo.sample.ui.screen

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
import com.haizo.generaladapter.utils.EdgeVerticalItemPaddingDecoration
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.sample.R
import com.haizo.sample.callbacks.StoryActionCallback
import com.haizo.sample.callbacks.UserActionCallback
import com.haizo.sample.databinding.ActivityMainBinding
import com.haizo.sample.model.LoadingItem
import com.haizo.sample.model.Story
import com.haizo.sample.model.User
import java.util.Collections.emptyList

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
        adapter.submitListItems(viewModel.getDummyItems(), "Next Page URL 1")
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
            it.addItemDecoration(ItemPaddingDecoration(bottom = 10))
            it.addItemDecoration(EdgeVerticalItemPaddingDecoration(paddingTop = 10))
        }

        adapter.setupLoadMore(loadMoreListener = object : LoadMoreListener {
            override fun onLoadMore(nextPageNumber: Int, nextPageUrl: String?) {
                Toast.makeText(this@MainActivity, "$nextPageUrl", Toast.LENGTH_SHORT).show()
                demoLoadMore(nextPageNumber)
            }
        })
        adapter.setLoadingListItem(LoadingItem())
    }

    private fun demoLoadMore(pageToLoad: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            // assuming last page is 3 and the next page returned null list
            val list: List<ListItem> =
                if (pageToLoad <= 3) {
                    ArrayList<ListItem>().also {
                        for (i in 1..10) {
                            val userWrapper = MainViewModel.getMockUser()
                            it.add(userWrapper)
                        }
                        it.add(MainViewModel.getMockStory())
                    }
                } else if (pageToLoad == 4) {
                    listOf(MainViewModel.getMockUser(), MainViewModel.getMockUser(), MainViewModel.getMockUser())
                } else emptyList()
            adapter.submitMoreListItems(list, "Next Page URL $pageToLoad")
        }, 1000)
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