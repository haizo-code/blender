package com.haizo.poc.callbacks

import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.poc.model.Story

interface StoryActionCallback : BaseActionCallback {
    fun onStoryClicked(story: Story)
}