package com.haizo.poc.ui.screen.main

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.loadmore.LoadMoreListener
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.poc.R
import com.haizo.poc.data.model.ModelType1
import com.haizo.poc.data.model.ModelType3
import com.haizo.poc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ListItemCallback, LoadMoreListener {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initRecyclerView()

        viewModel.items.observe(this, Observer {
            activityBinding.recyclerView2.updateList(it)
        })
    }

    private fun initRecyclerView() {
        activityBinding.recyclerView2.setup(
            layoutManager = LinearLayoutManager(this),
            listItemCallback = this,
            loadMoreListener = this,
            onRefreshListener = {Handler().postDelayed({ activityBinding.recyclerView2.removeLoadingViews() }, 2000)},
            onEmptyListListener = {hasContent -> Toast.makeText(this, "Has content: $hasContent", Toast.LENGTH_SHORT).show()}
        )
        activityBinding.recyclerView2.get()?.addItemDecoration(
            ItemPaddingDecoration(5, 5))
    }

    private fun showDialog(text: String) {
        AlertDialog.Builder(this)
            .setTitle("Triggered")
            .setMessage(text)
            .show()
    }

    override fun onItemClicked(view: View, listItem: ListItem?, position: Int, actionId: Int?) {
        when (listItem) {
            is ModelType1 -> showDialog(listItem.text ?: "")
            is ModelType3 -> showDialog(listItem.text ?: "")
        }
    }

    override fun onLoadMore(pageToLoad: Int) {
        Handler().postDelayed({ activityBinding.recyclerView2.removeLoadingViews() }, 2000)
    }
}