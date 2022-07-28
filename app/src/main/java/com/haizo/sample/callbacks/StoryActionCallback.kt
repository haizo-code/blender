package com.haizo.sample.callbacks

import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.sample.model.Story

interface StoryActionCallback : BaseActionCallback {
    fun onStoryClicked(story: Story)
}