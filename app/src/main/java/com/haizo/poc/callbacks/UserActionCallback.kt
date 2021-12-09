package com.haizo.poc.callbacks

import com.haizo.generaladapter.interfaces.BaseActionCallback
import com.haizo.poc.model.User

interface UserActionCallback : BaseActionCallback {
    fun onAvatarClicked(user: User)
    fun onCallClicked(user: User)
}