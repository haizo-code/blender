package com.haizo.poc.ui.screen.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haizo.generaladapter.ListItemCallback
import com.haizo.generaladapter.adapter.GeneralBindingListAdapter
import com.haizo.generaladapter.model.ListItem
import com.haizo.generaladapter.utils.ItemPaddingDecoration
import com.haizo.poc.R
import com.haizo.poc.databinding.ActivityMainBinding
import com.haizo.poc.model.ModelType1
import com.haizo.poc.model.ModelType2
import com.haizo.poc.util.toast

class MainActivity : AppCompatActivity(), ListItemCallback {

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
            is ModelType1 -> toast(listItem.text)
            is ModelType2 -> toast(listItem.imageUrl)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.addItemDecoration(ItemPaddingDecoration(10, 10))
        binding.recyclerView.adapter = adapter
    }
}