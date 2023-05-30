package com.haizo.sample.ui.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.haizo.generaladapter.interfaces.LoadMoreListener
import com.haizo.generaladapter.kotlin.setupVertical
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter: BlenderListAdapter by lazy {
        BlenderListAdapter(context = this, userCallback, storyActionCallback)
    }

    private val userCallback = object : UserActionCallback {
        override fun onAvatarClicked(user: User) {
            Toast.makeText(this@MainActivity, "User Avatar clicked: ${user.name}", Toast.LENGTH_SHORT).show()
        }

        override fun onCallClicked(user: User) {
            val ss = user.copy(name = user.name + " Edited")
            adapter.updateItemData(ss, true)
            Toast.makeText(this@MainActivity, "User Call clicked: ${user.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private val storyActionCallback = object : StoryActionCallback {
        override fun onStoryClicked(story: Story) {
            val updatedStory = story.copy(clicksCount = story.clicksCount + 1)
            val containerId = story.containerId ?: ""
            adapter.updateInnerListItem(containerId, updatedStory)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupRecyclerView()
        adapter.submitListItems(viewModel.getDummyItems(), "Next Page URL 1")
    }

    private fun setupRecyclerView() {
        binding.recyclerView.let {
            it.setupVertical()
            it.adapter = adapter
            it.addItemDecoration(ItemPaddingDecoration(bottom = 10))
            it.addItemDecoration(EdgeVerticalItemPaddingDecoration(paddingTop = 10))
        }

        adapter.setupLoadMore(
            autoShowLoadingItem = true,
            pageSize = 10,
            loadingThreshold = 3,
            loadMoreListener = object : LoadMoreListener {
                override fun onLoadMore(nextPageNumber: Int, nextPagePayload: String?) {
                    Toast.makeText(this@MainActivity, "$nextPagePayload", Toast.LENGTH_SHORT).show()
                    demoLoadMore(nextPageNumber)
                }

                override fun isShouldTriggerLoadMore(nextPageNumber: Int, nextPagePayload: String?): Boolean {
                    return nextPageNumber <= 4
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
}